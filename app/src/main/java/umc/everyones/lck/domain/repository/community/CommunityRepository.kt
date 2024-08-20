package umc.everyones.lck.domain.repository.community

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.request.community.EditCommunityRequestDto
import umc.everyones.lck.data.dto.response.NotBaseResponse
import umc.everyones.lck.data.dto.response.community.EditCommunityResponseDto
import umc.everyones.lck.domain.model.community.CommunityListModel
import umc.everyones.lck.domain.model.request.community.EditCommunityRequestModel
import umc.everyones.lck.domain.model.request.community.WriteCommunityRequestModel
import umc.everyones.lck.domain.model.response.community.EditCommunityResponseModel
import umc.everyones.lck.domain.model.response.community.ReadCommunityResponseModel
import umc.everyones.lck.domain.model.response.community.WriteCommunityResponseModel
import umc.everyones.lck.domain.model.response.party.ViewingPartyListModel

interface CommunityRepository {
    suspend fun fetchCommunityList(postType: String, page: Int, size: Int): Result<CommunityListModel>

    suspend fun writeCommunityPost(requestModel: WriteCommunityRequestModel): Result<WriteCommunityResponseModel>

    suspend fun fetchCommunityPost(postId: Long): Result<ReadCommunityResponseModel>

    suspend fun deleteCommunityPost(postId: Long): Result<NotBaseResponse>

    suspend fun editCommunityPost(postId: Long, request: EditCommunityRequestModel): Result<EditCommunityResponseModel>

    fun fetchPagingSource(category: String): Flow<PagingData<CommunityListModel.CommunityListElementModel>>
}