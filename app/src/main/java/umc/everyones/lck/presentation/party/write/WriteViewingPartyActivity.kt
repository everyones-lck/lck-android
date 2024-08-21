package umc.everyones.lck.presentation.party.write

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import umc.everyones.lck.R
import umc.everyones.lck.databinding.ActivityWriteViewingPartyBinding
import umc.everyones.lck.domain.model.request.party.WriteViewingPartyModel
import umc.everyones.lck.presentation.MainActivity
import umc.everyones.lck.presentation.base.BaseActivity
import umc.everyones.lck.presentation.party.dialog.CalendarDialogFragment
import umc.everyones.lck.presentation.party.write.WriteViewingPartyViewModel.Companion.GEOCODING_FAIL
import umc.everyones.lck.util.extension.addDecimalFormattedTextWatcher
import umc.everyones.lck.util.extension.repeatOnStarted
import umc.everyones.lck.util.extension.setOnEditorActionListener
import umc.everyones.lck.util.extension.setOnSingleClickListener
import umc.everyones.lck.util.extension.showCustomSnackBar
import umc.everyones.lck.util.extension.showKeyboard
import umc.everyones.lck.util.extension.textToString
import umc.everyones.lck.util.extension.validateMaxLength
import umc.everyones.lck.util.network.UiState

@AndroidEntryPoint
class WriteViewingPartyActivity :
    BaseActivity<ActivityWriteViewingPartyBinding>(R.layout.activity_write_viewing_party),
    OnMapReadyCallback {
    private var naverMap: NaverMap? = null
    private val viewingPartyMarker = Marker()
    private val viewModel: WriteViewingPartyViewModel by viewModels()
    private var isEdit = false
    private var postId = 0L
    private var shortLocation = ""
    private var location = ""
    override fun initObserver() {
        repeatOnStarted {
            // 달력에서 선택한 날짜 및 시간 반영
            viewModel.date.collect {
                binding.tvWriteViewingPartyDate.text = it
            }
        }

        repeatOnStarted {
            viewModel.writeViewingPartyEvent.collect { event ->
                handleUiState(event)
            }
        }
    }

    private fun handleUiState(state: UiState<WriteViewingPartyViewModel.WriteViewingPartyEvent>) {
        when (state) {
            is UiState.Success -> {
                handleWriteViewingPartyEvent(state.data)
            }

            is UiState.Failure -> {
                showCustomSnackBar(binding.root, state.msg)
                if(state.msg == GEOCODING_FAIL){
                    viewingPartyMarker.map = null
                }
            }

            else -> Unit
        }
    }

    private fun handleWriteViewingPartyEvent(event: WriteViewingPartyViewModel.WriteViewingPartyEvent) {
        when (event) {
            is WriteViewingPartyViewModel.WriteViewingPartyEvent.Geocoding -> {
                with(event.geoCodingResult) {
                    if (!latLng.isValid) {
                        showCustomSnackBar(binding.root, "주소를 정확히 입력해주세요")
                        return
                    }

                    shortLocation = adminAddress

                    // 지도에 마커 띄우고 카메라 이동
                    viewingPartyMarker.apply {
                        position = latLng
                        icon = OverlayImage.fromResource(R.drawable.img_marker)
                        map = naverMap
                    }

                    naverMap?.moveCamera(CameraUpdate.scrollTo(latLng))
                    binding.etWriteViewingPartyAddress.setText(
                        roadAddress.ifEmpty { jibunAddress }
                    )
                }
            }

            is WriteViewingPartyViewModel.WriteViewingPartyEvent.WriteDoneViewingParty -> {
                if(event.isWriteDone) {
                    setResult(
                        RESULT_OK,
                        MainActivity.writeDoneIntent(this@WriteViewingPartyActivity, true)
                    )
                    finish()
                }
            }
        }
    }

    override fun initView() {
        initEditView()
        initNaverMap()
        addDecimalFormat()
        setViewingPartyPlace()
        validateViewingPartyName()
        validateViewingPartyQualify()
        validateViewingPartyEtc()
        showKeyBoard()
        writeDone()
        showDatePicker()
        closeWriteViewingParty()
    }

    private fun initEditView() {
        val editViewingParty = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("viewingParty", WriteViewingPartyModel::class.java)
        } else {
            intent.getSerializableExtra("viewingParty") as? WriteViewingPartyModel
        }
        if (editViewingParty != null) {
            isEdit = true
            postId = intent.getLongExtra("postId", 0L)
            with(binding) {
                etWriteViewingPartyName.setText(editViewingParty.name)
                etWriteViewingPartyPrice.setText(editViewingParty.price)
                etWriteViewingPartyQualify.setText(editViewingParty.qualify)
                etWriteViewingPartyParticipantMinimum.setText(editViewingParty.lowParticipate)
                etWriteViewingPartyParticipantMaximum.setText(editViewingParty.highParticipate)
                etWriteViewingPartyAddress.setText(editViewingParty.location)
                etWriteViewingPartyEtc.setText(editViewingParty.etc)
                tvWriteViewingPartyDate.text = editViewingParty.date
                tvWriteDone.text = "수정"

                location = editViewingParty.location
            }
        }
    }

    // 달력 표시
    private fun showDatePicker() {
        binding.tvWriteViewingPartyDate.setOnSingleClickListener {
            val dialog = CalendarDialogFragment()
            dialog.show(supportFragmentManager, dialog.tag)
        }
    }

    private fun showKeyBoard() {
        with(binding) {
            // 비용 입력
            layoutWriteViewingPartyPrice.setOnSingleClickListener {
                etWriteViewingPartyPrice.showKeyboard()
            }

            // 최소 인원 입력
            layoutWriteViewingPartyParticipantMinimum.setOnSingleClickListener {
                etWriteViewingPartyParticipantMinimum.showKeyboard()
            }

            // 최대 인원 입력
            layoutWriteViewingPartyParticipantMaximum.setOnSingleClickListener {
                etWriteViewingPartyParticipantMaximum.showKeyboard()
            }
        }
    }

    // 뷰잉파티 이름 유효성 검사
    private fun validateViewingPartyName() {
        with(binding) {
            etWriteViewingPartyName.validateMaxLength(this@WriteViewingPartyActivity, 20,
                onLengthExceeded = {
                    showCustomSnackBar(
                        etWriteViewingPartyName,
                        "이름은 최대 20자까지 입력할 수 있어요"
                    )
                }
            )
        }
    }

    // 뷰잉파티 참여 자격 및 조건 유효성 검사
    private fun validateViewingPartyQualify() {
        with(binding) {
            etWriteViewingPartyQualify.validateMaxLength(this@WriteViewingPartyActivity, 100,
                onLengthExceeded = {
                    showCustomSnackBar(
                        etWriteViewingPartyQualify,
                        "참여 자격 및 조건은 최대 100자까지 입력할 수 있어요"
                    )
                }
            )
        }
    }

    // 뷰잉파티 기타 유효성 검사
    private fun validateViewingPartyEtc() {
        with(binding) {
            etWriteViewingPartyEtc.validateMaxLength(this@WriteViewingPartyActivity, 1000,
                onLengthExceeded = {
                    showCustomSnackBar(
                        etWriteViewingPartyEtc,
                        "기타 항목은 최대 1000자까지 입력할 수 있어요"
                    )
                }
            )
        }
    }

    // 숫자 입력 시 #,### 형식으로 콤마 추가
    private fun addDecimalFormat() {
        with(binding) {
            etWriteViewingPartyPrice.addDecimalFormattedTextWatcher(this@WriteViewingPartyActivity)
            etWriteViewingPartyParticipantMaximum.addDecimalFormattedTextWatcher(this@WriteViewingPartyActivity)
            etWriteViewingPartyParticipantMinimum.addDecimalFormattedTextWatcher(this@WriteViewingPartyActivity)
        }
    }

    private fun initNaverMap() {
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance(NaverMapOptions().locationButtonEnabled(false)).also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync { map ->
            naverMap = map
            naverMap?.moveCamera(CameraUpdate.zoomTo(16.0))
            if(location.isNotEmpty()) {
                viewModel.fetchGeocoding(location)
            }
        }
    }

    // 뷰잉파티 등록 버튼 눌렀을 때
    private fun writeDone() {
        with(binding) {
            tvWriteDone.setOnSingleClickListener {

                // 최대 최소 인원 예외처리
                if (etWriteViewingPartyParticipantMaximum.text.toString()
                        .isNotEmpty() && etWriteViewingPartyParticipantMinimum.text.toString()
                        .isNotEmpty()
                ) {
                    if (etWriteViewingPartyParticipantMaximum.text.toString().replace(",", "")
                            .toInt() <=
                        etWriteViewingPartyParticipantMinimum.text.toString().replace(",", "")
                            .toInt()
                    ) {
                        showCustomSnackBar(binding.tvWriteGuide, "최소 인원이 최대 인원보다 많습니다")
                        return@setOnSingleClickListener
                    }
                }

                // 제목이나 본문 입력하지 않을 시 예외처리
                if (etWriteViewingPartyName.text.isEmpty() || etWriteViewingPartyPrice.text.isEmpty() ||
                    etWriteViewingPartyParticipantMinimum.text.isEmpty() || etWriteViewingPartyParticipantMaximum.text.isEmpty() ||
                    etWriteViewingPartyQualify.text.isEmpty() || tvWriteViewingPartyDate.text == "시간을 입력하세요" || viewingPartyMarker.map == null
                ) {
                    showCustomSnackBar(binding.tvWriteGuide, "필수 항목을 입력하지 않았습니다")
                    return@setOnSingleClickListener
                }

                // API 호출
                viewModel.writeViewingParty(
                    isEdit,
                    postId,
                    name = etWriteViewingPartyName.textToString(),
                    date = tvWriteViewingPartyDate.textToString(),
                    latitude = viewingPartyMarker.position.latitude,
                    longitude = viewingPartyMarker.position.longitude,
                    location = etWriteViewingPartyAddress.textToString(),
                    shortLocation = shortLocation,
                    price = etWriteViewingPartyPrice.textToString().trim(),
                    lowParticipate = etWriteViewingPartyParticipantMinimum.textToString().trim(),
                    highParticipate = etWriteViewingPartyParticipantMaximum.textToString().trim(),
                    qualify = etWriteViewingPartyQualify.textToString(),
                    etc = etWriteViewingPartyEtc.textToString()
                )
            }
        }
    }

    // 뷰잉파티 개최 장소 설정
    private fun setViewingPartyPlace() {
        binding.etWriteViewingPartyAddress.setOnEditorActionListener(EditorInfo.IME_ACTION_DONE) {
            Log.d("geoCoding", binding.etWriteViewingPartyAddress.text.toString())
            viewModel.fetchGeocoding(binding.etWriteViewingPartyAddress.text.toString())
        }
    }

    private fun closeWriteViewingParty() {
        binding.ivWriteClose.setOnSingleClickListener {
            finish()
        }
    }

    override fun onMapReady(p0: NaverMap) {
    }

    companion object {
        fun newIntent(context: Context) =
            Intent(context, WriteViewingPartyActivity::class.java)

        fun editIntent(context: Context, postId: Long, editViewingParty: WriteViewingPartyModel) =
            Intent(context, WriteViewingPartyActivity::class.java).apply {
                putExtra("postId", postId)
                putExtra("viewingParty", editViewingParty)
            }
    }
}