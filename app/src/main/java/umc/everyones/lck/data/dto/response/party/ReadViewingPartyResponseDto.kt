package umc.everyones.lck.data.dto.response.party

import umc.everyones.lck.domain.model.response.party.ReadViewingPartyModel
import umc.everyones.lck.util.extension.combineNicknameAndTeam
import umc.everyones.lck.util.extension.partyDateToString
import java.text.DecimalFormat
import java.time.LocalDateTime

data class ReadViewingPartyResponseDto(
    val name: String,
    val ownerName: String,
    val ownerTeam: String,
    val ownerImage: String,
    val qualify: String,
    val partyDate: String,
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val price: Int,
    val lowParticipate: Int,
    val highParticipate: Int,
    val etc: String
) {
    fun toReadViewingPartyModel() =
        ReadViewingPartyModel(
            name,
            ownerName.combineNicknameAndTeam(ownerTeam),
            ownerImage,
            qualify,
            partyDate.partyDateToString(),
            location,
            latitude,
            longitude,
            "₩ ${DecimalFormat("#,###").format(price)}",
            "$lowParticipate - $highParticipate 명",
            etc
        )
}
