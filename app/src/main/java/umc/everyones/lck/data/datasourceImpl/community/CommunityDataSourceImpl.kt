package umc.everyones.lck.data.datasourceImpl.community

import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.everyones.lck.data.datasource.community.CommunityDataSource
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.request.community.WriteCommunityRequestDto
import umc.everyones.lck.data.dto.response.community.CommunityListResponseDto
import umc.everyones.lck.data.dto.response.community.ReadCommunityResponseDto
import umc.everyones.lck.data.dto.response.community.WriteCommunityResponseDto
import umc.everyones.lck.data.service.community.CommunityService
import javax.inject.Inject

class CommunityDataSourceImpl @Inject constructor(
    private val communityService: CommunityService
): CommunityDataSource {
    override suspend fun fetchCommunityList(
        postType: String,
        page: Int,
        size: Int
    ): BaseResponse<CommunityListResponseDto> =
        communityService.fetchCommunityList(postType, page, size)

    override suspend fun writeCommunity(
        request: WriteCommunityRequestDto
    ): BaseResponse<WriteCommunityResponseDto> =
        communityService.writeCommunity(request.files, request.writeRequest)

    override suspend fun fetchCommunity(postId: Long): BaseResponse<ReadCommunityResponseDto> =
        communityService.fetchCommunity(postId)

}