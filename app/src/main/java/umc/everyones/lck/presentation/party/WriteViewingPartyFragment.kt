package umc.everyones.lck.presentation.party

import android.util.Log
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.widget.ZoomControlView
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentWriteViewingPartyBinding
import umc.everyones.lck.presentation.base.BaseFragment

class WriteViewingPartyFragment : BaseFragment<FragmentWriteViewingPartyBinding>(R.layout.fragment_write_viewing_party), OnMapReadyCallback {
    private var naverMap: NaverMap? = null
    override fun initObserver() {

    }

    override fun initView() {
        initNaverMap()
    }

    private fun initNaverMap(){
        val fm = childFragmentManager
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
}