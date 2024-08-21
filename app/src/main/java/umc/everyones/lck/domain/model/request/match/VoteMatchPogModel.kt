package umc.everyones.lck.domain.model.request.match

import umc.everyones.lck.data.dto.request.match.VoteMatchPogRequestDto
import java.io.Serializable

data class VoteMatchPogModel(
    val matchId: Long,
    val playerId: Int
): Serializable {
    fun toVoteMatchPogRequestDto() =
        VoteMatchPogRequestDto(
            matchId,
            playerId
        )
}
