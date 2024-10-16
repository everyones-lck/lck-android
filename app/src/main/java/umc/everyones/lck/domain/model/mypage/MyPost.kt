package umc.everyones.lck.domain.model.mypage

import java.io.Serializable

data class MyPost(
    val id: Int,
    val title: String,
    val postType: String
): Serializable
