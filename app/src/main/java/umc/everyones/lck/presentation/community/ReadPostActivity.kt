package umc.everyones.lck.presentation.community

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.ActivityReadPostBinding
import umc.everyones.lck.domain.model.community.Comment
import umc.everyones.lck.domain.model.community.Post
import umc.everyones.lck.domain.model.community.PostListItem
import umc.everyones.lck.presentation.base.BaseActivity
import umc.everyones.lck.presentation.community.adapter.CommentRVA
import umc.everyones.lck.presentation.community.adapter.ReadMediaRVA
import umc.everyones.lck.util.GridSpaceItemDecoration
import umc.everyones.lck.util.extension.drawableOf
import umc.everyones.lck.util.extension.setOnSingleClickListener
import umc.everyones.lck.util.extension.showCustomSnackBar

@AndroidEntryPoint
class ReadPostActivity : BaseActivity<ActivityReadPostBinding>(R.layout.activity_read_post) {
    private val commentRVA by lazy {
        CommentRVA(
            editComment = { commentId, Body -> },
            reportComment = { commentId -> },
            deleteComment = { commentId -> }
        )
    }

    private val readMediaRVA by lazy {
        ReadMediaRVA { url -> }
    }

    private val postId by lazy {
        intent.getIntExtra("postId", 0)
    }

    override fun initObserver() {

    }

    override fun initView() {
        initCommentRVAdapter()
        initReadMediaRVAdapter()
        validateCommentSend()
        editPost()
        binding.ivReadBackBtn.setOnSingleClickListener {
            finish()
        }
        Log.d("postId", postId.toString())
    }

    private fun editPost() {
        binding.layoutReadEditBtn.setOnClickListener {
            Log.d("click", "click")
            startActivity(
                WritePostActivity.EditIntent(
                    this,
                    Post(
                        0, "ㅇㅇ", binding.tvReadPostTitle.text.toString(),
                        binding.tvReadPostBody.text.toString(), "후기"
                    )
                )
            )
        }
    }

    private fun initCommentRVAdapter() {
        binding.rvReadComment.apply {
            adapter = commentRVA
            itemAnimator = null
        }
        val list = listOf(
            Comment(0, "ㅇㄴㅇㄴ", "ㅇㄴ", "ㅇㄴ", "ㅇㄴ", "ㄴㅇ"),
            Comment(0, "ㅇㄴㅇㄴ", "ㅇㄴ", "ㅇㄴ", "ㅇㄴ", "ㄴㅇ"),
            Comment(0, "ㅇㄴㅇㄴ", "ㅇㄴ", "ㅇㄴ", "ㅇㄴ", "ㄴㅇ"),
            Comment(0, "ㅇㄴㅇㄴ", "ㅇㄴ", "ㅇㄴ", "ㅇㄴ", "ㄴㅇ"),
            Comment(0, "ㅇㄴㅇㄴ", "ㅇㄴ", "ㅇㄴ", "ㅇㄴ", "ㄴㅇ"),
            Comment(0, "ㅇㄴㅇㄴ", "ㅇㄴ", "ㅇㄴ", "ㅇㄴ", "ㄴㅇ"),
            Comment(0, "ㅇㄴㅇㄴ", "ㅇㄴ", "ㅇㄴ", "ㅇㄴ", "ㄴㅇ"),
            Comment(0, "ㅇㄴㅇㄴ", "ㅇㄴ", "ㅇㄴ", "ㅇㄴ", "ㄴㅇ"),
        )
        commentRVA.submitList(list)

    }

    private fun initReadMediaRVAdapter() {
        binding.rvReadMedia.apply {
            adapter = readMediaRVA
            itemAnimator = null
        }
        val list = listOf(
            "dsdsd",
            "dsdsd",
            "dsdsd",
            "dsdsd",
            "dsdsd",
            "dsdsd",
            "dsdsd",
        )
        binding.rvReadMedia.addItemDecoration(GridSpaceItemDecoration(4, 8))
        readMediaRVA.submitList(list)
    }

    private fun validateCommentSend() {
        binding.etReadCommentInput.addTextChangedListener(
            onTextChanged = { text: CharSequence?, _: Int, _: Int, _: Int ->
                if (text != null) {
                    binding.ivReadSendCommentBtn.setImageDrawable(drawableOf(R.drawable.ic_send_enabled))
                    if (text.length >= 1000) {
                        showCustomSnackBar(
                            binding.ivReadSendCommentBtn,
                            "댓글은 최대 1,000자까지 입력할 수 있어요"
                        )
                    }
                } else {
                    binding.ivReadSendCommentBtn.setImageDrawable(drawableOf(R.drawable.ic_send))
                }
            }
        )
    }

    companion object {
        fun newIntent(context: Context, postId: Int) =
            Intent(context, ReadPostActivity::class.java).apply {
                putExtra("postId", postId)
            }
    }

}