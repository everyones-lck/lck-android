package umc.everyones.lck.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentLoginBinding
import umc.everyones.lck.util.LoginManager

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        binding.ivLoginKakao.setOnClickListener {
            // 로그인 성공 시 처리
            val loginManager = LoginManager(requireContext())
            loginManager.setLoggedIn(true)
            findNavController().navigate(R.id.action_loginFragment_to_signupNicknameFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
