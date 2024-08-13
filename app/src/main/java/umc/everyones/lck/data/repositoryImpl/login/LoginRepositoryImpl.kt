package umc.everyones.lck.data.repositoryImpl.login

import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.everyones.lck.data.datasource.login.LoginDataSource
import umc.everyones.lck.domain.model.request.login.LoginAuthUserModel
import umc.everyones.lck.domain.model.response.login.CommonLoginModel
import umc.everyones.lck.domain.repository.login.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
): LoginRepository{
    override suspend fun signup(
        kakaoUserId: RequestBody,
        nickName: RequestBody,
        role: RequestBody,
        teamId: RequestBody,
        profileImage: MultipartBody.Part
    ): Result<CommonLoginModel> =
        runCatching { loginDataSource.signup(kakaoUserId, nickName, role, teamId, profileImage).data.toCommonLoginResponseDto() }

    override suspend fun login(request:LoginAuthUserModel): Result<CommonLoginModel> =
        runCatching { loginDataSource.login(request.toLoginAuthUserRequestDto()).data.toCommonLoginResponseDto()}
}