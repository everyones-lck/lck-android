package umc.everyones.lck.data.datasource.community

import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.request.community.EditCommunityRequestDto
import umc.everyones.lck.data.dto.request.community.WriteCommunityRequestDto
import umc.everyones.lck.data.dto.response.NonBaseResponse
import umc.everyones.lck.data.dto.response.community.CommunityListResponseDto
import umc.everyones.lck.data.dto.response.community.EditCommunityResponseDto
import umc.everyones.lck.data.dto.response.community.ReadCommunityResponseDto
import umc.everyones.lck.data.dto.response.community.WriteCommunityResponseDto

interface CommunityDataSource {
    suspend fun fetchCommunityList(postType: String, page: Int, size: Int): BaseResponse<CommunityListResponseDto>

    suspend fun writeCommunityPost(request: WriteCommunityRequestDto): BaseResponse<WriteCommunityResponseDto>

    suspend fun fetchCommunityPost(postId: Long): BaseResponse<ReadCommunityResponseDto>

    suspend fun deleteCommunityPost(postId: Long): NonBaseResponse

    suspend fun editCommunityPost(postId: Long, request: EditCommunityRequestDto): BaseResponse<EditCommunityResponseDto>

    suspend fun reportCommunityPost(postId: Long): NonBaseResponse

    suspend fun reportCommunityComment(commentId: Long): NonBaseResponse
}