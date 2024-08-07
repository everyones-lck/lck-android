package umc.everyones.lck.presentation.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentSignupSuccessBinding
import umc.everyones.lck.presentation.MainActivity
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.util.NicknameManager
import umc.everyones.lck.util.TeamData
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import umc.everyones.lck.User

@AndroidEntryPoint
class SignupSuccessFragment : BaseFragment<FragmentSignupSuccessBinding>(R.layout.fragment_signup_success) {

    private val viewModel: SignupViewModel by viewModels()
    private lateinit var nicknameManager: NicknameManager
    private val nickname: String by lazy {
        arguments?.getString("nickname") ?: ""
    }

    override fun initObserver() {
        // No observers needed for this fragment
    }

    override fun initView() {
        nicknameManager = NicknameManager(requireContext())

        // Fetch user details from ViewModel
        lifecycleScope.launch {
            val user = viewModel.getUser(nickname)
            if (user != null) {
                displayUserInfo(user)
            }
        }

        // Handle Next button click
        binding.ivSignupSuccessNext.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun displayUserInfo(user: User) {
        Log.d("SignupSuccessFragment", "User: $user")
        // Display user info
        binding.tvSignupSuccessCongratulation.text = "${user.name} 님 가입을 축하드립니다!"

        // Set team logo
        val teamLogoResId = TeamData.getSignupSuccessTeamLogo(user.team)
        binding.ivSignupSuccessBackgroundLogo.setImageResource(teamLogoResId)

        // Load profile image or set default
        if (user.profileUri.isNotEmpty()) {
            Glide.with(this)
                .load(Uri.parse(user.profileUri))
                .placeholder(R.drawable.img_signup_profile) // Default image
                .into(binding.ivSignupSuccessProfile)
        } else {
            binding.ivSignupSuccessProfile.setImageResource(R.drawable.img_signup_profile) // Default image
        }
    }
}
