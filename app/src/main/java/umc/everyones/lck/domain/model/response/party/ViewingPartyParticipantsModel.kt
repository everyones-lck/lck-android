package umc.everyones.lck.domain.model.response.party

import umc.everyones.lck.data.dto.response.party.ViewingPartyParticipantsResponseDto

data class ViewingPartyParticipantsModel(
    val viewingPartyName: String,
    val ownerName: String,
    val ownerTeam: String,
    val ownerImage: String,
    val participantList: List<ParticipantsModel>
) {
    data class ParticipantsModel(
        val id: Long,
        val name: String,
        val team: String,
        val image: String
    )
}