package umc.everyones.lck.domain.model.request.login

import umc.everyones.lck.data.dto.request.login.NicknameAuthUserRequestDto

data class NicknameAuthUserRequestModel(
    val nickname: String
){
    fun toNicknameAuthUserRequestDto() =
        NicknameAuthUserRequestDto(nickname)
}
