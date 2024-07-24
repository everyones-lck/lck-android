package umc.everyones.lck.presentation.login

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentSignupNicknameBinding
import umc.everyones.lck.util.NicknameManager

@AndroidEntryPoint
class SignupNicknameFragment : Fragment(R.layout.fragment_signup_nickname) {

    private var _binding: FragmentSignupNicknameBinding? = null
    private val binding get() = _binding!!
    private lateinit var nicknameManager: NicknameManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignupNicknameBinding.bind(view)

        nicknameManager = NicknameManager(requireContext())

        binding.etSignupNicknameName.doOnTextChanged { text, _, _, _ ->
            validateNickname(text.toString())
        }

        binding.ivSignupNicknameNext.setOnClickListener {
            val nickname = binding.etSignupNicknameName.text.toString()
            if (validateNickname(nickname)) {
                val bundle = Bundle().apply {
                    putString("nickname", nickname)
                }
                findNavController().navigate(R.id.action_signupNicknameFragment_to_signupProfileFragment, bundle)
            }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
