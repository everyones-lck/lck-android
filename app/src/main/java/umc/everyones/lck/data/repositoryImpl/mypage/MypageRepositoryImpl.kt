package umc.everyones.lck.data.repositoryImpl.mypage

import umc.everyones.lck.data.datasource.MypageDataSource
import umc.everyones.lck.domain.model.response.mypage.InquiryProfilesModel
import umc.everyones.lck.domain.model.response.mypage.PostsMypageModel
import umc.everyones.lck.domain.repository.MypageRepository
import javax.inject.Inject

class MypageRepositoryImpl  @Inject constructor(
    private val mypageDataSource: MypageDataSource
): MypageRepository {
    override suspend fun inquiryProfiles(token: String): Result<InquiryProfilesModel> =
        runCatching { mypageDataSource.inquiryProfiles(token).data.toInquiryProfilesModel() }

    override suspend fun postsMypage(token: String, size: Int, page: Int): Result<PostsMypageModel> =
        runCatching { mypageDataSource.postsProfiles(token, size, page).data.toPostsMypageModel() }
}