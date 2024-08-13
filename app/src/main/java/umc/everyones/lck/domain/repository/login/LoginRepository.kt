package umc.everyones.lck.domain.repository.login

import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.everyones.lck.data.dto.response.login.CommonLoginResponseDto
import umc.everyones.lck.domain.model.request.login.LoginAuthUserModel
import umc.everyones.lck.domain.model.response.login.CommonLoginModel

interface LoginRepository {
    suspend fun signup(kakaoUserId: RequestBody, nickName: RequestBody, role: RequestBody, teamId: RequestBody, profileImage: MultipartBody.Part): Result<CommonLoginModel>

    suspend fun login(request: LoginAuthUserModel): Result<CommonLoginModel>
}