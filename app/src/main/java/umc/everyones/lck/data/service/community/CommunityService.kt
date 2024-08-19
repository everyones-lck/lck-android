package umc.everyones.lck.data.service.community

import android.util.Size
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.community.CommunityListResponseDto
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
    suspend fun writeCommunity(
        @Part files: List<MultipartBody.Part>,
        @Part("request") request: RequestBody
    ): BaseResponse<WriteCommunityResponseDto>
}