package umc.everyones.lck.data.datasource.community

import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.community.CommunityListResponseDto

interface CommunityDataSource {
    suspend fun fetchCommunityList(postType: String, page: Int, size: Int): BaseResponse<CommunityListResponseDto>
}