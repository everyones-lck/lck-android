package umc.everyones.lck.presentation.mypage

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentMypageProfileWithdrawBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.login.LoginActivity
import umc.everyones.lck.util.extension.setOnSingleClickListener
import umc.everyones.lck.util.extension.showCustomSnackBar
import umc.everyones.lck.util.extension.showCustomToast

@AndroidEntryPoint
class MyPageProfileWithdrawFragment : BaseFragment<FragmentMypageProfileWithdrawBinding>(R.layout.fragment_mypage_profile_withdraw) {

    private val myPageViewModel: MyPageViewModel by activityViewModels()

    override fun initObserver() {
        myPageViewModel.withdrawResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                Timber.d("회원 탈퇴 후 로그인 화면으로 이동")
                navigateToLoginScreen()
            } else {
                Timber.e("회원 탈퇴 실패, 오류 처리 필요")
                requireContext().showCustomToast("계정 탈퇴에 실패했습니다. 다시 시도해주세요.")
            }
        }
    }

    override fun initView() {
        // 뒤로가기 버튼 클릭 시 이동
        binding.ivMypageProfileWithdrawBack.setOnSingleClickListener {
            findNavController().navigateUp()
        }

        binding.tvMypageProfileWithdraw.setOnSingleClickListener {
            myPageViewModel.withdraw()
        }
    }

    private fun navigateToLoginScreen() {
        showCustomSnackBar(binding.root, "계정 탈퇴 되었습니다")
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }, 2000)
    }
}