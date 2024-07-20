package umc.everyones.lck.presentation.community

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentPostListBinding
import umc.everyones.lck.domain.model.community.Post
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.community.adapter.PostListRVA

class PostListFragment : BaseFragment<FragmentPostListBinding>(R.layout.fragment_post_list) {
    private val navigator by lazy {
        findNavController()
    }
    private val viewModel: ReadPostViewModel by viewModels({requireParentFragment()})
    private val postListRVA by lazy {
        PostListRVA{ postId ->
            startActivity(ReadPostActivity.newIntent(requireContext(), postId))
        }
    }

    override fun initObserver() {

    }

    override fun initView() {
        initPostListRVAdapter()
    }

    private fun initPostListRVAdapter(){
        binding.rvPostList.adapter = postListRVA
        val list = listOf(
            Post(1, "테스트", "2024.01.01", "ㅇㄴㅇㄴㅇ", "T1", 20, ""),
            Post(1, "테스트", "2024.01.01", "ㅇㄴㅇㄴㅇ", "T1", 20, ""),
            Post(1, "테스트", "2024.01.01", "ㅇㄴㅇㄴㅇ", "T1", 20, ""),
            Post(1, "테스트", "2024.01.01", "ㅇㄴㅇㄴㅇ", "T1", 20, ""),
            Post(1, "테스트", "2024.01.01", "ㅇㄴㅇㄴㅇ", "T1", 20, ""),
            Post(1, "테스트", "2024.01.01", "ㅇㄴㅇㄴㅇ", "T1", 20, ""),
            Post(1, "테스트", "2024.01.01", "ㅇㄴㅇㄴㅇ", "T1", 20, ""),
            Post(1, "테스트", "2024.01.01", "ㅇㄴㅇㄴㅇ", "T1", 20, ""),
            Post(1, "테스트", "2024.01.01", "ㅇㄴㅇㄴㅇ", "T1", 20, ""),
            Post(1, "테스트", "2024.01.01", "ㅇㄴㅇㄴㅇ", "T1", 20, ""),
            Post(1, "테스트", "2024.01.01", "ㅇㄴㅇㄴㅇ", "T1", 20, ""),
            Post(1, "테스트", "2024.01.01", "ㅇㄴㅇㄴㅇ", "T1", 20, ""),
            Post(1, "테스트", "2024.01.01", "ㅇㄴㅇㄴㅇ", "T1", 20, ""),
            Post(1, "테스트", "2024.01.01", "ㅇㄴㅇㄴㅇ", "T1", 20, ""),
            Post(1, "테스트", "2024.01.01", "ㅇㄴㅇㄴㅇ", "T1", 20, ""),
            Post(1, "테스트", "2024.01.01", "ㅇㄴㅇㄴㅇ", "T1", 20, ""),
            Post(1, "테스트", "2024.01.01", "ㅇㄴㅇㄴㅇ", "T1", 20, ""),
            )
        postListRVA.submitList(list)
    }
}