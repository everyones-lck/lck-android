package umc.everyones.lck.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentSignupSuccessBinding
import umc.everyones.lck.util.NicknameManager

class SignupSuccessFragment : Fragment(R.layout.fragment_signup_success) {

    private var _binding: FragmentSignupSuccessBinding? = null
    private val binding get() = _binding!!

    private val teamLogos = mapOf(
        "Hanhwa" to R.drawable.img_signup_success_hanhwa_logo,
        "Gen.G" to R.drawable.img_signup_success_gen_g_logo,
        "T1" to R.drawable.img_signup_success_t1_logo,
        "Kwangdong Freecs" to R.drawable.img_signup_success_kwangdong_freecs_logo,
        "BNK" to R.drawable.img_signup_success_bnk_logo,
        "Nongshim Red Force" to R.drawable.img_signup_success_nongshim_red_force_logo,
        "DRX" to R.drawable.img_signup_success_drx_logo,
        "OK Saving Bank Brion" to R.drawable.img_signup_success_ok_saving_bank_brion_logo,
        "Dplus Kia" to R.drawable.img_signup_success_dplus_kia_logo,
        "KT Rolster" to R.drawable.img_signup_success_kt_rolster_logo
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignupSuccessBinding.bind(view)

        val nicknameManager = NicknameManager(requireContext())

        val selectedTeam = arguments?.getString("selectedTeam")
        val nickname = arguments?.getString("nickname") ?: "닉네임 없음"
        val profileImageUri = arguments?.getString("profileImageUri")

        // 닉네임을 저장
        nicknameManager.addNickname(nickname)

        binding.tvSignupSuccessCongratulation.text = "$nickname 님 가입을 축하드립니다!"

        if (selectedTeam != null) {
            val teamLogoResId = teamLogos[selectedTeam] ?: android.R.color.transparent
            binding.ivSignupSuccessBackgroundLogo.setImageResource(teamLogoResId)
        } else {
            binding.ivSignupSuccessBackgroundLogo.setImageResource(android.R.color.transparent)
        }

        if (profileImageUri != null) {
            Glide.with(this)
                .load(Uri.parse(profileImageUri))
                .placeholder(R.drawable.img_signup_profile) // 기본 이미지
                .into(binding.ivSignupSuccessProfile)
        } else {
            binding.ivSignupSuccessProfile.setImageResource(R.drawable.img_signup_profile) // 기본 이미지
        }

        binding.ivSignupSuccessNext.setOnClickListener {
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
