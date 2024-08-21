package umc.everyones.lck.data.dto.request.party

import java.time.LocalDateTime

data class WriteViewingPartyRequestDto(
    val name: String,
    val date: String,
    val latitude: Double,
    val longitude: Double,
    val location: String,
    val shortLocation: String,
    val price: String,
    val lowParticipate: String,
    val highParticipate: String,
    val qualify: String,
    val etc: String
)