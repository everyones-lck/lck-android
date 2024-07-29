package umc.everyones.lck.presentation.mypage

import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentMypageCommunityBinding
import umc.everyones.lck.presentation.base.BaseFragment

@AndroidEntryPoint
class MyPageCommunityFragment : BaseFragment<FragmentMypageCommunityBinding>(R.layout.fragment_mypage_community){

    override fun initObserver() {

    }

    override fun initView() {

        binding.ivMypageCommunityBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}