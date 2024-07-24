package umc.everyones.lck.presentation.mypage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentMypageMainBinding
import umc.everyones.lck.presentation.MainActivity
import umc.everyones.lck.util.NicknameManager

@AndroidEntryPoint
class MyPageFragment : Fragment(R.layout.fragment_mypage_main) {
    private var _binding: FragmentMypageMainBinding? = null
    private val binding get() = _binding!!

    private val teamLogos = mapOf(
        "Hanhwa" to R.drawable.img_mypage_hanhwa_background,
        "Gen.G" to R.drawable.img_mypage_gen_g_background,
        "T1" to R.drawable.img_mypage_t1_background,
        "Kwangdong Freecs" to R.drawable.img_mypage_kwangdong_background,
        "BNK" to R.drawable.img_mypage_bnk_background,
        "Nongshim Red Force" to R.drawable.img_mypage_nongshim_red_force_background,
        "DRX" to R.drawable.img_mypage_drx_background,
        "OK Saving Bank Brion" to R.drawable.img_mypage_ok_saving_bank_biron_background,
        "Dplus Kia" to R.drawable.img_mypage_dplus_kia_background,
        "KT Rolster" to R.drawable.img_mypage_kt_rolster_background
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMypageMainBinding.bind(view)

        val nicknameManager = NicknameManager(requireContext())

        val selectedTeam = arguments?.getString("selectedTeam")
        val nickname = arguments?.getString("nickname") ?: "닉네임 없음"
        val profileImageUri = arguments?.getString("profileImageUri")

        // 닉네임을 저장
        nicknameManager.addNickname(nickname)

        binding.tvMypageMainNickname.text = "$nickname"

        if (selectedTeam != null) {
            val teamLogoResId = teamLogos[selectedTeam] ?: android.R.color.transparent
            binding.ivMypageMainTeamBackground.setImageResource(teamLogoResId)
        } else {
            binding.ivMypageMainTeamBackground.setImageResource(android.R.color.transparent)
        }

        if (profileImageUri != null) {
            Glide.with(this)
                .load(Uri.parse(profileImageUri))
                .placeholder(R.drawable.img_signup_profile) // 기본 이미지
                .into(binding.ivMypageMainProfile)
        } else {
            binding.ivMypageMainProfile.setImageResource(R.drawable.img_signup_profile) // 기본 이미지
        }

        binding.ivMypageMainBack.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
