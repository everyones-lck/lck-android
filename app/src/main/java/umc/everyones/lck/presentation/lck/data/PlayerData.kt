package umc.everyones.lck.presentation.lck.data

import umc.everyones.lck.domain.model.about_lck.AboutLckPlayerDetailsModel

data class PlayerData(
    val playerId: Int,
    val playerImg: String,
    val teamColor: Int,
    val name: String,
    val teamLogo: Int,
    val position: AboutLckPlayerDetailsModel.PlayerPosition?,
    val isCaptain: Boolean?
)
