package umc.everyones.lck.presentation.mypage.community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import umc.everyones.lck.domain.model.community.Comment
import umc.everyones.lck.domain.model.community.Post
import kotlin.random.Random

class MyPageCommunityViewModel : ViewModel() {

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> get() = _posts

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>> get() = _comments

    init {
        val categories = listOf(
            "#잡담게시판",
            "#응원게시판",
            "#질문게시판",
            "#FA게시판",
            "#후기게시판",
            "#거래게시판"
        )
        val postList = listOf(
            "배고프다",
            "오늘 LCK 재밌었다",
            "우와우와우와우"
        )
        val commentsList = listOf(
            "가나다라마바사아자차카타파하",
            "수수수수퍼보나 사건은 다가와 ah oh ay",
            "그대 나의 작은 심장에 귀 기울일 때에 입을 꼭 맞추어 내 숨을 가져가도 돼요",
            "나 그대의 품에 안겨서 입을 맞추고 Rock 'n' roll save my life",
            "그땐 난 어떤 마음이었길래 내 모든 걸 주고도 웃을 수 있었나",
            "저 멀리 기다리는 이름 모를 고민들과 언젠가 그리워질 지나간 것들도 안녕"
        )

        val posts = mutableListOf<Post>()
        val comments = mutableListOf<Comment>()

        for (index in 0 until 10) {
            val post = Post(
                postId = index,
                writer = "User$index",
                title = postList[Random.nextInt(postList.size)],
                body = "Post body $index",
                category = categories[Random.nextInt(categories.size)]
            )
            posts.add(post)
        }

        for (commentIndex in 0 until 10) {
            val comment = Comment(
                commentId = commentIndex + 100,
                nickname = "User$commentIndex",
                favoriteTeam = "Team${Random.nextInt(5)}",
                body = commentsList[Random.nextInt(commentsList.size)],
                date = "2024-08-06",
                profileImageUrl = "https://example.com/profile${commentIndex}.png",
                category = categories[Random.nextInt(categories.size)]
            )
            comments.add(comment)
        }
        _posts.value = posts
        _comments.value = comments
    }
}
