package umc.everyones.lck.data.repositoryImpl.mypage

import umc.everyones.lck.data.datasource.MypageDataSource
import umc.everyones.lck.domain.model.response.mypage.InquiryProfilesModel
import umc.everyones.lck.domain.repository.MypageRepository
import javax.inject.Inject

class MypageRepositoryImpl  @Inject constructor(
    private val mypageDataSource: MypageDataSource
): MypageRepository {
    override suspend fun inquiryProfiles(token: String): Result<InquiryProfilesModel> =
        runCatching { mypageDataSource.inquiryprofiles(token).data.toinquiryprofilesModel() }

}