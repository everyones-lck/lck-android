package umc.everyones.lck.presentation.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
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

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {navigator.navigate(R.id.action_myPageProfileFramgnet_to_myPageFragment)} }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun initObserver() {
        myPageViewModel.inquiryProfile()
        myPageViewModel.profileData.observe(viewLifecycleOwner) { profile ->
            profile?.let {
                binding.tvMypageProfileNickname.text = it.nickname // 닉네임 설정
                binding.tvMypageProfileMyTier.text = it.tier // 티어 설정
                val teamId = it.teamId ?: 1

                // 팀 이름 설정
                binding.tvMypageProfileTeam.text = TeamData.teamNames[teamId] ?: TeamData.teamNames[1] // teamNames에 없으면 기본값 사용

                // 팀 배경 설정
                val teamBackground = TeamData.mypageProfileTeamBackground[teamId]
                teamBackground?.let { colorResId ->
                    binding.tvMypageProfileTeam.setBackgroundResource(teamBackground)                }

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
        binding.ivMypageProfileSetting.setOnSingleClickListener {
            navigator.navigate(R.id.action_myPageProfileFragment_to_myPageInformationFragment)
        }
    }

    private fun updateTierUI(tier: String) {
        val tierBackgrounds = mapOf(
            "Bronze" to R.drawable.shape_oval_bronze,
            "Silver" to R.drawable.shape_oval_silver,
            "Gold" to R.drawable.shape_oval_gold,
            "Master" to R.drawable.shape_oval_master,
            "Challenger" to R.drawable.shape_oval_challenger
        )

        val tierStyles = mapOf(
            "Bronze" to R.style.TextAppearance_LCK_Medium_Bronze,
            "Silver" to R.style.TextAppearance_LCK_Medium_Silver,
            "Gold" to R.style.TextAppearance_LCK_Medium_Gold,
            "Master" to R.style.TextAppearance_LCK_Medium_Master,
            "Challenger" to R.style.TextAppearance_LCK_Medium_Challenger
        )

        when (tier) {
            "Bronze" -> {
                binding.viewMypageProfileCircleBronze.setBackgroundResource(tierBackgrounds[tier]!!)
                binding.tvMypageProfileBronzeText.setTextAppearance(requireContext(), tierStyles[tier]!!)
            }
            "Silver" -> {
                binding.viewMypageProfileCircleSilver.setBackgroundResource(tierBackgrounds[tier]!!)
                binding.tvMypageProfileSilverText.setTextAppearance(requireContext(), tierStyles[tier]!!)
            }
            "Gold" -> {
                binding.viewMypageProfileCircleGold.setBackgroundResource(tierBackgrounds[tier]!!)
                binding.tvMypageProfileGoldText.setTextAppearance(requireContext(), tierStyles[tier]!!)
            }
            "Master" -> {
                binding.viewMypageProfileCircleMaster.setBackgroundResource(tierBackgrounds[tier]!!)
                binding.tvMypageProfileMasterText.setTextAppearance(requireContext(), tierStyles[tier]!!)
            }
            "Challenger" -> {
                binding.viewMypageProfileCircleChallenger.setBackgroundResource(tierBackgrounds[tier]!!)
                binding.tvMypageProfileChallengerText.setTextAppearance(requireContext(), tierStyles[tier]!!)
            }
        }

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
