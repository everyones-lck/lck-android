package umc.everyones.lck.domain.model.party


import java.time.LocalDateTime

data class ViewingPartyListModel(
    val isLast: Boolean,
    val totalPage: Int,
    val partyList: ViewingPartyElementModel
)

data class ViewingPartyElementModel(
    val id: Long,
    val name: String,
    val userName: String,
    val teamName: String,
    val photoURL: String,
    val partyDate: LocalDateTime,
    val latitude: Double,
    val longitude: Double,
    val location: String
)
