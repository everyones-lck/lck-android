package umc.everyones.lck.presentation.mypage

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import umc.everyones.lck.R
import umc.everyones.lck.domain.model.response.mypage.InquiryProfilesModel
import umc.everyones.lck.domain.repository.MypageRepository
import umc.everyones.lck.presentation.MainActivity
import umc.everyones.lck.presentation.login.LoginActivity
import umc.everyones.lck.presentation.login.SignupViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val spf: SharedPreferences,
    application: Application,
    private val repository: MypageRepository
) : AndroidViewModel(application) {

    private val _profileData = MutableLiveData<InquiryProfilesModel?>()
    val profileData: LiveData<InquiryProfilesModel?> get() = _profileData

    private val _withdrawResult = MutableLiveData<Boolean>()
    val withdrawResult: LiveData<Boolean> get() = _withdrawResult

    private val _logoutResult = MutableLiveData<Boolean>()
    val logoutResult: LiveData<Boolean> get() = _logoutResult


    fun inquiryProfile() {
        viewModelScope.launch {
            repository.inquiryProfiles().onSuccess { response ->
                _profileData.value = response
                Log.d("inquiryProfile", response.toString())
            }.onFailure {
                Log.d("inquiryProfile", it.stackTraceToString())
            }
        }
    }

    fun withdraw() {
        viewModelScope.launch {
            repository.withdraw().onSuccess {response ->
                _withdrawResult.value = true
                Log.d("withdraw", "회원 탈퇴 성공: $response")
                spf.edit().putBoolean("isLoggedIn", false).apply()
            }.onFailure { error ->
                Log.e("withdraw", "회원 탈퇴 실패: ${error.message}")
                _withdrawResult.value = false
            }
        }
    }

    fun logout() {
        val refreshToken = spf.getString("refreshToken", null)
        if (refreshToken != null) {
            viewModelScope.launch {
                repository.logout(refreshToken).onSuccess { response ->
                    _logoutResult.value = true
                    Log.d("logout", "로그아웃 성공: $response")
                    spf.edit().putBoolean("isLoggedIn", false).apply()
                }.onFailure { error ->
                    _logoutResult.value = false
                    Log.e("logout", "로그아웃 실패: ${error.message}")
                }
            }
        } else {
            Log.e("logout", "refreshToken이 없습니다.")
            _logoutResult.value = false
        }
    }
}