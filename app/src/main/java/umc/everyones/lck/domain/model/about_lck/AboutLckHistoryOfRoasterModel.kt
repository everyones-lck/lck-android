package umc.everyones.lck.domain.model.about_lck

data class AboutLckHistoryOfRoasterModel(
    val seasonDetails: List<LckHistoryOfRoasterSeasonPlayerElementModel>,
    val totalPage: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean
) {
    data class LckHistoryOfRoasterSeasonPlayerElementModel(
        val players: List<LckHistoryOfRoasterPlayerElementModel>,
        val numberOfPlayerDetail: Int,
        val seasonName: String
    ){
        data class LckHistoryOfRoasterPlayerElementModel(
            val playerId: Int,
            val playerName: String,
            val playerRole: PlayerRole?,
            val playerPosition:PlayerPosition?
        )
        enum class PlayerRole {
            LCK_ROSTER,
            COACH,
            CL_ROSTER,
            DEFAULT
        }
        enum class PlayerPosition{
            TOP,
            JUNGLE,
            MID,
            BOT,
            SUPPORT,
            COACH
        }
    }
}