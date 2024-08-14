package umc.everyones.lck.data.datasource.login

import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.request.login.CommonLoginRequestDto
import umc.everyones.lck.data.dto.request.login.NicknameAuthUserRequestDto
import umc.everyones.lck.data.dto.response.login.CommonLoginResponseDto

interface LoginDataSource {
    suspend fun signup(kakaoUserId: RequestBody, nickName: RequestBody, role: RequestBody, teamId: RequestBody, profileImage: MultipartBody.Part): BaseResponse<CommonLoginResponseDto>

    suspend fun login(requestDto: CommonLoginRequestDto): BaseResponse<CommonLoginResponseDto>

    suspend fun refresh(requestDto: CommonLoginRequestDto): BaseResponse<CommonLoginResponseDto>

    suspend fun nickname(request: NicknameAuthUserRequestDto): Result<Unit>

    suspend fun usertest(token: String): BaseResponse<Unit>
}