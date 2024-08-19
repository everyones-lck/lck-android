package umc.everyones.lck.domain.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.everyones.lck.data.UpdateProfileRequest
import umc.everyones.lck.data.dto.request.mypage.UpdateTeamRequestDto
import umc.everyones.lck.domain.model.request.mypage.CancelHostViewingPartyMypageModel
import umc.everyones.lck.domain.model.request.mypage.CancelParticipateViewingPartyMypageModel
import umc.everyones.lck.domain.model.request.mypage.UpdateTeamModel
import umc.everyones.lck.domain.model.response.mypage.CommentsMypageModel
import umc.everyones.lck.domain.model.response.mypage.HostViewingPartyMypageModel
import umc.everyones.lck.domain.model.response.mypage.InquiryProfilesModel
import umc.everyones.lck.domain.model.response.mypage.ParticipateViewingPartyMypageModel
import umc.everyones.lck.domain.model.response.mypage.PostsMypageModel
import umc.everyones.lck.domain.model.response.mypage.UpdateProfilesModel

interface MypageRepository {
    suspend fun inquiryProfiles(): Result<InquiryProfilesModel>

    suspend fun postsMypage(size: Int, page: Int): Result<PostsMypageModel>

    suspend fun commentsMypage(size: Int, page: Int): Result<CommentsMypageModel>

    suspend fun participateViewingPartyMypage(size: Int, page: Int): Result<ParticipateViewingPartyMypageModel>

    suspend fun hostViewingPartyMypage(size: Int, page: Int): Result<HostViewingPartyMypageModel>

    suspend fun cancelParticipateViewingPartyMypage(request: CancelParticipateViewingPartyMypageModel): Result<Boolean>

    suspend fun cancelHostViewingPartyMypage(request: CancelHostViewingPartyMypageModel): Result<Boolean>

    suspend fun logout(refreshToken: String): Result<Boolean>

    suspend fun withdraw(): Result<Boolean>

    suspend fun updateProfiles(profileImage: MultipartBody.Part?, updateProfileRequest: RequestBody): Result<UpdateProfilesModel>

    suspend fun updateTeam(request: UpdateTeamModel): Result<Boolean>

}