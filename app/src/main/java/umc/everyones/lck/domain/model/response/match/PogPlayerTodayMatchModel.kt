package umc.everyones.lck.domain.model.response.match

data class PogPlayerTodayMatchModel(
    val information: List<InformationModel>
) {
    data class InformationModel(
        val playerId: Int,
        val playerProfileImageUrl: String,
        val playerName: String
    )
}