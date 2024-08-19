package umc.everyones.lck.data.repositoryImpl.community

import umc.everyones.lck.data.datasource.community.CommunityDataSource
import umc.everyones.lck.domain.model.community.CommunityListModel
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
}