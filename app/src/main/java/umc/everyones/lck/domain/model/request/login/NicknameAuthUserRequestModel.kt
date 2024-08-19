package umc.everyones.lck.domain.model.request.login

import umc.everyones.lck.data.dto.request.login.NicknameAuthUserRequestDto
import java.io.Serializable

data class NicknameAuthUserRequestModel(
    val nickname: String
): Serializable {
    fun toNicknameAuthUserRequestDto() =
        NicknameAuthUserRequestDto(nickname)
}
