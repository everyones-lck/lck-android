package umc.everyones.lck.data

data class UpdateProfileRequest(
    val nickname: String,
    val isDefaultImage: Boolean
)
