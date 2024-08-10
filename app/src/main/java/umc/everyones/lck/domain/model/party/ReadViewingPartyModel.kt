package umc.everyones.lck.domain.model.party

import java.time.LocalDateTime

data class ReadViewingPartyModel(
    val name: String,
    val writerInfo: String,
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
)