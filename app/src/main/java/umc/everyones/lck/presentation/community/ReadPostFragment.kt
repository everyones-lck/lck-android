package umc.everyones.lck.presentation.community

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentReadPostBinding
import umc.everyones.lck.domain.model.community.Comment
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.community.adapter.CommentRVA
import umc.everyones.lck.presentation.community.adapter.ReadMediaRVA
import umc.everyones.lck.util.GridSpaceItemDecoration

class ReadPostFragment : BaseFragment<FragmentReadPostBinding>(R.layout.fragment_read_post) {
    private val navigator by lazy {
        findNavController()
    }
    private val args: ReadPostFragmentArgs by navArgs()
    private val postId by lazy {
        args.postId
    }
    private var _commentRVA: CommentRVA? = null
    private val commentRVA get() = _commentRVA

    private var _readMediaRVA: ReadMediaRVA? = null
    private val readMediaRVA get() = _readMediaRVA
    override fun initObserver() {

    }

    override fun initView() {
        initCommentRVAdapter()
        initReadMediaRVAdapter()
    }

    private fun initCommentRVAdapter(){
        _commentRVA = CommentRVA(
            editComment = { commentId, Body -> },
            reportComment = { commentId -> },
            deleteComment = { commentId -> }
        )
        binding.rvReadComment.adapter = commentRVA
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
        commentRVA?.submitList(list)
    }

    private fun initReadMediaRVAdapter(){
        _readMediaRVA = ReadMediaRVA { url ->  }
        binding.rvReadMedia.adapter = readMediaRVA
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

    override fun onDestroyView() {
        super.onDestroyView()
        _commentRVA = null
        _readMediaRVA = null
    }
}