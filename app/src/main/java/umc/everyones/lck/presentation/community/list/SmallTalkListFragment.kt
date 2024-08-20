package umc.everyones.lck.presentation.community.list

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collectLatest
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentPostListBinding
import umc.everyones.lck.domain.model.community.CommunityListModel
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.community.CommunityViewModel
import umc.everyones.lck.presentation.community.adapter.PostListRVA
import umc.everyones.lck.presentation.community.read.ReadPostActivity
import umc.everyones.lck.presentation.community.read.ReadPostViewModel
import umc.everyones.lck.util.extension.repeatOnStarted

class SmallTalkListFragment  : BaseFragment<FragmentPostListBinding>(R.layout.fragment_post_list) {
    private val navigator by lazy {
        findNavController()
    }
    private val viewModel: CommunityViewModel by activityViewModels()
    private var _postListRVA: PostListRVA? = null
    private val postListRVA
        get() = _postListRVA

    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            viewModel.smallTalkListPage.collectLatest { data ->
                postListRVA?.submitData(data)
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.categoryNeedsRefresh.collect { categoryNeedsRefresh ->
                Log.d("categoryNeedsRefresh", categoryNeedsRefresh)
                if (categoryNeedsRefresh == CATEGORY) {
                    postListRVA?.refresh()
                    binding.rvPostList.scrollToPosition(0)
                }
            }
        }
    }

    override fun initView() {
        initPostListRVAdapter()
    }

    private fun initPostListRVAdapter() {
        _postListRVA = PostListRVA { postId ->
            startActivity(ReadPostActivity.newIntent(requireContext(), postId))
        }
        binding.rvPostList.adapter = postListRVA
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _postListRVA = null
    }

    companion object {
        private const val CATEGORY = "잡담"
    }
}