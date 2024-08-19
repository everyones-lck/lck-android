package umc.everyones.lck.domain.model.response.party


data class ViewingPartyListModel(
    val isLast: Boolean,
    val totalPage: Int,
    val partyList: List<ViewingPartyElementModel>
) {
    data class ViewingPartyElementModel(
        val id: Long,
        val name: String,
        val writerInfo: String,
        val photoURL: String,
        val partyDate: String,
        val latitude: Double,
        val longitude: Double,
        val location: String,
        val shortLocation: String?
    )
}
