package umc.everyones.lck.data.dto.response.match

import umc.everyones.lck.domain.model.response.match.PogPlayerTodayMatchModel

data class PogPlayerTodayMatchResponseDto(
    val information: List<InformationDto>
) {
    data class InformationDto(
        val playerId: Int,
        val playerProfileImageUrl: String,
        val playerName: String
    ) {
        fun toInformationModel() =
            PogPlayerTodayMatchModel.InformationModel(playerId, playerProfileImageUrl, playerName)
    }
    fun toPogPlayerTodayMatchModel() =
        PogPlayerTodayMatchModel(information.map { it.toInformationModel() })
}