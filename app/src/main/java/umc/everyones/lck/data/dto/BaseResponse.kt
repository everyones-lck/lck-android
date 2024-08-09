package umc.everyones.lck.data.dto

data class BaseResponse<T>(
    val message: String,
    val data: T,
    val success: Boolean
)