package umc.everyones.lck.data.dto.response.about_lck

import umc.everyones.lck.domain.model.about_lck.AboutLckPlayerModel

data class LckPlayerResponseDto(
    val nickName: String,
    val realName: String,
    val playerProfileImageUrl: String,
    val birthDate: String,
    val position: PlayerPosition
){
    fun toAboutLckPlayerModel() =
        AboutLckPlayerModel(nickName,realName,playerProfileImageUrl,birthDate,position.toAboutLckPlayerPosition())

    enum class PlayerPosition {
        TOP,
        JUNGLE,
        MID,
        BOT,
        SUPPORT,
        COACH;

        fun toAboutLckPlayerPosition(): AboutLckPlayerModel.PlayerPosition {
            return when (this) {
                TOP -> AboutLckPlayerModel.PlayerPosition.TOP
                JUNGLE -> AboutLckPlayerModel.PlayerPosition.JUNGLE
                MID -> AboutLckPlayerModel.PlayerPosition.MID
                BOT -> AboutLckPlayerModel.PlayerPosition.BOT
                SUPPORT -> AboutLckPlayerModel.PlayerPosition.SUPPORT
                COACH -> AboutLckPlayerModel.PlayerPosition.COACH
            }
        }
    }
}
