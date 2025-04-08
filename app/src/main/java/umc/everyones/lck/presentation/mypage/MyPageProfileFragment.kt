package umc.everyones.lck.presentation.mypage

import android.content.Intent
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.DialogMypageProfileLogoutBinding
import umc.everyones.lck.databinding.FragmentMypageProfileBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.login.LoginActivity
import umc.everyones.lck.util.TeamData
import umc.everyones.lck.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class MyPageProfileFragment : BaseFragment<FragmentMypageProfileBinding>(R.layout.fragment_mypage_profile) {

    private val myPageViewModel: MyPageViewModel by activityViewModels()
    private val teamLogos = TeamData.mypageTeamBackground
    private val navigator by lazy { findNavController() }


    override fun initObserver() {
        myPageViewModel.inquiryProfile()
        myPageViewModel.profileData.observe(viewLifecycleOwner) { profile ->
            profile?.let {
                binding.tvMypageProfileNickname.text = it.nickname // 닉네임 설정
                binding.tvMypageProfileMyTier.text = it.tier // 티어 설정

                // 팀 로고 설정 -> 수정 필요
                val teamBackgroundResId = teamLogos[it.teamId] ?: R.drawable.img_mypage_empty_background
                binding.tvMypageProfileTeam.setBackgroundResource(teamBackgroundResId)

                loadProfileImage(it.profileImageUrl) // 프로필 이미지 로드

                updateTierUI(it.tier)
            }
        }
    }

    override fun initView() {

        binding.tvMypageProfileEditText.setOnSingleClickListener {
            navigator.navigate(R.id.action_myPageProfileFragment_to_myPageProfileEditFragment)
        }

        binding.tvMypageProfileWithdrawText.setOnSingleClickListener {
            navigator.navigate(R.id.action_myPageProfileFragment_to_myPageProfileWithdrawFragment)
        }

        binding.tvMypageProfileLogoutText.setOnSingleClickListener {
            showProfileDialog()
        }
        binding.ivMypageProfileBack.setOnSingleClickListener {
            navigator.navigate(R.id.action_myPageProfileFramgnet_to_myPageFragment)
        }
    }

    private fun updateTierUI(tier: String) {
        val tierBackgrounds = mapOf(
            "bronze" to R.drawable.shape_oval_bronze,
            "Silver" to R.drawable.shape_oval_silver,
            "Gold" to R.drawable.shape_oval_gold,
            "Master" to R.drawable.shape_oval_master,
            "Challenger" to R.drawable.shape_oval_challenger
        )

        val tierStyles = mapOf(
            "bronze" to R.style.TextAppearance_Bronze,
            "Silver" to R.style.TextAppearance_Silver,
            "Gold" to R.style.TextAppearance_Gold,
            "Master" to R.style.TextAppearance_Master,
            "Challenger" to R.style.TextAppearance_Challenger
        )

        // 사용자 티어에 해당하는 요소만 업데이트
        binding.viewMypageProfileCircleBronze.setBackgroundResource(tierBackgrounds[tier]!!)
        binding.tvMypageProfileBronzeText.setTextAppearance(requireContext(), tierStyles[tier]!!)
        binding.tvMypageProfileMyTier.setTextAppearance(requireContext(), tierStyles[tier]!!)

        // 티어 텍스트 업데이트
        binding.tvMypageProfileMyTier.text = tier
    }

    private fun showProfileDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_mypage_profile_logout, null)
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
        dialogBinding.btnCancel.setOnSingleClickListener {
            dialog.dismiss()
        }

        // Logout button
        dialogBinding.btnConfirm.setOnSingleClickListener {
            dialog.dismiss()
            myPageViewModel.logout()

            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun loadProfileImage(uri: String?) {
        uri?.let {
            Glide. with(this)
                .load(it)
                .placeholder(R.drawable.img_signup_profile) // 기본 이미지
                .into(binding.ivMypageProfileProfile) // 프로필 이미지 뷰에 로드
        } ?: run {
            binding.ivMypageProfileProfile.setImageResource(R.drawable.img_signup_profile) // 기본 이미지 설정
        }
    }
}
