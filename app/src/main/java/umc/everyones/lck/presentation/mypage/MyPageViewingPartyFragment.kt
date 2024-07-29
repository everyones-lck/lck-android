package umc.everyones.lck.presentation.mypage

import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentMypageViewingPartyBinding
import umc.everyones.lck.presentation.base.BaseFragment

class MyPageViewingPartyFragment :BaseFragment<FragmentMypageViewingPartyBinding>(R.layout.fragment_mypage_viewing_party){

    override fun initObserver() {

    }

    override fun initView() {

        binding.ivMypageViewingPartyBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}