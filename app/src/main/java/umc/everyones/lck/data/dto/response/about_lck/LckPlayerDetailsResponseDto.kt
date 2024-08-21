package umc.everyones.lck.data.dto.response.about_lck

import umc.everyones.lck.domain.model.about_lck.AboutLckPlayerDetailsModel

data class LckPlayerDetailsResponseDto(
    val playerDetails: List<LckPlayerDetailsElementDto>,
    val numberOfPlayerDetails: Int
) {
    data class LckPlayerDetailsElementDto(
        val playerId: Int,
        val playerName: String,
        val playerRole: PlayerRole,
        val position : PlayerPosition,
        val profileImageUrl: String,
        val isCaptain: Boolean
    ) {
        fun toAboutLckPlayerDetailsElementModel() =
            AboutLckPlayerDetailsModel.AboutLckPlayerDetailsElementModel(playerId, playerName, playerRole.toAboutLckPlayerRole(), position.toAboutLckPlayerPosition(),profileImageUrl,isCaptain)
    }
    enum class PlayerPosition{
        TOP,
        JUNGLE,
        MID,
        BOT,
        SUPPORT,
        COACH;

        fun toAboutLckPlayerPosition(): AboutLckPlayerDetailsModel.PlayerPosition {
            return when (this) {
                TOP -> AboutLckPlayerDetailsModel.PlayerPosition.TOP
                JUNGLE -> AboutLckPlayerDetailsModel.PlayerPosition.JUNGLE
                MID -> AboutLckPlayerDetailsModel.PlayerPosition.MID
                BOT -> AboutLckPlayerDetailsModel.PlayerPosition.BOT
                SUPPORT -> AboutLckPlayerDetailsModel.PlayerPosition.SUPPORT
                COACH -> AboutLckPlayerDetailsModel.PlayerPosition.COACH
            }
        }
    }

    enum class PlayerRole {
        LCK_ROSTER,
        COACH,
        LCK_CL_ROSTER,
        DEFAULT;

        fun toAboutLckPlayerRole(): AboutLckPlayerDetailsModel.PlayerRole {
            return when (this) {
                LCK_ROSTER -> AboutLckPlayerDetailsModel.PlayerRole.LCK_ROSTER
                COACH -> AboutLckPlayerDetailsModel.PlayerRole.COACH
                LCK_CL_ROSTER -> AboutLckPlayerDetailsModel.PlayerRole.LCK_CL_ROSTER
                DEFAULT -> AboutLckPlayerDetailsModel.PlayerRole.DEFAULT
            }
        }
    }
    fun toAboutLckPlayerDetailsModel() =
        AboutLckPlayerDetailsModel(playerDetails.map{it.toAboutLckPlayerDetailsElementModel()},numberOfPlayerDetails)

}

