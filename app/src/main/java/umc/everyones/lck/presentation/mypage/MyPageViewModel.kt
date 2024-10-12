package umc.everyones.lck.presentation.mypage

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import umc.everyones.lck.R
import umc.everyones.lck.data.UpdateProfileRequest
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.mypage.UpdateProfilesResponseDto
import umc.everyones.lck.domain.model.request.mypage.UpdateTeamModel
import umc.everyones.lck.domain.model.response.mypage.InquiryProfilesModel
import umc.everyones.lck.domain.model.response.mypage.UpdateProfilesModel
import umc.everyones.lck.domain.repository.MypageRepository
import umc.everyones.lck.util.TeamData
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val spf: SharedPreferences,
    application: Application,
    private val repository: MypageRepository
) : AndroidViewModel(application) {

    private val context: Context = getApplication<Application>().applicationContext

    private val _profileData = MutableLiveData<InquiryProfilesModel?>()
    val profileData: LiveData<InquiryProfilesModel?> get() = _profileData

    private val _withdrawResult = MutableLiveData<Boolean>()
    val withdrawResult: LiveData<Boolean> get() = _withdrawResult

    private val _logoutResult = MutableLiveData<Boolean>()
    val logoutResult: LiveData<Boolean> get() = _logoutResult

    private val _updateProfileResult = MutableLiveData<UpdateProfilesModel?>()
    val updateProfileResult: LiveData<UpdateProfilesModel?> get() = _updateProfileResult

    private val _nickName = MutableLiveData<String>()
    val nickName: LiveData<String> get() = _nickName

    private val _isNicknameAvailable = MutableLiveData<Boolean>()
    val isNicknameAvailable: LiveData<Boolean> get() = _isNicknameAvailable

    private val _profileUri = MutableLiveData<Uri>()
    val profileUri: LiveData<Uri> get() = _profileUri

    private val _existingNickname = MutableLiveData<String>()
    val existingNickname: LiveData<String> get() = _existingNickname

    private val _teamId = MutableLiveData<Int>()
    val teamId: LiveData<Int> get() = _teamId

    fun setProfileImageUri(uri: Uri) {
        _profileUri.value = uri
    }

    fun inquiryProfile() {
        viewModelScope.launch {
            repository.inquiryProfiles().onSuccess { response ->
                _profileData.value = response
                _nickName.value = response.nickname // 닉네임을 LiveData에 저장
                _teamId.value = response.teamId
                Timber.d(response.toString())
            }.onFailure {
                Timber.d(it.stackTraceToString())
            }
        }
    }

    fun withdraw() {
        viewModelScope.launch {
            repository.withdraw().onSuccess {response ->
                _withdrawResult.value = true
                Timber.d("회원 탈퇴 성공: $response")
                spf.edit().putBoolean("isLoggedIn", false).apply()
                clearAppCache()
            }.onFailure { error ->
                Timber.e("회원 탈퇴 실패: ${error.message}")
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
                    Timber.d("로그아웃 성공: $response")
                    spf.edit().putBoolean("isLoggedIn", false).apply()
                    clearAppCache()
                }.onFailure { error ->
                    _logoutResult.value = false
                    Timber.e("로그아웃 실패: ${error.message}")
                }
            }
        } else {
            Timber.e("refreshToken이 없습니다.")
            _logoutResult.value = false
        }
    }

    fun updateProfile(nickName: String?, profileImageUri: Uri?) {
        viewModelScope.launch {
            // 현재 프로필 이미지와 닉네임 가져오기
            val currentProfileImageUri = _profileUri.value
                ?: Uri.parse("android.resource://${getApplication<Application>().packageName}/${R.drawable.img_signup_profile}") // 기본 이미지 URI
            val currentNickName = _nickName.value ?: ""

            // 입력값이 없으면 기존 값 사용
            val effectiveNickName = nickName?.takeIf { it.isNotEmpty() } ?: currentNickName

            // 프로필 이미지 URI를 조건에 따라 설정
            val effectiveProfileImageUri = if (profileImageUri != null) {
                profileImageUri // 새 이미지가 있을 경우 새 이미지 사용
            } else {
                currentProfileImageUri // 새 이미지가 없을 경우 기존 이미지 사용
            }

            // 프로필 이미지 부분 생성
            val profileImagePart = createProfileImagePart(effectiveProfileImageUri)

            // UpdateProfileRequest 생성
            val updateProfileRequest =
                UpdateProfileRequest(effectiveNickName, effectiveProfileImageUri == null)

            // JSON 변환
            val requestBody = Gson().toJson(updateProfileRequest)
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            // 프로필 업데이트 호출
            runCatching {
                repository.updateProfiles(profileImagePart, requestBody)
            }.onSuccess { response ->
                Timber.d("Response: ${Gson().toJson(response)}") // 응답 전체 로그

                // JSON 응답을 BaseResponse로 파싱
                val jsonResponse = Gson().toJson(response)
                Timber.d("JSON Response: $jsonResponse") // JSON 문자열 로그

                // 제네릭 타입을 이용한 BaseResponse 파싱
                val type = object : TypeToken<BaseResponse<UpdateProfilesResponseDto>>() {}.type
                val baseResponse: BaseResponse<UpdateProfilesResponseDto> =
                    Gson().fromJson(jsonResponse, type)

                // baseResponse가 null이 아닌지 확인하고, data가 유효한지 확인
                if (baseResponse.success) {
                    val updatedProfile = baseResponse.data // 이 값은 null이 아니어야 함
                    _updateProfileResult.value = UpdateProfilesModel(
                        updatedProfileImageUrl = updatedProfile.updatedProfileImageUrl,
                        updatedNickname = updatedProfile.updatedNickname
                    )
                } else {
                    Timber.e("Error in base response: ${baseResponse.message}")
                    _updateProfileResult.value = null
                }
            }.onFailure { error ->
                _updateProfileResult.value = null
                Timber.e("Error updating profile: ${error.message}")
            }
        }
    }

    fun updateTeam(teamId: Int) {
        viewModelScope.launch {
            // UpdateTeamModel 객체 생성
            val updateTeamModel = UpdateTeamModel(teamId) // 필요한 경우 추가 필드 설정

            runCatching {
                repository.updateTeam(updateTeamModel).onSuccess { response ->
                    Timber.d("팀 변경 성공: $response")
                    _teamId.value = teamId
                }.onFailure { error ->
                    Timber.d("팀 변경 실패: $error")
                }
            }.onFailure { error ->
                Timber.e("Error during update: ${error.message}")
            }
        }
    }


    private fun createProfileImagePart(uri: Uri): MultipartBody.Part {
        val inputStream = getApplication<Application>().contentResolver.openInputStream(uri) // URI에서 입력 스트림 열기
        val selectedImageBitmap = BitmapFactory.decodeStream(inputStream) // 비트맵으로 변환
        val byteArrayOutputStream = ByteArrayOutputStream().apply {
            selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, this)
        }
        val imageBytes = byteArrayOutputStream.toByteArray()

        val requestBody = imageBytes.toRequestBody("image/png".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("profileImage", "profile_image.png", requestBody)
    }



    private fun clearAppCache() {
        val cacheDir = context.cacheDir
        val profileImageFile = File(context.filesDir, "profileImage.jpg")
        if (cacheDir.isDirectory) {
            deleteDir(cacheDir)
        }
        if (profileImageFile.exists()) {
            profileImageFile.delete()
        }
    }

    private fun deleteDir(dir: File): Boolean {
        if (dir.isDirectory) {
            val children = dir.list()
            if (children != null) {
                for (child in children) {
                    deleteDir(File(dir, child))
                }
            }
        }
        return dir.delete()
    }
}