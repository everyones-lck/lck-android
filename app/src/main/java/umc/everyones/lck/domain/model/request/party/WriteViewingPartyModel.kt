package umc.everyones.lck.domain.model.request.party

import umc.everyones.lck.data.dto.request.party.WriteViewingPartyRequestDto
import java.io.Serializable
import java.time.LocalDateTime

data class WriteViewingPartyModel(
    val name: String,
    val date: String,
    val latitude: Double,
    val longitude: Double,
    val location: String,
    val price: String,
    val lowParticipate: String,
    val highParticipate: String,
    val qualify: String,
    val etc: String
): Serializable {
    fun toWriteViewingPartyRequestDto() =
        WriteViewingPartyRequestDto(
            name,
            LocalDateTime.now(),
            latitude,
            longitude,
            price.replace(",", "").toInt(),
            lowParticipate.replace(",", "").toInt(),
            highParticipate.replace(",", "").toInt(),
            qualify,
            etc
        )
}
