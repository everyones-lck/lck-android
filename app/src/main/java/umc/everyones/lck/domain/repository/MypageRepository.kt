package umc.everyones.lck.domain.repository

import umc.everyones.lck.domain.model.response.mypage.InquiryProfilesModel
import umc.everyones.lck.domain.model.response.mypage.PostsMypageModel

interface MypageRepository {
    suspend fun inquiryProfiles(token: String): Result<InquiryProfilesModel>

    suspend fun postsMypage(token: String, size: Int, page: Int): Result<PostsMypageModel>
}