package umc.everyones.lck.domain.repository

import umc.everyones.lck.domain.model.request.mypage.CancelParticipateViewingPartyMypageModel
import umc.everyones.lck.domain.model.response.mypage.CommentsMypageModel
import umc.everyones.lck.domain.model.response.mypage.HostViewingPartyMypageModel
import umc.everyones.lck.domain.model.response.mypage.InquiryProfilesModel
import umc.everyones.lck.domain.model.response.mypage.ParticipateViewingPartyMypageModel
import umc.everyones.lck.domain.model.response.mypage.PostsMypageModel

interface MypageRepository {
    suspend fun inquiryProfiles(token: String): Result<InquiryProfilesModel>

    suspend fun postsMypage(token: String, size: Int, page: Int): Result<PostsMypageModel>

    suspend fun commentsMypage(token: String, size: Int, page: Int): Result<CommentsMypageModel>

    suspend fun participateViewingPartyMypage(token: String, size: Int, page: Int): Result<ParticipateViewingPartyMypageModel>

    suspend fun hostViewingPartyMypage(token: String, size: Int, page: Int): Result<HostViewingPartyMypageModel>

    suspend fun cancelParticipateViewingPartyMypage(token: String, request: CancelParticipateViewingPartyMypageModel): Result<Boolean>
}