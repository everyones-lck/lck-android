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
        val context: Context = requireContext()

        with(binding) {
            ivLoginKakao.setOnClickListener {
                val context: Context = requireContext()
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                    loginWithKakaoTalk()
                } else {
                    loginWithKakaoAccount()
                }
                navigateToSignupNicknameScreen()
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
                handleLoginSuccess(token)
            }
        }
    }

    private fun loginWithKakaoAccount() {
        UserApiClient.instance.loginWithKakaoAccount(requireContext()) { accountToken, accountError ->
            if (accountError != null) {
                Log.e(TAG, "카카오 계정으로 로그인 실패", accountError)
            } else if (accountToken != null) {
                Log.i(TAG, "카카오 계정으로 로그인 성공 ${accountToken.accessToken}")
                handleLoginSuccess(accountToken)
            }
        }
    }

    private fun handleLoginSuccess(token: OAuthToken) {
        // 카카오 사용자 ID 획득
        UserApiClient.instance.me { user, error ->
            user?.let {
                val kakaoUserId = token.accessToken
                Log.d(TAG, "로그인 결과: $kakaoUserId") // 결과 로깅

                // ViewModel에 사용자 ID 설정
                viewModel.setKakaoUserId(kakaoUserId)

                lifecycleScope.launch {
                    viewModel.loginWithKakao(kakaoUserId) // 사용자 ID로 로그인
                }
            }
        }
    }

    private fun handleLoginResult(userInfo: CommonLoginResponseModel?) {
        if (userInfo != null) {
            // 로그인 성공 시, 메인 화면으로 이동
            navigateToMainScreen()
        } else {
            // 회원가입이 되어 있지 않은 경우 회원가입 화면으로 이동
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
