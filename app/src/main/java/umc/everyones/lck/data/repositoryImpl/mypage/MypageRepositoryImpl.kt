package umc.everyones.lck.data.repositoryImpl.mypage

import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.everyones.lck.data.datasource.MypageDataSource
import umc.everyones.lck.data.dto.request.mypage.CancelParticipateViewingPartyMypageRequestDto
import umc.everyones.lck.domain.model.request.mypage.CancelHostViewingPartyMypageModel
import umc.everyones.lck.domain.model.request.mypage.CancelParticipateViewingPartyMypageModel
import umc.everyones.lck.domain.model.request.mypage.UpdateTeamModel
import umc.everyones.lck.domain.model.response.mypage.CommentsMypageModel
import umc.everyones.lck.domain.model.response.mypage.HostViewingPartyMypageModel
import umc.everyones.lck.domain.model.response.mypage.InquiryProfilesModel
import umc.everyones.lck.domain.model.response.mypage.ParticipateViewingPartyMypageModel
import umc.everyones.lck.domain.model.response.mypage.PostsMypageModel
import umc.everyones.lck.domain.model.response.mypage.UpdateProfilesModel
import umc.everyones.lck.domain.repository.MypageRepository
import javax.inject.Inject

class MypageRepositoryImpl  @Inject constructor(
    private val mypageDataSource: MypageDataSource
): MypageRepository {
    override suspend fun inquiryProfiles(token: String): Result<InquiryProfilesModel> =
        runCatching { mypageDataSource.inquiryProfiles(token).data.toInquiryProfilesModel() }

    override suspend fun postsMypage(token: String, size: Int, page: Int): Result<PostsMypageModel> =
        runCatching { mypageDataSource.postsProfiles(token, size, page).data.toPostsMypageModel() }

    override suspend fun commentsMypage(token: String, size: Int, page: Int): Result<CommentsMypageModel> =
        runCatching { mypageDataSource.commentsProfiles(token, size, page).data.toCommentsMypageModel() }

    override suspend fun participateViewingPartyMypage(token: String, size: Int, page: Int): Result<ParticipateViewingPartyMypageModel> =
        runCatching { mypageDataSource.participateViewingPartyMypage(token, size, page).data.toParticipateViewingPartyMypageModel() }

    override suspend fun hostViewingPartyMypage(token: String, size: Int, page: Int): Result<HostViewingPartyMypageModel> =
        runCatching { mypageDataSource.hostViewingPartyMypage(token, size, page).data.toHostViewingPartyMypageModel() }

    override suspend fun cancelParticipateViewingPartyMypage(token: String, request: CancelParticipateViewingPartyMypageModel): Result<Boolean> =
        runCatching { mypageDataSource.cancelParticipateViewingPartyMypage(token, request.toCancelParticipateViewingPartyMypageRequestDto()).data }

    override suspend fun cancelHostViewingPartyMypage(token: String, request: CancelHostViewingPartyMypageModel): Result<Boolean> =
        runCatching { mypageDataSource.cancelHostViewingPartyMypage(token, request.toCancelHostViewingPartyMypageRequestDto()).data }

    override suspend fun logout(token: String, refreshToken: String): Result<Boolean> =
        runCatching { mypageDataSource.logout(token, refreshToken).data }

    override suspend fun withdraw(token: String): Result<Boolean> =
        runCatching { mypageDataSource.withdraw(token).data }

    override suspend fun updateProfiles(profileImage: MultipartBody.Part?, updateProfileRequest: RequestBody): Result<UpdateProfilesModel> =
        runCatching { mypageDataSource.updateProfiles(profileImage, updateProfileRequest).data.toUpdateProfilesModel() }

    override suspend fun updateTeam(token: String, request: UpdateTeamModel): Result<Boolean> =
        runCatching { mypageDataSource.updateTeam(token, request.toUpdateTeamRequestDto()).data }
}
