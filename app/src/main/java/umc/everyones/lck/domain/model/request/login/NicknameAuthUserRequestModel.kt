package umc.everyones.lck.domain.model.request.login

import umc.everyones.lck.data.dto.request.login.NicknameAuthUserRequestDto
import java.io.Serializable

data class NicknameAuthUserRequestModel(
    val nickName: String
): Serializable {
    fun toNicknameAuthUserRequestDto() =
        NicknameAuthUserRequestDto(nickName)
}
