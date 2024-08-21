package umc.everyones.lck.presentation.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentLoginBinding
import umc.everyones.lck.domain.model.request.login.CommonLoginRequestModel
import umc.everyones.lck.domain.model.response.login.CommonLoginResponseModel
import umc.everyones.lck.presentation.MainActivity
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.util.LoginManager

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val viewModel: SignupViewModel by activityViewModels()
    private val navigator by lazy { findNavController() }

    override fun initObserver() {
        // 관찰자 설정 코드 필요 시 추가
    }

    override fun initView() {
        with(binding) {
            ivLoginKakao.setOnClickListener {
                val context: Context = requireContext()
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                    loginWithKakaoTalk()
                } else {
                    loginWithKakaoAccount()
                }
            }
        }
    }

    private fun loginWithKakaoTalk() {
        UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오톡으로 로그인 실패", error)
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    // 사용자가 로그인 과정을 취소한 경우
                    return@loginWithKakaoTalk
                }
                // 카카오 계정으로 로그인 시도
                loginWithKakaoAccount()
            } else if (token != null) {
                Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                handleLoginSuccess()
            }
        }
    }

    private fun loginWithKakaoAccount() {
        UserApiClient.instance.loginWithKakaoAccount(requireContext()) { accountToken, accountError ->
            if (accountError != null) {
                Log.e(TAG, "카카오 계정으로 로그인 실패", accountError)
            } else if (accountToken != null) {
                Log.i(TAG, "카카오 계정으로 로그인 성공 ${accountToken.accessToken}")
                handleLoginSuccess()
            }
        }
    }

    private fun handleLoginSuccess() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 가져오기 실패", error)
                return@me
            }

            user?.let {
                val kakaoUserId = it.id.toString()
                Log.d(TAG, "로그인 결과: $kakaoUserId") // 결과 로깅

                // ViewModel에 사용자 ID 설정
                viewModel.setKakaoUserId(kakaoUserId)

                // 로그인 요청
                viewModel.loginWithKakao(kakaoUserId)

                // 로그인 결과 관찰
                viewModel.loginResult.observe(viewLifecycleOwner) { userInfo ->
                    handleLoginResult(userInfo) // 로그인 결과에 따라 화면 전환
                }
            } ?: run {
                Log.e(TAG, "사용자 정보가 null입니다.")
                navigateToSignupNicknameScreen() // 회원가입 화면으로 이동
            }
        }
    }

    private fun handleLoginResult(userInfo: CommonLoginResponseModel?) {
        if (userInfo != null) {
            navigateToMainScreen()
        } else {
            navigateToSignupNicknameScreen()
        }
    }

    private fun navigateToMainScreen() {
        Intent(requireContext(), MainActivity::class.java).apply {
            startActivity(this)
            requireActivity().finish() // 현재 액티비티 종료
        }
    }

    private fun navigateToSignupNicknameScreen() {
        navigator.navigate(R.id.action_loginFragment_to_signupNicknameFragment)
    }

    companion object {
        private const val TAG = "LoginFragment"
    }
}
