package umc.everyones.lck.presentation.party

import android.content.Context
import android.content.Intent
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import umc.everyones.lck.R
import umc.everyones.lck.databinding.ActivityWriteViewingPartyBinding
import umc.everyones.lck.presentation.base.BaseActivity
import umc.everyones.lck.presentation.community.WritePostActivity
import umc.everyones.lck.util.extension.addDecimalFormattedTextWatcher
import umc.everyones.lck.util.extension.showCustomSnackBar
import umc.everyones.lck.util.extension.validateMaxLength
import java.text.DecimalFormat

class WriteViewingPartyActivity : BaseActivity<ActivityWriteViewingPartyBinding>(R.layout.activity_write_viewing_party),
    OnMapReadyCallback {
    private var naverMap: NaverMap? = null
    override fun initObserver() {

    }

    override fun initView() {
        initNaverMap()
        addDecimalFormat()
        validateViewingPartyName()
        validateViewingPartyCondition()
        validateViewingPartyEtc()
        writeDone()
        binding.ivWriteClose.setOnClickListener {
            finish()
        }
    }

    private fun validateViewingPartyName() {
        binding.etWriteViewingPartyName.validateMaxLength(this, 20,
            onLengthExceeded = {
                showCustomSnackBar(
                    binding.etWriteViewingPartyName,
                    "이름은 최대 20자까지 입력할 수 있어요"
                )
            }
        )
    }

    private fun validateViewingPartyCondition() {
        binding.etWriteViewingPartyCondition.validateMaxLength(this, 100,
            onLengthExceeded = {
                showCustomSnackBar(
                    binding.etWriteViewingPartyCondition,
                    "참여 자격 및 조건은 최대 100자까지 입력할 수 있어요"
                )
            }
        )
    }

    private fun validateViewingPartyEtc() {
        binding.etWriteViewingPartyEtc.validateMaxLength(this, 1000,
            onLengthExceeded = {
                showCustomSnackBar(
                    binding.etWriteViewingPartyEtc,
                    "기타 항목은 최대 1000자까지 입력할 수 있어요"
                )
            }
        )
    }

    private fun addDecimalFormat(){
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
                if(etWriteViewingPartyName.text.isEmpty() || etWriteViewingPartyMoney.text.isEmpty() ||
                    etWriteViewingPartyParticipantMinimum.text.isEmpty() || etWriteViewingPartyParticipantMaximum.text.isEmpty() ||
                    etWriteViewingPartyCondition.text.isEmpty()){
                    showCustomSnackBar(binding.tvWriteGuide, "필수 항목을 입력하지 않았습니다")
                    return@setOnClickListener
                }

                finish()
            }
        }
    }

    override fun onMapReady(p0: NaverMap) {

    }

    companion object {
        fun newIntent(context: Context) =
            Intent(context, WriteViewingPartyActivity::class.java)
    }
}