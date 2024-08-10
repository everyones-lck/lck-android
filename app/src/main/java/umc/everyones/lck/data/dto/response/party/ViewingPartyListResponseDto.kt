package umc.everyones.lck.data.dto.response.party

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import umc.everyones.lck.domain.model.party.ViewingPartyElementModel
import umc.everyones.lck.domain.model.party.ViewingPartyListModel
import umc.everyones.lck.util.extension.combineNicknameAndTeam
import umc.everyones.lck.util.extension.toPartyDateToString
import java.text.SimpleDateFormat
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
            ViewingPartyElementModel(id, name, userName.combineNicknameAndTeam(teamName), photoURL, partyDate.toPartyDateToString(), latitude, longitude, location)
    }

    fun toViewingPartyListModel() =
        ViewingPartyListModel(isLast, totalPage, partyList.toViewingPartyListElementModel())
}
