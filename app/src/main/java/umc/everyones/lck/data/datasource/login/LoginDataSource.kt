package umc.everyones.lck.data.datasource.login

import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.request.login.CommonLoginRequestDto
import umc.everyones.lck.data.dto.request.login.NicknameAuthUserRequestDto
import umc.everyones.lck.data.dto.response.login.CommonLoginResponseDto
import umc.everyones.lck.data.dto.response.login.LoginResponseDto

interface LoginDataSource {
    suspend fun signup(signupUserData: RequestBody, profileImage: MultipartBody.Part): BaseResponse<CommonLoginResponseDto>

    suspend fun login(requestDto: CommonLoginRequestDto): BaseResponse<LoginResponseDto>

    suspend fun refresh(requestDto: CommonLoginRequestDto): BaseResponse<CommonLoginResponseDto>

    suspend fun nickname(request: NicknameAuthUserRequestDto): BaseResponse<Boolean>

    suspend fun usertest(token: String): BaseResponse<Unit>
}