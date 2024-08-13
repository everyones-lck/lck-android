package umc.everyones.lck.data.dto.request.login

import umc.everyones.lck.domain.model.request.login.CommonLoginRequestModel
import umc.everyones.lck.domain.model.response.login.CommonLoginResponseModel

data class CommonLoginRequestDto(
    val kakaoUserId: String
){
    fun toCommonLoginRequestDto() =
        CommonLoginRequestModel(kakaoUserId)
}
