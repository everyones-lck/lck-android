package umc.everyones.lck.data.dto.response.party

import umc.everyones.lck.domain.model.party.ViewingPartyElementModel
import umc.everyones.lck.domain.model.party.ViewingPartyListModel
import java.time.LocalDateTime

data class ViewingPartyListResponseDto(
    val isLast: Boolean,
    val totalPage: Int,
    val partyList: ViewingPartyListElementDto
) {
    data class ViewingPartyListElementDto(
        val id: Long,
        val name: String,
        val userName: String,
        val teamName: String,
        val photoURL: String,
        val partyDate: LocalDateTime,
        val latitude: Double,
        val longitude: Double,
        val location: String
    ){
        fun toViewingPartyListElementModel() =
            ViewingPartyElementModel(id, name, userName, teamName, photoURL, partyDate, latitude, longitude, location)
    }

    fun toViewingPartyListModel() =
        ViewingPartyListModel(isLast, totalPage, partyList.toViewingPartyListElementModel())
}
