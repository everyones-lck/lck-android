package umc.everyones.lck.presentation.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import umc.everyones.lck.R
import umc.everyones.lck.User
import umc.everyones.lck.databinding.FragmentSignupSuccessBinding
import umc.everyones.lck.presentation.MainActivity
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.util.TeamData

@AndroidEntryPoint
class SignupSuccessFragment : BaseFragment<FragmentSignupSuccessBinding>(R.layout.fragment_signup_success) {

    private val viewModel: SignupViewModel by activityViewModels()

    override fun initObserver() {
        // No observers needed for this fragment
    }

    override fun initView() {
        // 번들로 전달된 데이터 추출
        val profileImageUri = arguments?.getString("profileImageUri") ?: ""
        val teamName = arguments?.getString("teamName") ?: "default_team" // 기본값 처리

        // ViewModel에서 nickname 값 가져오기
        val nickname = viewModel.nickname.value ?: ""

        Log.d("SignupSuccessFragment", "Received profileImageUri: $profileImageUri, teamName: $teamName, nickname: $nickname")

        // 사용자 정보 로드 및 화면 표시
        lifecycleScope.launch {
            if (nickname.isNotEmpty()) {
                val user = viewModel.getUser(nickname)
                if (user != null) {
                    displayUserInfo(user, teamName)
                } else {
                    Log.e("SignupSuccessFragment", "User not found for nickname: $nickname")
                }
            } else {
                Log.e("SignupSuccessFragment", "Nickname is empty")
            }
        }

        // Handle Next button click
        binding.ivSignupSuccessNext.setOnClickListener {
            Intent(requireContext(), MainActivity::class.java).apply {
                startActivity(this)
            }
            requireActivity().finish()
        }
    }

    private fun displayUserInfo(user: User, teamName: String) {
        Log.d("SignupSuccessFragment", "User: $user")

        // 닉네임을 텍스트에 반영
        binding.tvSignupSuccessCongratulation.text = "${user.nickname}님 가입을 축하드립니다!"

        // 프로필 이미지 로드
        if (user.profileUri.isNotEmpty()) {
            Glide.with(this)
                .load(Uri.parse(user.profileUri))
                .placeholder(R.drawable.img_signup_profile)
                .into(binding.ivSignupSuccessProfile)
        } else {
            binding.ivSignupSuccessProfile.setImageResource(R.drawable.img_signup_profile)
        }
    }
}
