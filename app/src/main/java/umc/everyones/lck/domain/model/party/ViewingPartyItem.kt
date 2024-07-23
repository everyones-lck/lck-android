package umc.everyones.lck.domain.model.party

data class ViewingPartyItem(
    val id: Int,
    val title: String,
    val writer: String,
    val favoriteTeam: String,
    val date: String,
    val address: String
)
