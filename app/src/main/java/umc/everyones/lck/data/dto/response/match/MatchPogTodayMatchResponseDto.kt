package umc.everyones.lck.data.dto.response.match

import umc.everyones.lck.domain.model.response.match.MatchPogTodayMatchModel

data class MatchPogTodayMatchResponseDto(
    val information: List<InformationDto>
) {
    data class InformationDto(
        val playerId: Int,
        val playerProfileImageUrl: String,
        val playerName: String
    ) {
        fun toInformationModel() =
            MatchPogTodayMatchModel.InformationModel(playerId, playerProfileImageUrl, playerName)
    }
    fun toMatchPogTodayMatchModel() =
        MatchPogTodayMatchModel(information.map { it.toInformationModel() })
}

