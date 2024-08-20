package umc.everyones.lck.presentation.login

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.net.http.HttpException
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import umc.everyones.lck.R
import umc.everyones.lck.data.SignupUserData
import umc.everyones.lck.data.dto.request.login.SignupAuthUserRequestDto
import umc.everyones.lck.data.service.LoginService
import umc.everyones.lck.domain.model.request.login.NicknameAuthUserRequestModel
import umc.everyones.lck.domain.repository.login.LoginRepository
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val spf: SharedPreferences,
    application: Application,
    private val repository: LoginRepository
) : AndroidViewModel(application) {

    private val _kakaoUserId = MutableLiveData<String>()
    val kakaoUserId: LiveData<String> get() = _kakaoUserId

    private val _nickName = MutableLiveData<String>()
    val nickName: LiveData<String> get() = _nickName

    private var signupUserData: SignupUserData? = null

    // 중복 체크 결과를 위한 LiveData 추가
    private val _isNicknameAvailable = MutableLiveData<Boolean>()
    val isNicknameAvailable: LiveData<Boolean> get() = _isNicknameAvailable

    private val _profileUri = MutableLiveData<Uri?>()
    val profileUri: LiveData<Uri?> get() = _profileUri

    private val _teamId = MutableLiveData<Int?>()
    val teamId: LiveData<Int?> get() = _teamId

    fun setKakaoUserId(kakaoUserId: String) {
        _kakaoUserId.value = kakaoUserId
    }

    fun setNickName(nickName: String) {
        _nickName.value = nickName
    }

    fun setProfileImageUri(uri: Uri) {
        _profileUri.value = uri
    }

    fun setTeamId(teamId: Int) {
        _teamId.value = teamId
    }

    fun checkNicknameAvailability(nickName: String) {
        viewModelScope.launch {
            // API 호출
            val result = try {
                repository.nickname(NicknameAuthUserRequestModel(nickName))
            } catch (e: Exception) {
                Log.e("SignupViewModel", "Error checking nickname availability: ${e.message}")
                Result.failure(e) // 예외 발생 시
            }

            // 결과를 _isNicknameAvailable에 저장
            _isNicknameAvailable.value = result.getOrDefault(false) // 기본값 false 설정

            // 성공 여부에 따라 처리
            result.onSuccess { isAvailable ->
                _nickName.value = if (isAvailable) {
                    "Nickname is available"
                } else {
                    "Nickname is already taken"
                }
            }.onFailure {
                _nickName.value = "Failed to check nickname availability"
            }
        }
    }

    fun signupUser(){

    }

}