package umc.everyones.lck.data.dto.response.about_lck

import umc.everyones.lck.domain.model.about_lck.AboutLckPlayerDetailsModel

data class LckPlayerDetailsResponseDto(
    val playerDetails: List<LckPlayerDetailsElementDto>,
    val numberOfPlayerDetails: Int
) {
    data class LckPlayerDetailsElementDto(
        val playerId: Int,
        val playerName: String,
        val player_role: PlayerRole,
        val profileImageUrl: String
    ) {
        fun toAboutLckPlayerDetailsElementModel() =
            AboutLckPlayerDetailsModel.AboutLckPlayerDetailsElementModel(playerId, playerName, player_role.toAboutLckPlayerRole(), profileImageUrl)
    }

    enum class PlayerRole {
        LCK_ROSTER,
        COACH,
        CL_ROSTER,
        DEFAULT;

        fun toAboutLckPlayerRole(): AboutLckPlayerDetailsModel.PlayerRole {
            return when (this) {
                LCK_ROSTER -> AboutLckPlayerDetailsModel.PlayerRole.LCK_ROSTER
                COACH -> AboutLckPlayerDetailsModel.PlayerRole.COACH
                CL_ROSTER -> AboutLckPlayerDetailsModel.PlayerRole.CL_ROSTER
                DEFAULT -> AboutLckPlayerDetailsModel.PlayerRole.DEFAULT
            }
        }
    }
    fun toAboutLckPlayerDetailsModel() =
        AboutLckPlayerDetailsModel(playerDetails.map{it.toAboutLckPlayerDetailsElementModel()},numberOfPlayerDetails)

}

