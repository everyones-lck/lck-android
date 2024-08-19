package umc.everyones.lck.presentation.login

import android.os.Bundle
import android.util.Log
import android.view.View
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

    private lateinit var nicknameManager: NicknameManager
    private val viewModel: SignupViewModel by activityViewModels()
    private val navigator by lazy { findNavController() }

    override fun initObserver() {
        // No observers needed here
    }

    override fun initView() {
        nicknameManager = NicknameManager(requireContext())

        binding.etSignupNicknameName.doOnTextChanged { text, _, _, _ ->
            validateNickname(text.toString())
        }

        binding.ivSignupNicknameNext.setOnClickListener {
            val nickname = binding.etSignupNicknameName.text.toString()
            // 닉네임 유효성 검사가 통과하면
            if (validateNickname(nickname)) {
                viewModel.setNickname(nickname) // ViewModel에 닉네임 저장
                Log.d("SignupNicknameFragment", "Nickname set: $nickname")
                viewModel.setProfileImageUri(null) // 이후에 프로필 사진 선택할 때 업데이트

                navigator.navigate(R.id.action_signupNicknameFragment_to_signupProfileFragment)
            }
            val nickname2 = viewModel.nickname.value
            Log.d("SignupViewModel", "전달: $nickname2")
        }
    }

    private fun validateNickname(nickname: String): Boolean {
        var isValid = true

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

        if (nicknameManager.isNicknameDuplicate(nickname)) {
            binding.layoutSignupNicknameWarning4.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.layoutSignupNicknameWarning4.visibility = View.GONE
        }

        binding.layoutSignupNicknameValid.visibility = if (isValid) View.VISIBLE else View.GONE

        return isValid
    }
}