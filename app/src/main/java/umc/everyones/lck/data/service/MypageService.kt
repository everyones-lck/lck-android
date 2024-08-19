package umc.everyones.lck.data.service

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.Query
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.mypage.InquiryProfilesResponseDto
import umc.everyones.lck.data.dto.response.mypage.PostsMypageResponseDto

interface MypageService {

    @GET("my-pages/profiles")
    suspend fun inquiryProfiles(
        @Header("Authorization") token: String
    ): BaseResponse<InquiryProfilesResponseDto>

    @GET("my-pages/posts")
    suspend fun postsMypage(
        @Header("Authorization") token: String,
        @Query("size") size: Int = 6, // 기본값 6
        @Query("page") page: Int = 0 // 기본값 0
    ): BaseResponse<PostsMypageResponseDto>
}