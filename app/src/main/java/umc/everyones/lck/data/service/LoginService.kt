package umc.everyones.lck.data.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.request.login.LoginAuthUserRequestDto
import umc.everyones.lck.data.dto.response.login.CommonLoginResponseDto

interface LoginService {

    @Multipart
    @POST("/auth/signup")
    suspend fun signup(
        @Part("kakaoUserId") kakaoUserId: RequestBody,
        @Part("nickName") nickName: RequestBody,
        @Part("role") role: RequestBody,
        @Part("teamId") teamId: RequestBody,
        @Part profileImage: MultipartBody.Part
    ): BaseResponse<CommonLoginResponseDto>

    @POST("/auth/login")
    suspend fun login(
        @Body request:LoginAuthUserRequestDto
    ):BaseResponse<CommonLoginResponseDto>
}