package umc.everyones.lck.data.datasourceImpl

import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.everyones.lck.data.datasource.login.LoginDataSource
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.login.SignupAuthUserResponseDto
import umc.everyones.lck.data.service.LoginService
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val loginService: LoginService
): LoginDataSource {
    override suspend fun signup(
        kakaUserId: RequestBody,
        nickName: RequestBody,
        role: RequestBody,
        teamId: RequestBody,
        profileImage: MultipartBody.Part
    ): BaseResponse<SignupAuthUserResponseDto> =
        loginService.signup(kakaUserId, nickName, role, teamId, profileImage)

}