package umc.everyones.lck.domain.model.request.match

import umc.everyones.lck.data.dto.request.match.VoteMatchPogRequestDto
import umc.everyones.lck.data.dto.request.match.VoteMatchRequestDto
import java.io.Serializable

data class VoteMatchModel(
    val matchId: Long,
    val teamId: Int
): Serializable {
    fun toVoteMatchRequestDto() =
        VoteMatchRequestDto(
            matchId,
            teamId
        )
}
