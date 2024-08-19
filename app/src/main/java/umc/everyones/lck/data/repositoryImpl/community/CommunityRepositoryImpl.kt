package umc.everyones.lck.data.repositoryImpl.community

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import umc.everyones.lck.data.datasource.community.CommunityDataSource
import umc.everyones.lck.domain.model.community.CommunityListModel
import umc.everyones.lck.domain.model.community.WriteCommunityRequestModel
import umc.everyones.lck.domain.model.community.WriteCommunityResponseModel
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
}