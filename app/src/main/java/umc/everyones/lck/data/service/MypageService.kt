package umc.everyones.lck.data.service

import retrofit2.http.GET
import retrofit2.http.Header
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.mypage.InquiryProfilesResponseDto

interface MypageService {

    @GET("my-pages/profiles")
    suspend fun inquiryprofiles(
        @Header("Authorization") token: String
    ): BaseResponse<InquiryProfilesResponseDto>

}