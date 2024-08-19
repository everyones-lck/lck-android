package umc.everyones.lck.domain.repository.community

import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.community.CommunityListResponseDto
import umc.everyones.lck.data.dto.response.community.WriteCommunityResponseDto
import umc.everyones.lck.domain.model.community.CommunityListModel
import umc.everyones.lck.domain.model.community.WriteCommunityRequestModel
import umc.everyones.lck.domain.model.community.WriteCommunityResponseModel

interface CommunityRepository {
    suspend fun fetchCommunityList(postType: String, page: Int, size: Int): Result<CommunityListModel>

    suspend fun writeCommunity(requestModel: WriteCommunityRequestModel): Result<WriteCommunityResponseModel>
}