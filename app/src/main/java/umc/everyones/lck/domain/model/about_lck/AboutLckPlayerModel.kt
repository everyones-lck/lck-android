package umc.everyones.lck.domain.model.about_lck


data class AboutLckPlayerModel(
    val nickName: String,
    val realName: String,
    val playerProfileImageUrl: String,
    val birthDate: String,
    val position: PlayerPosition
){
    enum class PlayerPosition{
        TOP,
        JUNGLE,
        MID,
        BOT,
        SUPPORT,
        COACH
    }
}
