package umc.everyones.lck.domain.model.response.match

data class CommonTodayMatchPogModel(
    val id: Int,
    val name: String,
    val profileImageUrl: String,
    val seasonInfo: String,
    val matchNumber: Int,
    val matchDate: String
)
