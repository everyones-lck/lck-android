package umc.everyones.lck.presentation.mypage

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import umc.everyones.lck.R
import umc.everyones.lck.data.UpdateProfileRequest
import umc.everyones.lck.domain.model.response.mypage.InquiryProfilesModel
import umc.everyones.lck.domain.model.response.mypage.UpdateProfilesModel
import umc.everyones.lck.domain.repository.MypageRepository
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
                clearAppCache()
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
                    clearAppCache()
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

    // 닉네임 가용성 체크 및 프로필 업데이트 메소드
    fun checkNicknameAndUpdateProfile(nickName: String, isDefaultImage: Boolean) {
        viewModelScope.launch {
            // SharedPreferences에서 프로필 이미지 가져오기
            val profileImageResId = spf.getInt("profileImageResId", R.drawable.img_signup_profile) // 기본 이미지
            val profileImagePart = createProfileImagePart(profileImageResId) // 프로필 이미지 부분 생성

            // UpdateProfileRequest 생성
            val updateProfileRequest = UpdateProfileRequest(nickName, isDefaultImage)

            // Gson을 사용하여 UpdateProfileRequest를 JSON으로 변환
            val gson = Gson()
            val updateProfileJson = gson.toJson(updateProfileRequest)
            val requestBody = updateProfileJson.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            // 프로필 업데이트 호출
            val result = runCatching {
                repository.updateProfiles(profileImagePart, requestBody) // RequestBody를 전달
            }

            // 결과 처리
            result.onSuccess { response ->
                _isNicknameAvailable.value = true
                _nickName.value = "Profile updated successfully"
            }.onFailure { error ->
                _isNicknameAvailable.value = false
                _nickName.value = "Failed to update profile: ${error.message}"
                Log.e("YourViewModel", "Error updating profile: ${error.message}")
            }
        }
    }

    // Bitmap을 MultipartBody.Part로 변환하는 메소드
    private fun createProfileImagePart(profileImageResId: Int): MultipartBody.Part {
        return try {
            val defaultImageBitmap: Bitmap = BitmapFactory.decodeResource(getApplication<Application>().resources, profileImageResId)
            val defaultImageStream = ByteArrayOutputStream().apply {
                defaultImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, this)
            }
            val defaultImageBytes = defaultImageStream.toByteArray()

            val requestBody = defaultImageBytes.toRequestBody("image/png".toMediaTypeOrNull())
            MultipartBody.Part.createFormData(
                "profileImage", "profile_image.png", requestBody
            )
        } catch (e: Exception) {
            Log.e("YourViewModel", "Error creating MultipartBody.Part for profile image: ${e.message}")
            val emptyImageRequestBody = ByteArray(0).toRequestBody("image/png".toMediaTypeOrNull())
            MultipartBody.Part.createFormData(
                "profileImage", "profile_image.png", emptyImageRequestBody
            )
        }
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