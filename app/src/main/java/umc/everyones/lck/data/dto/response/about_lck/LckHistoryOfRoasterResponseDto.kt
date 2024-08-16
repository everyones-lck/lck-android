package umc.everyones.lck.data.dto.response.about_lck

import umc.everyones.lck.domain.model.about_lck.AboutLckHistoryOfRoasterModel
import umc.everyones.lck.domain.model.about_lck.AboutLckPlayerDetailsModel

data class LckHistoryOfRoasterResponseDto(
    val seasonDetails: List<LckHistoryOfRoasterSeasonPlayerElementDto>,
    val totalPage: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean
) {
    data class LckHistoryOfRoasterSeasonPlayerElementDto(
        val players: List<LckHistoryOfRoasterPlayerElementDto>,
        val numberOfPlayerDetail: Int,
        val seasonName: String
    ){
        data class LckHistoryOfRoasterPlayerElementDto(
            val playerId: Int,
            val playerName: String,
            val playerRole: PlayerRole,
            val playerPosition:PlayerPosition
        ){
            fun toAboutLckHistoryOfRoasterPlayerElementModel() =
                AboutLckHistoryOfRoasterModel.LckHistoryOfRoasterSeasonPlayerElementModel.LckHistoryOfRoasterPlayerElementModel(playerId,playerName,playerRole.toAboutLckPlayerRole(),playerPosition.toAboutLckPlayerPosition())

            enum class PlayerRole {
                LCK_ROSTER,
                COACH,
                CL_ROSTER,
                DEFAULT;

                fun toAboutLckPlayerRole(): AboutLckHistoryOfRoasterModel.LckHistoryOfRoasterSeasonPlayerElementModel.PlayerRole {
                    return when (this) {
                        LCK_ROSTER -> AboutLckHistoryOfRoasterModel.LckHistoryOfRoasterSeasonPlayerElementModel.PlayerRole.LCK_ROSTER
                        COACH -> AboutLckHistoryOfRoasterModel.LckHistoryOfRoasterSeasonPlayerElementModel.PlayerRole.COACH
                        CL_ROSTER -> AboutLckHistoryOfRoasterModel.LckHistoryOfRoasterSeasonPlayerElementModel.PlayerRole.CL_ROSTER
                        DEFAULT -> AboutLckHistoryOfRoasterModel.LckHistoryOfRoasterSeasonPlayerElementModel.PlayerRole.DEFAULT
                    }
                }
            }
            enum class PlayerPosition {
                TOP,
                JUNGLE,
                MID,
                BOT,
                SUPPORT,
                COACH;

                fun toAboutLckPlayerPosition(): AboutLckHistoryOfRoasterModel.LckHistoryOfRoasterSeasonPlayerElementModel.PlayerPosition {
                    return when (this) {
                        TOP -> AboutLckHistoryOfRoasterModel.LckHistoryOfRoasterSeasonPlayerElementModel.PlayerPosition.TOP
                        JUNGLE -> AboutLckHistoryOfRoasterModel.LckHistoryOfRoasterSeasonPlayerElementModel.PlayerPosition.JUNGLE
                        MID -> AboutLckHistoryOfRoasterModel.LckHistoryOfRoasterSeasonPlayerElementModel.PlayerPosition.MID
                        BOT -> AboutLckHistoryOfRoasterModel.LckHistoryOfRoasterSeasonPlayerElementModel.PlayerPosition.BOT
                        SUPPORT -> AboutLckHistoryOfRoasterModel.LckHistoryOfRoasterSeasonPlayerElementModel.PlayerPosition.SUPPORT
                        COACH -> AboutLckHistoryOfRoasterModel.LckHistoryOfRoasterSeasonPlayerElementModel.PlayerPosition.COACH
                    }
                }
            }
        }
        fun toAboutLckHistoryOfRoasterSeasonPlayerElementModel() =
            AboutLckHistoryOfRoasterModel.LckHistoryOfRoasterSeasonPlayerElementModel(players.map{it.toAboutLckHistoryOfRoasterPlayerElementModel()},numberOfPlayerDetail,seasonName)
    }
    fun toAboutLckHistoryOfRoasterModel() =
        AboutLckHistoryOfRoasterModel(seasonDetails.map{it.toAboutLckHistoryOfRoasterSeasonPlayerElementModel()},totalPage,totalElements,isFirst,isLast)

}