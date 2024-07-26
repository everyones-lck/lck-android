package umc.everyones.lck.presentation.login

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentLoginBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.util.LoginManager

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private lateinit var loginManager: LoginManager

    override fun initObserver() {

    }

    override fun initView() {
        loginManager = LoginManager(requireContext())

        binding.ivLoginKakao.setOnClickListener {
            // 로그인 성공 시 처리
            loginManager.setLoggedIn(true)
            findNavController().navigate(R.id.action_loginFragment_to_signupNicknameFragment)
        }
    }
}
