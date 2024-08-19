package umc.everyones.lck.data.datasource.community

import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.request.community.WriteCommunityRequestDto
import umc.everyones.lck.data.dto.response.community.CommunityListResponseDto
import umc.everyones.lck.data.dto.response.community.WriteCommunityResponseDto

interface CommunityDataSource {
    suspend fun fetchCommunityList(postType: String, page: Int, size: Int): BaseResponse<CommunityListResponseDto>

    suspend fun writeCommunity(request: WriteCommunityRequestDto): BaseResponse<WriteCommunityResponseDto>
}