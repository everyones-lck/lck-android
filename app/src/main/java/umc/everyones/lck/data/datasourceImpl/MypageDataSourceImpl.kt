package umc.everyones.lck.data.datasourceImpl

import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.everyones.lck.data.datasource.MypageDataSource
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.mypage.CommentsMypageResponseDto
import umc.everyones.lck.data.dto.response.mypage.HostViewingPartyMypageResponseDto
import umc.everyones.lck.data.dto.response.mypage.InquiryProfilesResponseDto
import umc.everyones.lck.data.dto.response.mypage.ParticipateViewingPartyMypageResponseDto
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

    override suspend fun commentsProfiles(token: String, size: Int, page: Int): BaseResponse<CommentsMypageResponseDto> =
        mypageService.commentsMypage(token, size, page)

    override suspend fun participateViewingPartyMypage(token: String, size: Int, page: Int): BaseResponse<ParticipateViewingPartyMypageResponseDto> =
        mypageService.participateViewingPartyMypage(token, size, page)

    override suspend fun hostViewingPartyMypage(token: String, size: Int, page: Int): BaseResponse<HostViewingPartyMypageResponseDto> =
        mypageService.hostViewingPartyMypage(token, size, page)
}