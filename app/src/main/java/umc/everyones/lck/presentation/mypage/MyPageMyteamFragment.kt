package umc.everyones.lck.presentation.mypage

import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentMypageMyteamBinding
import umc.everyones.lck.presentation.base.BaseFragment

@AndroidEntryPoint
class MyPageMyteamFragment : BaseFragment<FragmentMypageMyteamBinding>(R.layout.fragment_mypage_myteam) {

    private var isSecondLayoutVisible = false

    override fun initObserver() {

    }

    override fun initView() {
        // Back 버튼 클릭 이벤트
        binding.ivMypageMyteamBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        // Arrow 버튼 클릭 이벤트
        binding.btnMypageMyteamArrow.setOnClickListener {
            toggleSecondLayoutVisibility()
        }
    }

    private fun toggleSecondLayoutVisibility() {
        if (isSecondLayoutVisible) {
            binding.layoutMypageMyteamList.visibility = View.GONE
            binding.btnMypageMyteamArrow.setImageResource(R.drawable.ic_arrow_up)
        } else {
            binding.layoutMypageMyteamList.visibility = View.VISIBLE
            binding.btnMypageMyteamArrow.setImageResource(R.drawable.ic_arrow_down)
        }
        isSecondLayoutVisible = !isSecondLayoutVisible
    }
}
