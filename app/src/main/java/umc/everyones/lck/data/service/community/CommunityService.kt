package umc.everyones.lck.data.service.community

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.request.community.EditCommunityRequestDto
import umc.everyones.lck.data.dto.response.NotBaseResponse
import umc.everyones.lck.data.dto.response.community.CommunityListResponseDto
import umc.everyones.lck.data.dto.response.community.EditCommunityResponseDto
import umc.everyones.lck.data.dto.response.community.ReadCommunityResponseDto
import umc.everyones.lck.data.dto.response.community.WriteCommunityResponseDto

interface CommunityService {
    @GET("post/list")
    suspend fun fetchCommunityList(
        @Query("postType") postType: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): BaseResponse<CommunityListResponseDto>

    @Multipart
    @POST("post/create")
    suspend fun writeCommunityPost(
        @Part files: List<MultipartBody.Part?>,
        @Part("request") request: RequestBody
    ): BaseResponse<WriteCommunityResponseDto>

    @GET("post/{postId}/detail")
    suspend fun fetchCommunityPost(
        @Path("postId") postId: Long
    ): BaseResponse<ReadCommunityResponseDto>

    @DELETE("post/{postId}/delete")
    suspend fun deleteCommunityPost(
        @Path("postId") postId: Long
    ): NotBaseResponse

    @PATCH("post/{postId}/modify")
    suspend fun editCommunityPost(
        @Path("postId") postId: Long,
        @Body responseDto: EditCommunityRequestDto
    ): BaseResponse<EditCommunityResponseDto>
}