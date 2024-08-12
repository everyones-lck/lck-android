package umc.everyones.lck.data.datasource.login

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part
import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.response.login.SignupAuthUserResponseDto

interface LoginDataSource {
    suspend fun signup(kakaUserId: RequestBody,nickName: RequestBody, role: RequestBody,teamId: RequestBody,profileImage: MultipartBody.Part): BaseResponse<SignupAuthUserResponseDto>


}