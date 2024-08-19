package umc.everyones.lck.domain.repository.community

import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.community.CommunityListResponseDto
import umc.everyones.lck.domain.model.community.CommunityListModel

interface CommunityRepository {
    suspend fun fetchCommunityList(postType: String, page: Int, size: Int): Result<CommunityListModel>
}