package umc.everyones.lck.domain.repository

import umc.everyones.lck.domain.model.response.mypage.InquiryProfilesModel

interface MypageRepository {
    suspend fun inquiryProfiles(token: String): Result<InquiryProfilesModel>

}