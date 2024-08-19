package umc.everyones.lck.data.datasourceImpl

import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.everyones.lck.data.datasource.MypageDataSource
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.mypage.InquiryProfilesResponseDto
import umc.everyones.lck.data.dto.response.mypage.PostsMypageResponseDto
import umc.everyones.lck.data.service.MypageService
import javax.inject.Inject

class MypageDataSourceImpl @Inject constructor(
    private val mypageService: MypageService
): MypageDataSource {
    override suspend fun inquiryProfiles(token: String): BaseResponse<InquiryProfilesResponseDto> =
        mypageService.inquiryProfiles(token)

    override suspend fun postsProfiles(token: String, size: Int, page: Int): BaseResponse<PostsMypageResponseDto> =
        mypageService.postsMypage(token, size, page)

}