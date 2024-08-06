package umc.everyones.lck.presentation.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
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
        // Initialize observers if needed
    }

    override fun initView() {
        // Initialize views and set up listeners
        binding.tvMypageProfileEditText.setOnClickListener {
            findNavController().navigate(R.id.action_myPageProfileFragment_to_myPageProfileEditFragment)
        }

        binding.tvMypageProfileWithdrawText.setOnClickListener {
            findNavController().navigate(R.id.action_myPageProfileFragment_to_myPageProfileWithdrawFragment)
        }

        binding.tvMypageProfileLogoutText.setOnClickListener {
            showProfileDialog()
        }

        // Update UI with user's tier color
        updateTierUI()
    }

    private fun updateTierUI() {
        val tier = getUserTier()

        val tierBackgrounds = mapOf(
            "Bronze" to R.drawable.shape_circle_bronze,
            "Silver" to R.drawable.shape_circle_silver,
            "Gold" to R.drawable.shape_circle_gold,
            "Master" to R.drawable.shape_circle_master,
            "Challenger" to R.drawable.shape_circle_challenger
        )

        val tierStyles = mapOf(
            "Bronze" to R.style.TextAppearance_Bronze,
            "Silver" to R.style.TextAppearance_Silver,
            "Gold" to R.style.TextAppearance_Gold,
            "Master" to R.style.TextAppearance_Master,
            "Challenger" to R.style.TextAppearance_Challenger
        )

        // 기본 상태로 초기화 필요 없음

        // 사용자 티어에 해당하는 요소만 업데이트
        when (tier) {
            "Bronze" -> {
                binding.viewMypageProfileCircleBronze.setBackgroundResource(tierBackgrounds["Bronze"] ?: R.drawable.shape_circle_bronze)
                binding.tvMypageProfileBronzeText.setTextAppearance(requireContext(), tierStyles["Bronze"] ?: R.style.TextAppearance_LCK_Light_Detail_10sp)
                binding.tvMypageProfileTier.setTextAppearance(requireContext(), tierStyles["Bronze"] ?: R.style.TextAppearance_Bronze)
            }
            "Silver" -> {
                binding.viewMypageProfileCircleSilver.setBackgroundResource(tierBackgrounds["Silver"] ?: R.drawable.shape_circle_silver)
                binding.tvMypageProfileSilverText.setTextAppearance(requireContext(), tierStyles["Silver"] ?: R.style.TextAppearance_LCK_Light_Detail_10sp)
                binding.tvMypageProfileTier.setTextAppearance(requireContext(), tierStyles["Silver"] ?: R.style.TextAppearance_Silver)
            }
            "Gold" -> {
                binding.viewMypageProfileCircleGold.setBackgroundResource(tierBackgrounds["Gold"] ?: R.drawable.shape_circle_gold)
                binding.tvMypageProfileGoldText.setTextAppearance(requireContext(), tierStyles["Gold"] ?: R.style.TextAppearance_LCK_Light_Detail_10sp)
                binding.tvMypageProfileTier.setTextAppearance(requireContext(), tierStyles["Gold"] ?: R.style.TextAppearance_Gold)
            }
            "Master" -> {
                binding.viewMypageProfileCircleMaster.setBackgroundResource(tierBackgrounds["Master"] ?: R.drawable.shape_circle_master)
                binding.tvMypageProfileMasterText.setTextAppearance(requireContext(), tierStyles["Master"] ?: R.style.TextAppearance_LCK_Light_Detail_10sp)
                binding.tvMypageProfileTier.setTextAppearance(requireContext(), tierStyles["Master"] ?: R.style.TextAppearance_Master)
            }
            "Challenger" -> {
                binding.viewMypageProfileCircleChallenger.setBackgroundResource(tierBackgrounds["Challenger"] ?: R.drawable.shape_circle_challenger)
                binding.tvMypageProfileChallengerText.setTextAppearance(requireContext(), tierStyles["Challenger"] ?: R.style.TextAppearance_LCK_Light_Detail_10sp)
                binding.tvMypageProfileTier.setTextAppearance(requireContext(), tierStyles["Challenger"] ?: R.style.TextAppearance_Challenger)
            }
        }

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
