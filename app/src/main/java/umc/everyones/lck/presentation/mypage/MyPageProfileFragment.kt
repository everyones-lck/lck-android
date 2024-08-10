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

    private val navigator by lazy { findNavController() }

    override fun initObserver() {
        // Initialize observers if needed
    }

    override fun initView() {
        // Initialize views and set up listeners
        binding.tvMypageProfileEditText.setOnClickListener {
            navigator.navigate(R.id.action_myPageProfileFragment_to_myPageProfileEditFragment)
        }

        binding.tvMypageProfileWithdrawText.setOnClickListener {
            navigator.navigate(R.id.action_myPageProfileFragment_to_myPageProfileWithdrawFragment)
        }

        binding.tvMypageProfileLogoutText.setOnClickListener {
            showProfileDialog()
        }
        binding.ivMypageProfileBack.setOnClickListener{
            navigator.navigateUp()
        }

        // Update UI with user's tier color
        updateTierUI()
    }

    private fun updateTierUI() {
        val tier = getUserTier()

        val tierBackgrounds = mapOf(
            "Bronze" to R.drawable.shape_oval_bronze,
            "Silver" to R.drawable.shape_oval_silver,
            "Gold" to R.drawable.shape_oval_gold,
            "Master" to R.drawable.shape_oval_master,
            "Challenger" to R.drawable.shape_oval_challenger
        )

        val tierStyles = mapOf(
            "Bronze" to R.style.TextAppearance_Bronze,
            "Silver" to R.style.TextAppearance_Silver,
            "Gold" to R.style.TextAppearance_Gold,
            "Master" to R.style.TextAppearance_Master,
            "Challenger" to R.style.TextAppearance_Challenger
        )

        // 사용자 티어에 해당하는 요소만 업데이트
        binding.viewMypageProfileCircleBronze.setBackgroundResource(tierBackgrounds[tier]!!)
        binding.tvMypageProfileBronzeText.setTextAppearance(requireContext(), tierStyles[tier]!!)
        binding.tvMypageProfileTier.setTextAppearance(requireContext(), tierStyles[tier]!!)

        // 티어 텍스트 업데이트
        binding.tvMypageProfileTier.text = tier
    }


    private fun getUserTier(): String {
        // Fetch the user tier from LoginManager or other source
        // Here, we are assuming LoginManager has a method to get the tier
        val loginManager = LoginManager(requireContext())
        return loginManager.getUserTier() ?: "Bronze"
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

        // Cancel button
        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        // Logout button
        dialogBinding.btnConfirm.setOnClickListener {
            dialog.dismiss()
            val loginManager = LoginManager(requireContext())
            loginManager.clearLogin()

            // Navigate to login activity
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}
