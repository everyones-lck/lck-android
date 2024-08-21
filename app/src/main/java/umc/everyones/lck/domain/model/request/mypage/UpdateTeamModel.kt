package umc.everyones.lck.domain.model.request.mypage

import umc.everyones.lck.data.dto.request.mypage.UpdateTeamRequestDto
import java.io.Serializable

data class UpdateTeamModel(
    val teamId: Int
):Serializable {
    fun toUpdateTeamRequestDto() =
        UpdateTeamRequestDto(teamId)
}
