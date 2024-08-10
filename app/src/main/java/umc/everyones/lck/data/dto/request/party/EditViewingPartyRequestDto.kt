package umc.everyones.lck.data.dto.request.party

data class EditViewingPartyRequestDto(
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
