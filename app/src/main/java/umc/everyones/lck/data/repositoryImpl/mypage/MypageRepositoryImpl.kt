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
    override suspend fun inquiryProfiles(): Result<InquiryProfilesModel> =
        runCatching { mypageDataSource.inquiryProfiles().data.toInquiryProfilesModel() }

    override suspend fun postsMypage(size: Int, page: Int): Result<PostsMypageModel> =
        runCatching { mypageDataSource.postsProfiles(size, page).data.toPostsMypageModel() }

    override suspend fun commentsMypage(size: Int, page: Int): Result<CommentsMypageModel> =
        runCatching { mypageDataSource.commentsProfiles(size, page).data.toCommentsMypageModel() }

    override suspend fun participateViewingPartyMypage(size: Int, page: Int): Result<ParticipateViewingPartyMypageModel> =
        runCatching { mypageDataSource.participateViewingPartyMypage(size, page).data.toParticipateViewingPartyMypageModel() }

    override suspend fun hostViewingPartyMypage(size: Int, page: Int): Result<HostViewingPartyMypageModel> =
        runCatching { mypageDataSource.hostViewingPartyMypage(size, page).data.toHostViewingPartyMypageModel() }

    override suspend fun cancelParticipateViewingPartyMypage(request: CancelParticipateViewingPartyMypageModel): Result<Boolean> =
        runCatching { mypageDataSource.cancelParticipateViewingPartyMypage(request.toCancelParticipateViewingPartyMypageRequestDto()).data }

    override suspend fun cancelHostViewingPartyMypage(request: CancelHostViewingPartyMypageModel): Result<Boolean> =
        runCatching { mypageDataSource.cancelHostViewingPartyMypage(request.toCancelHostViewingPartyMypageRequestDto()).data }

    override suspend fun logout(refreshToken: String): Result<Boolean> =
        runCatching { mypageDataSource.logout(refreshToken).data }

    override suspend fun withdraw(): Result<Boolean> =
        runCatching { mypageDataSource.withdraw().data }

    override suspend fun updateProfiles(profileImage: MultipartBody.Part?, updateProfileRequest: RequestBody): Result<UpdateProfilesModel> =
        runCatching { mypageDataSource.updateProfiles(profileImage, updateProfileRequest).data.toUpdateProfilesModel() }

    override suspend fun updateTeam(request: UpdateTeamModel): Result<Boolean> =
        runCatching { mypageDataSource.updateTeam(request.toUpdateTeamRequestDto()).data }
}
