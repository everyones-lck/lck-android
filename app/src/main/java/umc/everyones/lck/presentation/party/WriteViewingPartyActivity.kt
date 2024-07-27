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
import umc.everyones.lck.util.extension.addDecimalFormattedTextWatcher
import java.text.DecimalFormat

class WriteViewingPartyActivity : BaseActivity<ActivityWriteViewingPartyBinding>(R.layout.activity_write_viewing_party),
    OnMapReadyCallback {
    private var naverMap: NaverMap? = null
    override fun initObserver() {

    }

    override fun initView() {
        initNaverMap()
        addDecimalFormat()
        binding.ivWriteClose.setOnClickListener {
            finish()
        }
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

    override fun onMapReady(p0: NaverMap) {

    }

    companion object {
        private val decimalFormat = DecimalFormat("#,###")
        fun newIntent(context: Context) =
            Intent(context, WriteViewingPartyActivity::class.java)
    }
}