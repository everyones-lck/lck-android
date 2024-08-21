package umc.everyones.lck.domain.model.response.mypage

data class InquiryProfilesModel(
    val nickname: String,
    val profileImageUrl: String,
    val teamId: Int,
    val tier: String
)
