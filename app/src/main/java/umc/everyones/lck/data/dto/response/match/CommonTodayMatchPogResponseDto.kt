package umc.everyones.lck.data.dto.response.match

import umc.everyones.lck.domain.model.response.match.CommonTodayMatchPogModel

//data class CommonTodayMatchPogResponseDto(
//    val id: Int,
//    val name: String,
//    val profileImageUrl: String,
//    val seasonInfo: String,
//    val matchNumber: Int,
//    val matchDate: String
//) {
//    fun toCommonTodayMatchPogModel() =
//        CommonTodayMatchPogModel(id, name, profileImageUrl, seasonInfo, matchNumber, matchDate.replace("T", " ").dropLast(3))
//}
data class CommonTodayMatchPogResponseDto(
    val seasonInfo: String,
    val matchNumber: Int,
    val matchDate: String,
    val setPogResponses: List<SetPogResponseDto>,
    val matchPogResponse: MatchPogResponseDto
) {
    data class SetPogResponseDto(
        val name: String,
        val profileImageUrl: String,
        val playerId: Int,
        val setIndex: Int
    ) {
        fun toSetPogResponseModel() =
            CommonTodayMatchPogModel.SetPogResponseModel(
                name, profileImageUrl, playerId, setIndex
            )
    }

    data class MatchPogResponseDto (
        val name: String,
        val profileImageUrl: String,
        val playerId: Int
    ) {
        fun toMatchPogResponseModel() =
            CommonTodayMatchPogModel.MatchPogResponseModel(
                name, profileImageUrl, playerId
            )
    }

    fun toCommonTodayMatchPogModel() =
        CommonTodayMatchPogModel(
            seasonInfo,
            matchNumber,
            matchDate.replace("T", " ").dropLast(3),
            setPogResponses.map { it.toSetPogResponseModel() },
            matchPogResponse?.toMatchPogResponseModel()
        )
}