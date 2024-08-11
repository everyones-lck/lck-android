package umc.everyones.lck.data.dto.response.party

import umc.everyones.lck.domain.model.response.party.ViewingPartyParticipantsModel

data class ViewingPartyParticipantsResponseDto(
    val viewingPartyName: String,
    val ownerName: String,
    val ownerTeam: String,
    val ownerImage: String,
    val participantList: List<ParticipantsDto>
){
    data class ParticipantsDto(
        val id: Long,
        val name: String,
        val team: String,
        val image: String
    ){
        fun toParticipantsModel() =
            ViewingPartyParticipantsModel.ParticipantsModel(id, name, team, image)
    }

    fun toViewingPartyParticipantsModel() =
        ViewingPartyParticipantsModel(viewingPartyName, ownerName, ownerTeam, ownerImage, participantList.map { it.toParticipantsModel() })
}
