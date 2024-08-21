package umc.everyones.lck.domain.model.request.match

import umc.everyones.lck.data.dto.request.match.VoteSetPogRequestDto
import java.io.Serializable

data class VoteSetPogModel(
    val matchId: Long,
    val setIndex: Int,
    val playerId: Int
): Serializable {
    fun toVoteSetPogRequestDto() =
        VoteSetPogRequestDto(
            matchId,
            setIndex,
            playerId
        )
}
