package umc.everyones.lck.domain.model.request.match

import umc.everyones.lck.data.dto.request.match.CommonPogRequestDto
import java.io.Serializable

data class CommonPogModel(
    val matchId: Long
): Serializable {
    fun toCommonPogRequestDto() =
        CommonPogRequestDto(
            matchId
        )
}
