package umc.everyones.lck.presentation.mypage

import android.app.Application
import android.content.Context
import android.content.Intent
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
    application: Application,
    private val repository: MypageRepository
) : AndroidViewModel(application) {

    private val _profileData = MutableLiveData<InquiryProfilesModel?>()
    val profileData: LiveData<InquiryProfilesModel?> get() = _profileData

    private val _withdrawResult = MutableLiveData<Boolean>()
    val withdrawResult: LiveData<Boolean> get() = _withdrawResult

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
                Log.d("MyViewModel", "회원 탈퇴 성공: $response")
            }.onFailure { error ->
                Log.e("MyViewModel", "회원 탈퇴 실패: ${error.message}")
                _withdrawResult.value = false
            }
        }
    }
}