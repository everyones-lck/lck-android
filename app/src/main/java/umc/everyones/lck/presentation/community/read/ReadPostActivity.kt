package umc.everyones.lck.presentation.community.read

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.ActivityReadPostBinding
import umc.everyones.lck.domain.model.community.Comment
import umc.everyones.lck.domain.model.community.Post
import umc.everyones.lck.domain.model.response.community.ReadCommunityResponseModel
import umc.everyones.lck.presentation.MainActivity
import umc.everyones.lck.presentation.base.BaseActivity
import umc.everyones.lck.presentation.community.write.WritePostActivity
import umc.everyones.lck.presentation.community.adapter.CommentRVA
import umc.everyones.lck.presentation.community.adapter.ReadMediaRVA
import umc.everyones.lck.util.GridSpaceItemDecoration
import umc.everyones.lck.util.KeyboardUtil
import umc.everyones.lck.util.extension.drawableOf
import umc.everyones.lck.util.extension.repeatOnStarted
import umc.everyones.lck.util.extension.setOnSingleClickListener
import umc.everyones.lck.util.extension.showCustomSnackBar
import umc.everyones.lck.util.network.UiState

@AndroidEntryPoint
class ReadPostActivity : BaseActivity<ActivityReadPostBinding>(R.layout.activity_read_post) {
    private val viewModel: ReadPostViewModel by viewModels()
    private val commentRVA by lazy {
        CommentRVA(
            // 댓글 신고 기능
            reportComment = { commentId ->
                            showCustomSnackBar(binding.layoutReadReportBtn, "댓글이 신고 되었습니다")
            },

            // 댓글 삭제 기능
            deleteComment = { commentId -> }
        )
    }

    private val readMediaRVA by lazy {
        ReadMediaRVA { url ->
            // 미디어 원본 보기 기능
        }
    }

    // Community Fragment에서 전송한 postId 수신
    private val postId by lazy {
        intent.getLongExtra("postId", 0)
    }

    override fun initObserver() {
        repeatOnStarted {
            viewModel.readCommunityEvent.collect { state ->
                when(state){
                    is UiState.Success -> handleReadCommunityEvent(state.data)
                    is UiState.Failure -> showCustomSnackBar(binding.root, state.msg)
                    else -> Unit
                }
            }
        }

        repeatOnStarted {
            viewModel.isWriter.collect{ isWriter ->
                with(binding) {
                    if (isWriter) {
                        layoutReadReportBtn.visibility = View.GONE
                        layoutReadWriterMenu.visibility = View.VISIBLE
                    } else {
                        layoutReadReportBtn.visibility = View.VISIBLE
                        layoutReadWriterMenu.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun handleReadCommunityEvent(event: ReadPostViewModel.ReadCommunityEvent){
        when(event){
            ReadPostViewModel.ReadCommunityEvent.DeletePost -> {
                setResult(RESULT_OK,
                    MainActivity.readMenuDoneIntent(this, true))
                finish()
            }
            ReadPostViewModel.ReadCommunityEvent.EditPost -> {}
            is ReadPostViewModel.ReadCommunityEvent.ReadPost -> {
                with(event.post){
                    binding.tvReadPostTitle.text = postTitle
                    binding.tvReadPostBody.text = content
                    binding.tvReadWriter.text = writerInfo
                    binding.tvReadCategory.text = postType
                    binding.tvReadDate.text = postCreatedAt
                    Glide.with(this@ReadPostActivity)
                        .load(writerProfileUrl)
                        .into(binding.ivReadProfileImage)
                    commentRVA.submitList(commentList)
                    readMediaRVA.submitList(fileUrlList)
                }
            }
            ReadPostViewModel.ReadCommunityEvent.ReportComment -> {}
            ReadPostViewModel.ReadCommunityEvent.ReportPost -> {}
        }
    }

    override fun initView() {
        // 키보드 올라올 때 화면 맨 밑으로 자동스크롤을 위한 리스너 등록
        viewModel.setPostId(postId)
        KeyboardUtil.registerKeyboardVisibilityListener(binding.root, binding.svRead)
        initCommentRVAdapter()
        initReadMediaRVAdapter()

        // 댓글 유효성 검사
        validateCommentSend()
        editPost()
        reportPost()
        deletePost()
        viewModel.fetchCommunityPost()

        binding.ivReadBackBtn.setOnSingleClickListener {
            finish()
        }
        Log.d("postId", postId.toString())
    }

    private fun reportPost(){
        binding.layoutReadReportBtn.setOnSingleClickListener {
            showCustomSnackBar(binding.layoutReadReportBtn, "게시글이 신고 되었습니다")
        }
    }

    private fun editPost() {
        binding.layoutReadEditBtn.setOnSingleClickListener {
            // 글 작성 화면으로 이동 및 현재 게시글 Data 전송
            startActivity(
                WritePostActivity.editIntent(
                    this,
                    Post(
                        0, "ㅇㅇ", binding.tvReadPostTitle.text.toString(),
                        binding.tvReadPostBody.text.toString(), "후기"
                    )
                )
            )
        }
    }

    private fun deletePost(){
        binding.layoutReadDeleteBtn.setOnSingleClickListener {
            viewModel.deleteCommunityPost()
        }
    }

    private fun initCommentRVAdapter() {
        binding.rvReadComment.apply {
            adapter = commentRVA
            itemAnimator = null
        }
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

        // RecyclerView Item의 GridLayout에서의 일정한 간격을 위해 설정
        binding.rvReadMedia.addItemDecoration(GridSpaceItemDecoration(4, 8))
        readMediaRVA.submitList(list)
    }

    private fun validateCommentSend() {
        binding.etReadCommentInput.addTextChangedListener(
            onTextChanged = { text: CharSequence?, _: Int, _: Int, _: Int ->
                if (text != null) {

                    // 댓글 작성 여부에 따른 전송 버튼 활성화 제어
                    if(text.isEmpty()){
                        binding.ivReadSendCommentBtn.setImageDrawable(drawableOf(R.drawable.ic_send))
                    } else {
                        binding.ivReadSendCommentBtn.setImageDrawable(drawableOf(R.drawable.ic_send_enabled))
                    }

                    if (text.length >= 1000) {
                        showCustomSnackBar(
                            binding.ivReadSendCommentBtn,
                            "댓글은 최대 1,000자까지 입력할 수 있어요"
                        )
                    }
                }
            }
        )
    }

    companion object {
        fun newIntent(context: Context, postId: Long) =
            Intent(context, ReadPostActivity::class.java).apply {
                putExtra("postId", postId)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        KeyboardUtil.unregisterKeyboardVisibilityListener(binding.root)
    }
}