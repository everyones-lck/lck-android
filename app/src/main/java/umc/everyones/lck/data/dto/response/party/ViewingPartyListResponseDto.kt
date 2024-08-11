package umc.everyones.lck.data.dto.response.party

import umc.everyones.lck.domain.model.response.party.ViewingPartyElementModel
import umc.everyones.lck.domain.model.response.party.ViewingPartyListModel
import umc.everyones.lck.util.extension.combineNicknameAndTeam
import umc.everyones.lck.util.extension.listPartyDateToString
import java.time.LocalDateTime

data class ViewingPartyListResponseDto(
    val isLast: Boolean,
    val totalPage: Int,
    val partyList: List<ViewingPartyListElementDto>
) {
    data class ViewingPartyListElementDto(
        val id: Long,
        val name: String,
        val userName: String,
        val teamName: String,
        val photoURL: String,
        val partyDate: String,
        val latitude: Double,
        val longitude: Double,
        val location: String
    ){
        fun toViewingPartyListElementModel() =
            ViewingPartyElementModel(id, name, userName.combineNicknameAndTeam(teamName), photoURL, partyDate, latitude, longitude, location)
    }

    fun toViewingPartyListModel() =
        ViewingPartyListModel(isLast, totalPage, partyList.map { it.toViewingPartyListElementModel() })
}
