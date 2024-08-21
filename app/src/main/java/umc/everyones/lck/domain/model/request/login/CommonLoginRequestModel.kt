package umc.everyones.lck.domain.model.request.login

import umc.everyones.lck.data.dto.request.login.CommonLoginRequestDto
import java.io.Serializable

data class CommonLoginRequestModel(
    val kakaoUserId: String
):Serializable {
    fun toCommonLoginRequestDto() =
        CommonLoginRequestDto(kakaoUserId)
}
