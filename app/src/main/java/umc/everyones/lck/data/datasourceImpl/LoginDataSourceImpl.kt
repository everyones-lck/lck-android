package umc.everyones.lck.data.datasourceImpl

import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.everyones.lck.data.datasource.login.LoginDataSource
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.request.login.CommonLoginRequestDto
import umc.everyones.lck.data.dto.response.login.CommonLoginResponseDto
import umc.everyones.lck.data.service.LoginService
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val loginService: LoginService
): LoginDataSource {
    override suspend fun signup(
        kakaoUserId: RequestBody,
        nickName: RequestBody,
        role: RequestBody,
        teamId: RequestBody,
        profileImage: MultipartBody.Part
    ): BaseResponse<CommonLoginResponseDto> =
        loginService.signup(kakaoUserId, nickName, role, teamId, profileImage)

    override suspend fun login(requestDto: CommonLoginRequestDto):BaseResponse<CommonLoginResponseDto> = loginService.login(requestDto)

    override suspend fun refresh(requestDto: CommonLoginRequestDto):BaseResponse<CommonLoginResponseDto> = loginService.refresh(requestDto)
}