package umc.everyones.lck.presentation.login


import android.util.Log
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentSignupNicknameBinding
import umc.everyones.lck.presentation.base.BaseFragment
@AndroidEntryPoint
class SignupNicknameFragment : BaseFragment<FragmentSignupNicknameBinding>(R.layout.fragment_signup_nickname) {

    private val viewModel: SignupViewModel by activityViewModels()
    private val navigator by lazy { findNavController() }
    private var isNicknameDuplicated = false // 닉네임 중복 여부 추적

    override fun initObserver() {
        viewModel.isNicknameAvailable.observe(viewLifecycleOwner) { isAvailable ->
            isNicknameDuplicated = !isAvailable
            if (isAvailable) {
                binding.etSignupNicknameName.setBackgroundResource(R.drawable.shape_rect_4_green_line)
                binding.tvSignupNicknameDuplication.text = "확인" // 텍스트 변경
                binding.layoutSignupNicknameWarning4.visibility = View.GONE
                binding.layoutSignupNicknameValid.visibility = View.VISIBLE
                binding.tvSignupNicknameDuplication.setOnClickListener { // 확인 버튼 클릭 시 이동
                    val nickname = binding.etSignupNicknameName.text.toString()
                    viewModel.setNickName(nickname)
                    navigateToSignupProfile()
                }
            } else {
                binding.layoutSignupNicknameWarning4.visibility = View.VISIBLE // 중복
                binding.etSignupNicknameName.setBackgroundResource(R.drawable.shape_rect_4_red_line)
                binding.tvSignupNicknameDuplication.text = "중복 확인" // 텍스트 유지
                binding.tvSignupNicknameDuplication.setOnClickListener {
                    viewModel.checkNicknameAvailability(binding.etSignupNicknameName.text.toString())
                }
            }
        }
    }

    override fun initView() {
        setInitialState() // 초기 상태 설정

        binding.etSignupNicknameName.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.tvSignupNicknameDuplication.setBackgroundResource(R.drawable.shape_rect_4_gray_line_black_fill) // 입력창 배경
                binding.tvSignupNicknameDuplication.setTextColor(requireContext().getColor(R.color.grayscale_100)) // 글자색
            } else if (binding.etSignupNicknameName.text.isNullOrEmpty()) {
                setInitialState()
            }
        }

        binding.etSignupNicknameName.doOnTextChanged { _, _, _, _ ->
            // 텍스트 변경 시 경고 및 유효 메시지 숨기고, 버튼 텍스트 복구
            binding.layoutSignupNicknameWarning1.visibility = View.GONE
            binding.layoutSignupNicknameWarning2.visibility = View.GONE
            binding.layoutSignupNicknameWarning3.visibility = View.GONE
            binding.layoutSignupNicknameWarning4.visibility = View.GONE
            binding.layoutSignupNicknameValid.visibility = View.GONE
            binding.tvSignupNicknameDuplication.text = "중복 확인"
            binding.tvSignupNicknameDuplication.setOnClickListener {
                val nickname = binding.etSignupNicknameName.text.toString()
                if (nickname.isNotEmpty()) {
                    validateAndCheckNickname(nickname)
                } else {
                    binding.layoutSignupNicknameWarning1.visibility = View.VISIBLE // 비어있다는 경고 표시
                }
            }
            // 입력 중에는 버튼 활성화 (유효성 검사는 버튼 클릭 시 수행)
            binding.tvSignupNicknameDuplication.isEnabled = true
            binding.etSignupNicknameName.setBackgroundResource(R.drawable.shape_rect_4_gray_line_black_fill)
            binding.tvSignupNicknameDuplication.setTextColor(requireContext().getColor(R.color.grayscale_100))
        }

        binding.tvSignupNicknameDuplication.setOnClickListener {
            val nickname = binding.etSignupNicknameName.text.toString()
            if (nickname.isNotEmpty()) {
                validateAndCheckNickname(nickname)
            } else {
                binding.layoutSignupNicknameWarning1.visibility = View.VISIBLE // 비어있다는 경고 표시
            }
        }
    }

    private fun validateAndCheckNickname(nickname: String) {
        val isValid = validateNickname(nickname)
        if (isValid) {
            viewModel.checkNicknameAvailability(nickname)
        }
    }

    private fun setInitialState() {
        binding.tvSignupNicknameDuplication.setTextColor(requireContext().getColor(R.color.grayscale_700)) // 회색
        binding.tvSignupNicknameDuplication.setBackgroundResource(R.drawable.shape_rect_4_grayscale_700_line_new_bg_fill) // 회색 배경
        binding.tvSignupNicknameDuplication.text = "중복 확인" // 텍스트 초기화
        binding.layoutSignupNicknameWarning4.visibility = View.GONE // 초기 경고 숨기기
        binding.layoutSignupNicknameValid.visibility = View.GONE // 초기 유효 숨기기
        binding.tvSignupNicknameDuplication.isEnabled = false // 초기에는 비활성화
    }

    private fun validateNickname(nickname: String): Boolean {
        var isValid = true
        // 닉네임 유효성 검사
        if (nickname.isEmpty()) {
            binding.layoutSignupNicknameWarning1.visibility = View.VISIBLE
            binding.etSignupNicknameName.setBackgroundResource(R.drawable.shape_rect_4_red_line)
            isValid = false
        } else {
            binding.layoutSignupNicknameWarning1.visibility = View.GONE
        }
        if (nickname.length > 10) {
            binding.layoutSignupNicknameWarning2.visibility = View.VISIBLE
            binding.etSignupNicknameName.setBackgroundResource(R.drawable.shape_rect_4_red_line)
            isValid = false
        } else {
            binding.layoutSignupNicknameWarning2.visibility = View.GONE
        }
        if (nickname.contains(" ")) {
            binding.layoutSignupNicknameWarning3.visibility = View.VISIBLE
            binding.etSignupNicknameName.setBackgroundResource(R.drawable.shape_rect_4_red_line)
            isValid = false
        } else {
            binding.layoutSignupNicknameWarning3.visibility = View.GONE
        }
        return isValid
    }

    private fun navigateToSignupProfile() {
        navigator.navigate(R.id.action_signupNicknameFragment_to_signupProfileFragment)
    }
}