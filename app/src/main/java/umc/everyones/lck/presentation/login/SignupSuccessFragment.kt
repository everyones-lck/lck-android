package umc.everyones.lck.presentation.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentSignupSuccessBinding
import umc.everyones.lck.presentation.MainActivity
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.util.NicknameManager
import umc.everyones.lck.util.TeamData
import androidx.navigation.fragment.navArgs

@AndroidEntryPoint
class SignupSuccessFragment : BaseFragment<FragmentSignupSuccessBinding>(R.layout.fragment_signup_success) {

    private val args: SignupSuccessFragmentArgs by navArgs()

    override fun initObserver() {
        // No observers needed here
    }

    override fun initView() {
        val nicknameManager = NicknameManager(requireContext())

        // Retrieve arguments using Safe Args
        val selectedTeam = args.selectedTeam
        val nickname = args.nickname
        val profileImageUri = args.profileImageUri

        // Save nickname
        nicknameManager.addNickname(nickname)

        binding.tvSignupSuccessCongratulation.text = "$nickname 님 가입을 축하드립니다!"

        // Set team logo
        if (selectedTeam != null) {
            val teamLogoResId = TeamData.getSignupSuccessTeamLogo(selectedTeam)
            binding.ivSignupSuccessBackgroundLogo.setImageResource(teamLogoResId)
        } else {
            binding.ivSignupSuccessBackgroundLogo.setImageResource(android.R.color.transparent)
        }

        // Load profile image or set default
        if (profileImageUri != null) {
            Glide.with(this)
                .load(Uri.parse(profileImageUri))
                .placeholder(R.drawable.img_signup_profile) // Default image
                .into(binding.ivSignupSuccessProfile)
        } else {
            binding.ivSignupSuccessProfile.setImageResource(R.drawable.img_signup_profile) // Default image
        }

        // Handle Next button click
        binding.ivSignupSuccessNext.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}
