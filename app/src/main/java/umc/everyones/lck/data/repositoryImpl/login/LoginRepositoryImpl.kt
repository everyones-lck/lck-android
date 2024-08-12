package umc.everyones.lck.data.repositoryImpl.login

import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.everyones.lck.data.datasource.login.LoginDataSource
import umc.everyones.lck.domain.model.response.login.SignupAuthUserModel
import umc.everyones.lck.domain.repository.login.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
): LoginRepository{
    override suspend fun signup(
        kakaUserId: RequestBody,
        nickName: RequestBody,
        role: RequestBody,
        teamId: RequestBody,
        profileImage: MultipartBody.Part
    ): Result<SignupAuthUserModel> =
        runCatching { loginDataSource.signup(kakaUserId, nickName, role, teamId, profileImage).data.toSignupAuthUserModel() }
}