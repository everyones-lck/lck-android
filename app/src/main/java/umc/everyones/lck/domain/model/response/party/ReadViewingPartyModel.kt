package umc.everyones.lck.domain.model.response.party

data class ReadViewingPartyModel(
    val name: String,
    val writerInfo: String,
    val ownerImage: String,
    val qualify: String,
    val partyDate: String,
    val place: String,
    val latitude: Double,
    val longitude: Double,
    val price: String,
    val participants: String,
    val etc: String
)