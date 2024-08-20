package umc.everyones.lck.presentation.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentSignupNicknameBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.util.NicknameManager

@AndroidEntryPoint
class SignupNicknameFragment : BaseFragment<FragmentSignupNicknameBinding>(R.layout.fragment_signup_nickname) {

    private val viewModel: SignupViewModel by activityViewModels()
    private val navigator by lazy { findNavController() }

    override fun initObserver() {
        viewModel.isNicknameAvailable.observe(viewLifecycleOwner) { isAvailable ->
            if (isAvailable) {
                binding.layoutSignupNicknameWarning4.visibility = View.GONE // 중복 아님
                binding.viewSignupNicknameBar.setBackgroundResource(R.color.success) // 성공 색상
                binding.tvSignupNicknameDuplication.setTextColor(requireContext().getColor(R.color.success)) // 성공 색상
            } else {
                binding.layoutSignupNicknameWarning4.visibility = View.VISIBLE // 중복
                binding.viewSignupNicknameBar.setBackgroundResource(R.color.warning) // 실패 색상
            }
        }
    }

    override fun initView() {
        binding.etSignupNicknameName.doOnTextChanged { text, _, _, _ ->
            val nickname = text.toString()
            if (validateNickname(nickname)) {
                binding.tvSignupNicknameDuplication.setTextColor(requireContext().getColor(R.color.black)) // 기본 색상
                binding.tvSignupNicknameDuplication.setBackgroundResource(R.drawable.shape_rect_12_white_line) // 기본 배경
            }
        }

        binding.tvSignupNicknameDuplication.setOnClickListener {
            val nickname = binding.etSignupNicknameName.text.toString()
            if (nickname.isEmpty()) {
                binding.tvSignupNicknameDuplication.setTextColor(requireContext().getColor(R.color.gray)) // 회색
                binding.tvSignupNicknameDuplication.setBackgroundResource(R.drawable.shape_rect_12_gray_line) // 회색 배경
                return@setOnClickListener
            }

            // 중복 확인 호출
            viewModel.checkNicknameAvailability(nickname)
        }

        binding.ivSignupNicknameNext.setOnClickListener {
            val nickname = binding.etSignupNicknameName.text.toString()
            if (validateNickname(nickname) && viewModel.isNicknameAvailable.value == true) {
                viewModel.setNickName(nickname) // ViewModel에 닉네임 저장
                navigator.navigate(R.id.action_signupNicknameFragment_to_signupProfileFragment)
            } else {
                Toast.makeText(requireContext(), "닉네임이 유효하지 않거나 이미 사용 중입니다.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
    private fun validateNickname(nickname: String): Boolean {
        var isValid = true
            // 닉네임 유효성 검사
        if (nickname.isEmpty()) {
            binding.layoutSignupNicknameWarning1.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.layoutSignupNicknameWarning1.visibility = View.GONE
        }
        if (nickname.length > 10) {
            binding.layoutSignupNicknameWarning2.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.layoutSignupNicknameWarning2.visibility = View.GONE
        }
        if (nickname.contains(" ")) {
            binding.layoutSignupNicknameWarning3.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.layoutSignupNicknameWarning3.visibility = View.GONE
        }

        // 닉네임 유효성 검사 결과에 따라 UI 업데이트
        binding.layoutSignupNicknameValid.visibility = if (isValid) View.VISIBLE else View.GONE
        return isValid
    }
}