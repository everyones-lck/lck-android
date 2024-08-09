// MyPageFragment.kt
package umc.everyones.lck.presentation.mypage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentMypageMainBinding
import umc.everyones.lck.presentation.MainActivity
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.util.NicknameManager
import umc.everyones.lck.util.TeamData

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageMainBinding>(R.layout.fragment_mypage_main) {

    private val teamLogos = TeamData.mypageTeamBackground

    override fun initObserver() {
        // No observers needed here
    }

    override fun initView() {

        val nicknameManager = NicknameManager(requireContext())

        // Arguments에서 데이터 가져오기
        val selectedTeam = arguments?.getString("selectedTeam")
        val nickname = arguments?.getString("nickname")
        val profileImageUri = arguments?.getString("profileImageUri")

        // UI 업데이트
        binding.tvMypageMainNickname.text = nickname

        val teamLogoResId = teamLogos[selectedTeam] ?: android.R.color.transparent
        binding.ivMypageMainTeamBackground.setImageResource(teamLogoResId)

        if (profileImageUri != null) {
            Glide.with(this)
                .load(Uri.parse(profileImageUri))
                .placeholder(R.drawable.img_signup_profile) // 기본 이미지
                .into(binding.ivMypageMainProfile)
        } else {
            binding.ivMypageMainProfile.setImageResource(R.drawable.img_signup_profile) // 기본 이미지
        }

        // 뒤로가기 버튼 클릭 시 MainActivity로 이동
        binding.ivMypageMainBack.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            findNavController().navigateUp()
        }

        // 프로필 텍스트 클릭 시 MyPageProfileFragment로 이동
        binding.tvMypageMainMyprofileText.setOnClickListener {
            findNavController().navigate(R.id.action_myPageFragment_to_myPageProfileFragment)
        }

        // 마이팀 텍스트 클릭 시 MyPageMyteamFragment로 이동
        binding.tvMypageMainMyteamText.setOnClickListener {
            findNavController().navigate(R.id.action_myPageFragment_to_myPageMyteamFragment)
        }

        binding.tvMypageMainCommunityText.setOnClickListener{
            findNavController().navigate(R.id.action_myPageFragment_to_myPageCommunityFragment)
        }

        binding.tvMypageMainViewingPartyText.setOnClickListener{
            findNavController().navigate(R.id.action_myPageFragment_to_myPageViewingPartyFragment)
        }
    }
}
