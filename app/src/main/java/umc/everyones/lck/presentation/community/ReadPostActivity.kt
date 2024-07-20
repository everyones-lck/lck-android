package umc.everyones.lck.presentation.community

import android.content.Context
import android.content.Intent
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import umc.everyones.lck.R
import umc.everyones.lck.databinding.ActivityReadPostBinding
import umc.everyones.lck.domain.model.community.Comment
import umc.everyones.lck.presentation.base.BaseActivity
import umc.everyones.lck.presentation.community.adapter.CommentRVA
import umc.everyones.lck.presentation.community.adapter.ReadMediaRVA
import umc.everyones.lck.util.GridSpaceItemDecoration
import umc.everyones.lck.util.extension.drawableOf
import umc.everyones.lck.util.extension.setOnSingleClickListener
import umc.everyones.lck.util.extension.showCustomSnackBar

@AndroidEntryPoint
class ReadPostActivity : BaseActivity<ActivityReadPostBinding>(R.layout.activity_read_post) {
    private var _commentRVA: CommentRVA? = null
    private val commentRVA get() = _commentRVA

    private var _readMediaRVA: ReadMediaRVA? = null
    private val readMediaRVA get() = _readMediaRVA
    override fun initObserver() {

    }

    override fun initView() {
        initCommentRVAdapter()
        initReadMediaRVAdapter()
        validateCommentSend()
        binding.ivReadBackBtn.setOnSingleClickListener {
            //navigator.navigateUp()
        }
    }

    private fun initCommentRVAdapter(){
        _commentRVA = CommentRVA(
            editComment = { commentId, Body -> },
            reportComment = { commentId -> },
            deleteComment = { commentId -> }
        )
        binding.rvReadComment.adapter = commentRVA
        binding.rvReadComment.itemAnimator = null
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
        lifecycleScope.launch {
            commentRVA?.submitList(list)
        }
    }

    private fun initReadMediaRVAdapter(){
        _readMediaRVA = ReadMediaRVA { url ->  }
        binding.rvReadMedia.adapter = readMediaRVA
        binding.rvReadMedia.itemAnimator = null
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
        readMediaRVA?.submitList(list)
    }

    private fun validateCommentSend(){
        binding.etReadCommentInput.addTextChangedListener(
            onTextChanged = {text: CharSequence?, _: Int, _: Int, _: Int ->
                if(text != null){
                    binding.ivReadSendCommentBtn.setImageDrawable(drawableOf(R.drawable.ic_send_enabled))
                    if(text.length >= 1000){
                        showCustomSnackBar(binding.ivReadSendCommentBtn, "댓글은 최대 1,000자까지 입력할 수 있어요")
                    }
                } else {
                    binding.ivReadSendCommentBtn.setImageDrawable(drawableOf(R.drawable.ic_send))
                }
            }
        )
    }

    companion object{
        fun newIntent(context: Context) =
            Intent(context, ReadPostActivity::class.java).apply {
            }
    }

}