package umc.everyones.lck.domain.model.request.login

import umc.everyones.lck.data.dto.request.login.LoginAuthUserRequestDto
import java.io.Serializable

data class LoginAuthUserModel(
    val kakaoUserId: String
):Serializable {
    fun toLoginAuthUserRequestDto() =
        LoginAuthUserRequestDto(kakaoUserId)
}
