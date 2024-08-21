package umc.everyones.lck.presentation.mypage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentMypageMainBinding
import umc.everyones.lck.presentation.MainActivity
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.login.SignupViewModel
import umc.everyones.lck.util.TeamData

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageMainBinding>(R.layout.fragment_mypage_main) {

    private val myPageViewModel: MyPageViewModel by activityViewModels()
    private val signupViewModel: SignupViewModel by activityViewModels()
    private val teamLogos = TeamData.mypageTeamBackground
    private val navigator by lazy { findNavController() }

    override fun initObserver() {
        // 사용자 정보를 관찰
        myPageViewModel.user.observe(viewLifecycleOwner) { user ->
            Log.d("MyPageFragment", "User observed: $user")
            if (user != null) {
                binding.tvMypageMainNickname.text = user.nickname
                binding.tvMypageMainTier.text = user.tier

                val teamLogoResId = teamLogos[user.team] ?: android.R.color.transparent
                binding.ivMypageMainTeamBackground.setImageResource(teamLogoResId)

                if (user.profileUri.isNotEmpty()) {
                    Glide.with(this@MyPageFragment)
                        .load(Uri.parse(user.profileUri))
                        .placeholder(R.drawable.img_signup_profile)
                        .into(binding.ivMypageMainProfile)
                } else {
                    binding.ivMypageMainProfile.setImageResource(R.drawable.img_signup_profile)
                }
            } else {
                Log.d("MyPageFragment", "No user found")
                binding.tvMypageMainNickname.text = "Unknown"
                binding.tvMypageMainTier.text = "Bronze"
                binding.ivMypageMainTeamBackground.setImageResource(android.R.color.transparent)
                binding.ivMypageMainProfile.setImageResource(R.drawable.img_signup_profile)
            }
        }

        // 프로필 데이터를 관찰
        myPageViewModel.profileData.observe(viewLifecycleOwner) { profile ->
            profile?.let {
                binding.tvMypageMainNickname.text = it.nickname
                binding.tvMypageMainTier.text = it.tier

                val teamLogoResId = teamLogos[it.teamId] ?: android.R.color.transparent
                binding.ivMypageMainTeamBackground.setImageResource(teamLogoResId)

                Glide.with(this@MyPageFragment)
                    .load(Uri.parse(it.profileImageUrl))
                    .placeholder(R.drawable.img_signup_profile)
                    .into(binding.ivMypageMainProfile)
            } ?: run {
                Log.d("MyPageFragment", "No profile data found")
                binding.tvMypageMainNickname.text = "Unknown"
                binding.tvMypageMainTier.text = "Bronze"
                binding.ivMypageMainTeamBackground.setImageResource(android.R.color.transparent)
                binding.ivMypageMainProfile.setImageResource(R.drawable.img_signup_profile)
            }
        }
    }

    override fun initView() {
        val nickname = arguments?.getString("nickname")

        // ViewModel에 nickname 설정하기
        nickname?.let {
            myPageViewModel.inquiryProfile() // 프로필 조회 호출
        }

        binding.ivMypageMainBack.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            navigator.navigateUp()
        }

        binding.tvMypageMainMyprofileText.setOnClickListener {
            navigator.navigate(R.id.action_myPageFragment_to_myPageProfileFragment)
        }

        binding.tvMypageMainMyteamText.setOnClickListener {
            navigator.navigate(R.id.action_myPageFragment_to_myPageMyteamFragment)
        }

        binding.tvMypageMainCommunityText.setOnClickListener {
            navigator.navigate(R.id.action_myPageFragment_to_myPageCommunityFragment)
        }

        binding.tvMypageMainViewingPartyText.setOnClickListener {
            navigator.navigate(R.id.action_myPageFragment_to_myPageViewingPartyFragment)
        }
    }
}
