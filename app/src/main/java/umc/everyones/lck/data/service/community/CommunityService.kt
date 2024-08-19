package umc.everyones.lck.data.service.community

import android.util.Size
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.community.CommunityListResponseDto

interface CommunityService {
    @GET("post/list")
    suspend fun fetchCommunityList(
        @Query("postType") postType: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): BaseResponse<CommunityListResponseDto>
}