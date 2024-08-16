package umc.everyones.lck.domain.model.about_lck

import umc.everyones.lck.data.dto.response.about_lck.LckPlayerDetailsResponseDto


data class AboutLckPlayerDetailsModel(
    val playerDetails: List<AboutLckPlayerDetailsElementModel>,
    val numberOfPlayerDetails: Int
){
    data class AboutLckPlayerDetailsElementModel(
        val playerId: Int,
        val playerName: String,
        val player_role: PlayerRole,
        val profileImageUrl:String
    )
    enum class PlayerRole {
        LCK_ROSTER,
        COACH,
        CL_ROSTER,
        DEFAULT;

        fun toLckPlayerRole(): LckPlayerDetailsResponseDto.PlayerRole {
            return when (this) {
                LCK_ROSTER -> LckPlayerDetailsResponseDto.PlayerRole.LCK_ROSTER
                COACH -> LckPlayerDetailsResponseDto.PlayerRole.COACH
                CL_ROSTER -> LckPlayerDetailsResponseDto.PlayerRole.CL_ROSTER
                DEFAULT -> LckPlayerDetailsResponseDto.PlayerRole.DEFAULT
            }
        }
    }
}
