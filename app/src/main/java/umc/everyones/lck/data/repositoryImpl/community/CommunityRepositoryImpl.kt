package umc.everyones.lck.data.repositoryImpl.community

import android.content.SharedPreferences
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import umc.everyones.lck.data.datasource.community.CommunityDataSource
import umc.everyones.lck.data.datasourceImpl.community.CommunityListPagingSource
import umc.everyones.lck.data.dto.response.NonBaseResponse
import umc.everyones.lck.data.service.community.CommunityService
import umc.everyones.lck.domain.model.community.CommunityListModel
import umc.everyones.lck.domain.model.request.community.CreateCommentRequestModel
import umc.everyones.lck.domain.model.request.community.EditCommunityRequestModel
import umc.everyones.lck.domain.model.request.community.WriteCommunityRequestModel
import umc.everyones.lck.domain.model.response.community.EditCommunityResponseModel
import umc.everyones.lck.domain.model.response.community.ReadCommunityResponseModel
import umc.everyones.lck.domain.model.response.community.WriteCommunityResponseModel
import umc.everyones.lck.domain.repository.community.CommunityRepository
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val communityDataSource: CommunityDataSource,
    private val communityService: CommunityService,
    private val spf: SharedPreferences
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
            communityDataSource.fetchCommunityPost(postId).data.toReadCommunityResponseModel(spf.getString("nickname","")?:"")
        }

    override suspend fun deleteCommunityPost(postId: Long): Result<NonBaseResponse> =
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

    override fun fetchPagingSource(category: String): Flow<PagingData<CommunityListModel.CommunityListElementModel>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { CommunityListPagingSource(communityService, category) }
        ).flow

    override suspend fun reportCommunityPost(postId: Long): Result<NonBaseResponse> =
        runCatching { communityService.reportCommunityPost(postId)}

    override suspend fun reportCommunityComment(commentId: Long): Result<NonBaseResponse> =
        runCatching { communityDataSource.reportCommunityComment(commentId) }

    override suspend fun createComment(postId: Long, request: CreateCommentRequestModel): Result<NonBaseResponse> =
        runCatching { communityDataSource.createComment(postId, request.toCreateCommentRequestDto()) }
}