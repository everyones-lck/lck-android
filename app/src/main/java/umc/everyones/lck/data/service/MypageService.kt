package umc.everyones.lck.data.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.Query
import umc.everyones.lck.data.UpdateProfileRequest
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.request.mypage.CancelHostViewingPartyMypageRequestDto
import umc.everyones.lck.data.dto.request.mypage.CancelParticipateViewingPartyMypageRequestDto
import umc.everyones.lck.data.dto.request.mypage.UpdateTeamRequestDto
import umc.everyones.lck.data.dto.response.mypage.CommentsMypageResponseDto
import umc.everyones.lck.data.dto.response.mypage.HostViewingPartyMypageResponseDto
import umc.everyones.lck.data.dto.response.mypage.InquiryProfilesResponseDto
import umc.everyones.lck.data.dto.response.mypage.ParticipateViewingPartyMypageResponseDto
import umc.everyones.lck.data.dto.response.mypage.PostsMypageResponseDto
import umc.everyones.lck.data.dto.response.mypage.UpdateProfilesResponseDto

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

    @GET("my-pages/comments")
    suspend fun commentsMypage(
        @Header("Authorization") token: String,
        @Query("size") size: Int = 6, // 기본값 6
        @Query("page") page: Int = 0 // 기본값 0
    ):BaseResponse<CommentsMypageResponseDto>

    @GET("my-pages/viewing-parties/participate")
    suspend fun participateViewingPartyMypage(
        @Header("Authorization") token: String,
        @Query("size") size: Int = 6, // 기본값 6
        @Query("page") page: Int = 0 // 기본값 0
    ):BaseResponse<ParticipateViewingPartyMypageResponseDto>

    @GET("my-pages/viewing-parties/host")
    suspend fun hostViewingPartyMypage(
        @Header("Authorization") token: String,
        @Query("size") size: Int = 6, // 기본값 6
        @Query("page") page: Int = 0 // 기본값 0
    ):BaseResponse<HostViewingPartyMypageResponseDto>

    @DELETE("my-pages/viewing-parties/participate")
    suspend fun cancelParticipateViewingPartyMypage(
        @Header("Authorization") token: String,
        @Body request: CancelParticipateViewingPartyMypageRequestDto
    ):BaseResponse<Boolean>

    @DELETE("my-pages/viewing-parties/host")
    suspend fun cancelHostViewingPartyMypage(
        @Header("Authorization") token: String,
        @Body request: CancelHostViewingPartyMypageRequestDto
    ):BaseResponse<Boolean>

    @DELETE("my-pages/logout")
    suspend fun logout(
        @Header("Authorization") token: String,
        @Header("Refresh") refreshToken: String
    ): BaseResponse<Boolean>

    @DELETE("my-pages/withdrawal")
    suspend fun withdraw(
        @Header("Authorization") token: String
    ): BaseResponse<Boolean>

    @Multipart
    @PATCH("my-pages/profiles")
    suspend fun updateProfiles(
        @Part profileImage: MultipartBody.Part?,
        @Part("updateProfileRequest") updateProfileRequest: RequestBody
    ): BaseResponse<UpdateProfilesResponseDto>

    @PATCH("my-pages/my-team")
    suspend fun updateTeam(
        @Header("Authorization") token: String,
        @Body request: UpdateTeamRequestDto
    ): BaseResponse<Boolean>
}