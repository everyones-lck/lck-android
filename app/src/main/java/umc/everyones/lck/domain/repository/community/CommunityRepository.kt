package umc.everyones.lck.domain.repository.community

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import umc.everyones.lck.data.dto.response.NonBaseResponse
import umc.everyones.lck.domain.model.community.CommunityListModel
import umc.everyones.lck.domain.model.request.community.EditCommunityRequestModel
import umc.everyones.lck.domain.model.request.community.WriteCommunityRequestModel
import umc.everyones.lck.domain.model.response.community.EditCommunityResponseModel
import umc.everyones.lck.domain.model.response.community.ReadCommunityResponseModel
import umc.everyones.lck.domain.model.response.community.WriteCommunityResponseModel

interface CommunityRepository {
    suspend fun fetchCommunityList(postType: String, page: Int, size: Int): Result<CommunityListModel>

    suspend fun writeCommunityPost(requestModel: WriteCommunityRequestModel): Result<WriteCommunityResponseModel>

    suspend fun fetchCommunityPost(postId: Long): Result<ReadCommunityResponseModel>

    suspend fun deleteCommunityPost(postId: Long): Result<NonBaseResponse>

    suspend fun editCommunityPost(postId: Long, request: EditCommunityRequestModel): Result<EditCommunityResponseModel>

    fun fetchPagingSource(category: String): Flow<PagingData<CommunityListModel.CommunityListElementModel>>

    suspend fun reportCommunityPost(postId: Long): Result<NonBaseResponse>

    suspend fun reportCommunityComment(commentId: Long): Result<NonBaseResponse>
}