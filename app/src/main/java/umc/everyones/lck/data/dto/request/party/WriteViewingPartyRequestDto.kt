package umc.everyones.lck.data.dto.request.party

data class WriteViewingPartyRequestDto(
    val name: String,
    val date: String,
    val latitude: Double,
    val longitude: Double,
    val price: Int,
    val lowParticipate: Int,
    val highParticipate: Int,
    val qualify: String,
    val etc: String
)
