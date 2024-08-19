package umc.everyones.lck.domain.model.community

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import umc.everyones.lck.data.dto.request.community.WriteCommunityRequestDto

data class WriteCommunityRequestModel(
    val files: List<MultipartBody.Part>,
    val request: WriteRequestModel
) {
    data class WriteRequestModel(
        val postType: String,
        val postTitle: String,
        val postContent: String
    ) {
        fun toWriteRequestDto() =
            Gson().toJson(this).toRequestBody("application/json".toMediaTypeOrNull())
    }

    fun toWriteCommunityRequestDto() =
        WriteCommunityRequestDto(files, request.toWriteRequestDto())
}
