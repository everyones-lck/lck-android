package umc.everyones.lck.domain.repository.login

import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.login.SignupAuthUserResponseDto
import umc.everyones.lck.domain.model.response.login.SignupAuthUserModel

interface LoginRepository {
    suspend fun signup(kakaUserId: RequestBody, nickName: RequestBody, role: RequestBody, teamId: RequestBody, profileImage: MultipartBody.Part): Result<SignupAuthUserModel>

}