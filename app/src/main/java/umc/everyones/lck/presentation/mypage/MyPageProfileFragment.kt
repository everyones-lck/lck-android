package umc.everyones.lck.presentation.mypage

import android.content.Intent
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.DialogMypageProfileLogoutBinding
import umc.everyones.lck.databinding.FragmentMypageProfileBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.login.LoginActivity
import umc.everyones.lck.util.LoginManager

@AndroidEntryPoint
class MyPageProfileFragment : BaseFragment<FragmentMypageProfileBinding>(R.layout.fragment_mypage_profile) {

    override fun initObserver() {
        // No observers needed here
    }

    override fun initView() {

        // 뒤로가기 버튼 클릭 시 이동
        binding.ivMypageProfileBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        // 프로필 수정 버튼 클릭 시 MyPageProfileEditFragment로 이동
        binding.tvMypageProfileEditText.setOnClickListener {
            findNavController().navigate(R.id.action_myPageProfileFragment_to_myPageProfileEditFragment)
        }

        // 계정 탈퇴 버튼 클릭 시 MyPageProfileWithdrawFragment로 이동
        binding.tvMypageProfileWithdrawText.setOnClickListener {
            findNavController().navigate(R.id.action_myPageProfileFragment_to_myPageProfileWithdrawFragment)
        }

        // 로그아웃 버튼 클릭 시 다이얼로그 표시
        binding.tvMypageProfileLogoutText.setOnClickListener {
            showProfileDialog()
        }
    }

    private fun showProfileDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_mypage_profile_logout, null)
        val dialogBinding = DialogMypageProfileLogoutBinding.bind(dialogView)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        val layoutParams = dialog.window?.attributes
        layoutParams?.dimAmount = 0.8f
        dialog.window?.attributes = layoutParams

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnConfirm.setOnClickListener {
            dialog.dismiss()
            val loginManager = LoginManager(requireContext())
            loginManager.clearLogin()

            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}
