package umc.everyones.lck.domain.model.response.match

//data class CommonTodayMatchPogModel(
//    val id: Int,
//    val name: String,
//    val profileImageUrl: String,
//    val seasonInfo: String,
//    val matchNumber: Int,
//    val matchDate: String,
//    val tabIndex: Int = 0
//)
data class CommonTodayMatchPogModel(
    val seasonInfo: String,
    val matchNumber: Int,
    val matchDate: String,
    val setPogResponse: List<SetPogResponseModel>,
    val matchPogResponse: MatchPogResponseModel?,
    val tabIndex: Int = 0
) {
    data class SetPogResponseModel(
        val name: String,
        val profileImageUrl: String,
        val playerId: Int,
        val setIndex: Int
    )

    data class MatchPogResponseModel(
        val name: String,
        val profileImageUrl: String,
        val playerId: Int
    )
}