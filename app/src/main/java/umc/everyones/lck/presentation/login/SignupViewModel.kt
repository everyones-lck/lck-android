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
import umc.everyones.lck.domain.model.request.login.CommonLoginRequestModel
import umc.everyones.lck.domain.model.request.login.NicknameAuthUserRequestModel
import umc.everyones.lck.domain.model.response.login.CommonLoginResponseModel
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
    private var userInfo: CommonLoginResponseModel? = null


    // 중복 체크 결과를 위한 LiveData 추가
    private val _isNicknameAvailable = MutableLiveData<Boolean>()
    val isNicknameAvailable: LiveData<Boolean> get() = _isNicknameAvailable

    private val _profileUri = MutableLiveData<Uri?>()
    val profileUri: LiveData<Uri?> get() = _profileUri

    private val _teamId = MutableLiveData<Int>()
    val teamId: LiveData<Int> get() = _teamId

    private val _signupResponse = MutableLiveData<Result<CommonLoginResponseModel>?>()
    val signupResponse: LiveData<Result<CommonLoginResponseModel>?> get() = _signupResponse

    private val _loginResult = MutableLiveData<CommonLoginResponseModel?>()
    val loginResult: LiveData<CommonLoginResponseModel?> get() = _loginResult

    private val context: Context = application

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

    fun loginWithKakao(kakaoUserId: String) {
        viewModelScope.launch {
            _kakaoUserId.value = kakaoUserId
            val requestModel = CommonLoginRequestModel(kakaoUserId)

            repository.login(requestModel).onSuccess { response ->
                Log.d("loginWithKakao", response.toString())
                spf.edit().putString("jwt", response.accessToken).apply()
                _loginResult.value = response // 로그인 결과를 LiveData에 저장
            }.onFailure { error ->
                Log.d("loginWithKakao Error", error.message.toString())
                Log.d("LoginWithKakao", "$requestModel")
                _loginResult.value = null // 실패 시 null 설정
            }
        }
    }

    fun sendSignupData() {
        viewModelScope.launch {
            try {
                val signupRequest = prepareSignupRequest()

                Log.d("SignupViewModel", "Sending API request with data: ${signupRequest.signupUserData}")

                val gson = Gson()
                val signupUserDataJson = gson.toJson(signupRequest.signupUserData)

                // API 호출
                val response = repository.signup(
                    signupUserDataJson.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull()),
                    signupRequest.profileImage
                )

                Log.d("SignupViewModel", "API call successful: $response")
                _signupResponse.value = response // 응답 값을 LiveData에 저장
                spf.edit().apply {
                    putString("nickName", signupRequest.signupUserData.nickName)
                    putString("profileImage", _profileUri.value?.toString()) //URI 형식을 문자열로 변환하여 저장
                    putString("role", signupRequest.signupUserData.role)
                    putInt("teamId", signupRequest.signupUserData.teamId)
                    putString("tier", signupRequest.signupUserData.tier)
                }
            } catch (e: Exception) {
                Log.e("SignupViewModel", "API call failed: ${e.message}")
                _signupResponse.value = null // 실패 시 null 처리
            }
        }
    }

    private fun prepareSignupRequest(): SignupAuthUserRequestDto {
        // Mock data, replace with actual data
        val kakaoUserId = _kakaoUserId.value ?: throw IllegalArgumentException("Kakao User ID is required.")
        val nickName = _nickName.value ?: throw IllegalArgumentException("Nickname is required.")
        val role = "ROLE_USER"
        val teamId = _teamId.value ?: 1 // 기본값 설정
        val tier = "Bronze" // 실제 티어로 대체

        // 리소스에서 Bitmap을 생성하여 MultipartBody.Part로 변환
        val profileImagePart = try {
            val defaultImageBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.img_signup_profile)
            val defaultImageStream = ByteArrayOutputStream().apply {
                defaultImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, this)
            }
            val defaultImageBytes = defaultImageStream.toByteArray()

            val requestBody = defaultImageBytes.toRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData(
                "profileImage", "img_signup_profile.png", requestBody
            )
        } catch (e: Exception) {
            Log.e("SignupViewModel", "Error creating MultipartBody.Part for profile image: ${e.message}")
            val emptyImageRequestBody = ByteArray(0).toRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData(
                "profileImage", "img_signup_profile.png", emptyImageRequestBody
            )
        }

        return SignupAuthUserRequestDto(
            profileImage = profileImagePart,
            signupUserData = SignupAuthUserRequestDto.SignupUserDataRequestDto(
                kakaoUserId = kakaoUserId,
                nickName = nickName,
                role = role,
                tier = tier,
                teamId = teamId
            )
        )
    }

    fun signupUser() {

    }

}
