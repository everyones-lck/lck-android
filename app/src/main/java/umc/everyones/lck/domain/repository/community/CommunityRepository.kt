package umc.everyones.lck.domain.repository.community

import umc.everyones.lck.data.dto.response.NotBaseResponse
import umc.everyones.lck.domain.model.community.CommunityListModel
import umc.everyones.lck.domain.model.request.community.WriteCommunityRequestModel
import umc.everyones.lck.domain.model.response.community.ReadCommunityResponseModel
import umc.everyones.lck.domain.model.response.community.WriteCommunityResponseModel

interface CommunityRepository {
    suspend fun fetchCommunityList(postType: String, page: Int, size: Int): Result<CommunityListModel>

    suspend fun writeCommunityPost(requestModel: WriteCommunityRequestModel): Result<WriteCommunityResponseModel>

    suspend fun fetchCommunityPost(postId: Long): Result<ReadCommunityResponseModel>

    suspend fun deleteCommunityPost(postId: Long): Result<NotBaseResponse>
}