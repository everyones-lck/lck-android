package umc.everyones.lck.domain.model.request.party

import umc.everyones.lck.data.dto.request.party.WriteViewingPartyRequestDto

data class WriteViewingPartyModel(
    val name: String,
    val date: String,
    val latitude: Double,
    val longitude: Double,
    val price: String,
    val lowParticipate: String,
    val highParticipate: String,
    val qualify: String,
    val etc: String
) {
    fun toWriteViewingPartyRequestDto() =
        WriteViewingPartyRequestDto(
            name,
            date,
            latitude,
            longitude,
            price.replace(",", "").toInt(),
            lowParticipate.replace(",", "").toInt(),
            highParticipate.replace(",", "").toInt(),
            qualify,
            etc
        )
}
