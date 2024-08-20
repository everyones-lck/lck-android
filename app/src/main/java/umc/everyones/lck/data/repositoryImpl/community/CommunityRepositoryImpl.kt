package umc.everyones.lck.data.repositoryImpl.community

import umc.everyones.lck.data.datasource.community.CommunityDataSource
import umc.everyones.lck.domain.model.community.CommunityListModel
import umc.everyones.lck.domain.model.request.community.WriteCommunityRequestModel
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

    override suspend fun writeCommunity(requestModel: WriteCommunityRequestModel): Result<WriteCommunityResponseModel> =
        runCatching {
            communityDataSource.writeCommunity(requestModel.toWriteCommunityRequestDto()).data.toWriteCommunityResponseModel()
        }

    override suspend fun fetchCommunity(postId: Long): Result<ReadCommunityResponseModel> =
        runCatching {
            communityDataSource.fetchCommunity(postId).data.toReadCommunityResponseModel()
        }
}