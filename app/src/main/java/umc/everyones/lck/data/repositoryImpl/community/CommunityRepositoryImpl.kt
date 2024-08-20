package umc.everyones.lck.data.repositoryImpl.community

import umc.everyones.lck.data.datasource.community.CommunityDataSource
import umc.everyones.lck.data.dto.response.NotBaseResponse
import umc.everyones.lck.domain.model.community.CommunityListModel
import umc.everyones.lck.domain.model.request.community.EditCommunityRequestModel
import umc.everyones.lck.domain.model.request.community.WriteCommunityRequestModel
import umc.everyones.lck.domain.model.response.community.EditCommunityResponseModel
import umc.everyones.lck.domain.model.response.community.ReadCommunityResponseModel
import umc.everyones.lck.domain.model.response.community.WriteCommunityResponseModel
import umc.everyones.lck.domain.repository.community.CommunityRepository
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val communityDataSource: CommunityDataSource
): CommunityRepository {
    override suspend fun fetchCommunityList(
        postType: String,
        page: Int,
        size: Int
    ): Result<CommunityListModel> =
        runCatching {
            communityDataSource.fetchCommunityList(postType, page, size).data.toCommunityListModel()
        }

    override suspend fun writeCommunityPost(requestModel: WriteCommunityRequestModel): Result<WriteCommunityResponseModel> =
        runCatching {
            communityDataSource.writeCommunityPost(requestModel.toWriteCommunityRequestDto()).data.toWriteCommunityResponseModel()
        }

    override suspend fun fetchCommunityPost(postId: Long): Result<ReadCommunityResponseModel> =
        runCatching {
            communityDataSource.fetchCommunityPost(postId).data.toReadCommunityResponseModel()
        }

    override suspend fun deleteCommunityPost(postId: Long): Result<NotBaseResponse> =
        runCatching {
            communityDataSource.deleteCommunityPost(postId)
        }

    override suspend fun editCommunityPost(
        postId: Long,
        request: EditCommunityRequestModel
    ): Result<EditCommunityResponseModel> =
        runCatching {
            communityDataSource.editCommunityPost(postId, request.toEditCommunityRequestDto()).data.toEditCommunityResponseModel()
        }
}