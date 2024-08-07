package umc.everyones.lck.presentation.party

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.MarkerIcons
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.ActivityWriteViewingPartyBinding
import umc.everyones.lck.presentation.base.BaseActivity
import umc.everyones.lck.presentation.community.WritePostActivity
import umc.everyones.lck.util.extension.addDecimalFormattedTextWatcher
import umc.everyones.lck.util.extension.repeatOnStarted
import umc.everyones.lck.util.extension.setOnEditorActionListener
import umc.everyones.lck.util.extension.showCustomSnackBar
import umc.everyones.lck.util.extension.showKeyboard
import umc.everyones.lck.util.extension.validateMaxLength
import java.text.DecimalFormat

@AndroidEntryPoint
class WriteViewingPartyActivity :
    BaseActivity<ActivityWriteViewingPartyBinding>(R.layout.activity_write_viewing_party),
    OnMapReadyCallback {
    private var naverMap: NaverMap? = null

    private val viewModel: WriteViewingPartyViewModel by viewModels()
    override fun initObserver() {
        repeatOnStarted {
            viewModel.latLng.collect{ latLng ->
                val marker = Marker()
                marker.apply {
                    position = latLng
                    icon = OverlayImage.fromResource(R.drawable.img_marker)
                    map = naverMap
                }
                naverMap?.moveCamera(CameraUpdate.scrollTo(latLng))
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {
        initNaverMap()
        addDecimalFormat()
        setViewingPartyPlace()
        validateViewingPartyName()
        validateViewingPartyCondition()
        validateViewingPartyEtc()
        showKeyBoard()
        writeDone()
        binding.ivWriteClose.setOnClickListener {
            finish()
        }

        binding.tvWriteViewingPartyDate.setOnClickListener {
            val dialog = CalendarDialogFragment()
            dialog.showBelowView(binding.tvWriteViewingPartyDate)
            dialog.show(supportFragmentManager, dialog.tag)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    private fun showKeyBoard(){
        with(binding){
            // 비용 입력
            layoutWriteViewingPartyMoney.setOnClickListener {
                etWriteViewingPartyMoney.showKeyboard()
            }

            // 최소 인원 입력
            layoutWriteViewingPartyParticipantMinimum.setOnClickListener {
                etWriteViewingPartyParticipantMinimum.showKeyboard()
            }

            // 최대 인원 입력
            layoutWriteViewingPartyParticipantMaximum.setOnClickListener {
                etWriteViewingPartyParticipantMaximum.showKeyboard()
            }
        }
    }

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

    private fun validateViewingPartyCondition() {
        with(binding) {
            etWriteViewingPartyCondition.validateMaxLength(this@WriteViewingPartyActivity, 100,
                onLengthExceeded = {
                    showCustomSnackBar(
                        etWriteViewingPartyCondition,
                        "참여 자격 및 조건은 최대 100자까지 입력할 수 있어요"
                    )
                }
            )
        }
    }

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

    private fun addDecimalFormat() {
        binding.etWriteViewingPartyMoney.addDecimalFormattedTextWatcher(this)
        binding.etWriteViewingPartyParticipantMaximum.addDecimalFormattedTextWatcher(this)
        binding.etWriteViewingPartyParticipantMinimum.addDecimalFormattedTextWatcher(this)
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
        }
    }

    private fun writeDone() {
        with(binding) {
            ivWriteDone.setOnClickListener {

                // 제목이나 본문 입력하지 않을 시 예외처리
                if (etWriteViewingPartyName.text.isEmpty() || etWriteViewingPartyMoney.text.isEmpty() ||
                    etWriteViewingPartyParticipantMinimum.text.isEmpty() || etWriteViewingPartyParticipantMaximum.text.isEmpty() ||
                    etWriteViewingPartyCondition.text.isEmpty()
                ) {
                    showCustomSnackBar(binding.tvWriteGuide, "필수 항목을 입력하지 않았습니다")
                    return@setOnClickListener
                }

                finish()
            }
        }
    }

    private fun setViewingPartyPlace(){
        binding.etWriteViewingPartyAddress.setOnEditorActionListener(EditorInfo.IME_ACTION_DONE) {
            Log.d("geoCoding", binding.etWriteViewingPartyAddress.text.toString())
            viewModel.fetchGeoCoding(binding.etWriteViewingPartyAddress.text.toString())
        }
    }

    override fun onMapReady(p0: NaverMap) {

    }

    companion object {
        fun newIntent(context: Context) =
            Intent(context, WriteViewingPartyActivity::class.java)
    }
}