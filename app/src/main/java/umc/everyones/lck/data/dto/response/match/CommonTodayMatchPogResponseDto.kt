package umc.everyones.lck.data.dto.response.match

import umc.everyones.lck.domain.model.response.match.CommonTodayMatchPogModel

data class CommonTodayMatchPogResponseDto(
    val id: Int,
    val name: String,
    val profileImageUrl: String,
    val seasonInfo: String,
    val matchNumber: Int,
    val matchDate: String
) {
    fun toCommonTodayMatchPogModel() =
        CommonTodayMatchPogModel(id, name, profileImageUrl, seasonInfo, matchNumber, matchDate)
}
