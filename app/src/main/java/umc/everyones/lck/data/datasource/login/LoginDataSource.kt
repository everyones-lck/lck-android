package umc.everyones.lck.data.datasource.login

import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.request.login.LoginAuthUserRequestDto
import umc.everyones.lck.data.dto.response.login.CommonLoginResponseDto

interface LoginDataSource {
    suspend fun signup(kakaoUserId: RequestBody, nickName: RequestBody, role: RequestBody, teamId: RequestBody, profileImage: MultipartBody.Part): BaseResponse<CommonLoginResponseDto>

    suspend fun login(requestDto: LoginAuthUserRequestDto): BaseResponse<CommonLoginResponseDto>
}