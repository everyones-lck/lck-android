package umc.everyones.lck.domain.model.community

import java.io.Serializable

data class EditPost(
    val postId: Long,
    val title: String,
    val body: String,
    val category: String
): Serializable
