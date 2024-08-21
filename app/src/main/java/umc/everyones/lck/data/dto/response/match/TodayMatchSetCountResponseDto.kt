package umc.everyones.lck.data.dto.response.match

import umc.everyones.lck.domain.model.response.match.TodayMatchSetCountModel

data class TodayMatchSetCountResponseDto(
    val setCount: Int
) {
    fun toTodayMatchSetCountModel() =
        TodayMatchSetCountModel(setCount)
}
