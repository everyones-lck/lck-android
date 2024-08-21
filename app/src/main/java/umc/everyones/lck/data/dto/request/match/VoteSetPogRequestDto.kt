package umc.everyones.lck.data.dto.request.match

data class VoteSetPogRequestDto(
    val matchId: Long,
    val setIndex: Int,
    val playerId: Int
)
