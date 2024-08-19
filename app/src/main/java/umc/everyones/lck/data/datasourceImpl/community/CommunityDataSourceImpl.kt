package umc.everyones.lck.data.datasourceImpl.community

import umc.everyones.lck.data.datasource.community.CommunityDataSource
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.community.CommunityListResponseDto
import umc.everyones.lck.data.service.community.CommunityService
import javax.inject.Inject

class CommunityDataSourceImpl @Inject constructor(
    private val communityService: CommunityService
): CommunityDataSource {
    override suspend fun fetchCommunityList(
        postType: String,
        page: Int,
        size: Int
    ): BaseResponse<CommunityListResponseDto> =
        communityService.fetchCommunityList(postType, page, size)

}