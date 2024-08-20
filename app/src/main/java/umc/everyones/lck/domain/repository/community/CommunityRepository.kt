package umc.everyones.lck.domain.repository.community

import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.community.ReadCommunityResponseDto
import umc.everyones.lck.domain.model.community.CommunityListModel
import umc.everyones.lck.domain.model.request.community.WriteCommunityRequestModel
import umc.everyones.lck.domain.model.response.community.ReadCommunityResponseModel
import umc.everyones.lck.domain.model.response.community.WriteCommunityResponseModel

interface CommunityRepository {
    suspend fun fetchCommunityList(postType: String, page: Int, size: Int): Result<CommunityListModel>

    suspend fun writeCommunity(requestModel: WriteCommunityRequestModel): Result<WriteCommunityResponseModel>

    suspend fun fetchCommunity(postId: Long): Result<ReadCommunityResponseModel>
}