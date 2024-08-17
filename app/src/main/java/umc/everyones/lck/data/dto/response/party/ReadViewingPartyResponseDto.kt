package umc.everyones.lck.data.dto.response.party

import umc.everyones.lck.domain.model.response.party.ReadViewingPartyModel
import umc.everyones.lck.util.extension.combineNicknameAndTeam
import umc.everyones.lck.util.extension.toReadViewingPartyDateFormat
import java.text.DecimalFormat

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
            partyDate.slice(0..15).toReadViewingPartyDateFormat(),
            location,
            latitude,
            longitude,
            "₩ ${DecimalFormat("#,###").format(price)}",
            "$lowParticipate - $highParticipate 명",
            etc
        )
}
