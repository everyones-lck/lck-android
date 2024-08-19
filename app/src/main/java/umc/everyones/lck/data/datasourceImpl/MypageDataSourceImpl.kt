package umc.everyones.lck.data.datasourceImpl

import umc.everyones.lck.data.datasource.MypageDataSource
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.mypage.InquiryProfilesResponseDto
import umc.everyones.lck.data.service.MypageService
import javax.inject.Inject

class MypageDataSourceImpl @Inject constructor(
    private val mypageService: MypageService
): MypageDataSource {
    override suspend fun inquiryprofiles(token: String): BaseResponse<InquiryProfilesResponseDto> =
        mypageService.inquiryprofiles(token)

}