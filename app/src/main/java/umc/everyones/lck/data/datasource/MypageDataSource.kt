package umc.everyones.lck.data.datasource

import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.mypage.CommentsMypageResponseDto
import umc.everyones.lck.data.dto.response.mypage.InquiryProfilesResponseDto
import umc.everyones.lck.data.dto.response.mypage.PostsMypageResponseDto

interface MypageDataSource {
    suspend fun inquiryProfiles(token: String): BaseResponse<InquiryProfilesResponseDto>

    suspend fun postsProfiles(token: String, size: Int, page: Int): BaseResponse<PostsMypageResponseDto>

    suspend fun commentsProfiles(token: String, size: Int, page: Int): BaseResponse<CommentsMypageResponseDto>
}