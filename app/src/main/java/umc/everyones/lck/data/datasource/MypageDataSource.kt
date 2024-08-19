package umc.everyones.lck.data.datasource

import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.mypage.InquiryProfilesResponseDto

interface MypageDataSource {
    suspend fun inquiryprofiles(token: String): BaseResponse<InquiryProfilesResponseDto>
}