package umc.everyones.lck.presentation.mypage

import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentMypageMyteamBinding
import umc.everyones.lck.presentation.base.BaseFragment

@AndroidEntryPoint
class MyPageMyteamFragment : BaseFragment<FragmentMypageMyteamBinding>(R.layout.fragment_mypage_myteam) {
    override fun initObserver() {

    }

    override fun initView() {

        binding.ivMypageMyteamBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}