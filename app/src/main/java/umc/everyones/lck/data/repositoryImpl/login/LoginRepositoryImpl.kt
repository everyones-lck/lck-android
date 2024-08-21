package umc.everyones.lck.data.repositoryImpl.login

import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.everyones.lck.data.datasource.login.LoginDataSource
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.domain.model.request.login.CommonLoginRequestModel
import umc.everyones.lck.domain.model.request.login.NicknameAuthUserRequestModel
import umc.everyones.lck.domain.model.response.login.CommonLoginResponseModel
import umc.everyones.lck.domain.repository.login.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
): LoginRepository{
    override suspend fun signup(
        signupUserData: RequestBody,
        profileImage: MultipartBody.Part
    ): Result<CommonLoginResponseModel> =
        runCatching { loginDataSource.signup(signupUserData, profileImage).data.toCommonLoginResponseDto() }

    override suspend fun login(request:CommonLoginRequestModel): Result<CommonLoginResponseModel> =
        runCatching { loginDataSource.login(request.toCommonLoginRequestDto()).data.toCommonLoginResponseDto()}

    override suspend fun refresh(request: CommonLoginRequestModel): Result<CommonLoginResponseModel> =
        runCatching { loginDataSource.refresh(request.toCommonLoginRequestDto()).data.toCommonLoginResponseDto() }

    override suspend fun nickname(request: NicknameAuthUserRequestModel): Result<Boolean> =
        runCatching {loginDataSource.nickname(request.toNicknameAuthUserRequestDto()).data}

    override suspend fun usertest(token: String): Result<Unit> =
        runCatching { loginDataSource.usertest(token)
            Unit }
}
