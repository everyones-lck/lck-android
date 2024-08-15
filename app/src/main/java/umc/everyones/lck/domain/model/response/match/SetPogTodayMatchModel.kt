package umc.everyones.lck.domain.model.response.match

import umc.everyones.lck.data.dto.response.match.SetPogTodayMatchResponseDto

data class SetPogTodayMatchModel(
    val information: List<InformationModel>
) {
    data class InformationModel(
        val playerId: Int,
        val playerProfileImageUrl: String,
        val playerName: String
    )
}
