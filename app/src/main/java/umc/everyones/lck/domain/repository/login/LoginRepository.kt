package umc.everyones.lck.domain.repository.login

import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.everyones.lck.data.dto.response.login.CommonLoginResponseDto
import umc.everyones.lck.domain.model.request.login.CommonLoginRequestModel
import umc.everyones.lck.domain.model.request.login.NicknameAuthUserRequestModel
import umc.everyones.lck.domain.model.response.login.CommonLoginResponseModel

interface LoginRepository {
    suspend fun signup(kakaoUserId: RequestBody, nickName: RequestBody, role: RequestBody, teamId: RequestBody, profileImage: MultipartBody.Part): Result<CommonLoginResponseModel>

    suspend fun login(request: CommonLoginRequestModel): Result<CommonLoginResponseModel>

    suspend fun refresh(request: CommonLoginRequestModel): Result<CommonLoginResponseModel>

    suspend fun nickname(request: NicknameAuthUserRequestModel): Result<Unit>
}