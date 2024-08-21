package umc.everyones.lck.presentation.community.list

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentPostListBinding
import umc.everyones.lck.domain.model.community.CommunityListModel
import umc.everyones.lck.domain.model.community.PostListItem
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.community.adapter.PostListRVA
import umc.everyones.lck.presentation.community.read.ReadPostActivity
import umc.everyones.lck.presentation.community.read.ReadPostViewModel

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
    }
}