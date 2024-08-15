package umc.everyones.lck.data.dto.response.match

import umc.everyones.lck.domain.model.response.match.SetPogTodayMatchModel

data class SetPogTodayMatchResponseDto(
    val information: List<InformationDto>
) {
    data class InformationDto(
        val playerId: Int,
        val playerProfileImageUrl: String,
        val playerName: String
    ) {
        fun toInformationModel() =
            SetPogTodayMatchModel.InformationModel(playerId, playerProfileImageUrl, playerName)
    }
    fun toSetPogTodayMatchModel() =
        SetPogTodayMatchModel(information.map { it.toInformationModel() })
}
