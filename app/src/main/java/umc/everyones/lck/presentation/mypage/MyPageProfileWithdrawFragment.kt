package umc.everyones.lck.presentation.mypage

import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentMypageProfileWithdrawBinding
import umc.everyones.lck.presentation.base.BaseFragment

@AndroidEntryPoint
class MyPageProfileWithdrawFragment : BaseFragment<FragmentMypageProfileWithdrawBinding>(R.layout.fragment_mypage_profile_withdraw){
    override fun initObserver() {

    }

    override fun initView() {
        // 뒤로가기 버튼 클릭 시 이동
        binding.ivMypageProfileWithdrawBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}