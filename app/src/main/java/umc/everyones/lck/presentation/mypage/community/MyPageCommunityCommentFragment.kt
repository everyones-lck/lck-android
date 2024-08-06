package umc.everyones.lck.presentation.mypage.community

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentMypageCommunityCommentBinding
import umc.everyones.lck.presentation.base.BaseFragment

@AndroidEntryPoint
class MyPageCommunityCommentFragment : BaseFragment<FragmentMypageCommunityCommentBinding>(R.layout.fragment_mypage_community_comment) {

    private val viewModel: MyPageCommunityViewModel by viewModels()
    private lateinit var adapter: MyPageCommunityItemAdapter

    override fun initView() {
        // RecyclerView 초기화
        adapter = MyPageCommunityItemAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    override fun initObserver() {
        viewModel.comments.observe(viewLifecycleOwner) { comments ->
            adapter.submitList(comments.map { CommunityItem.CommentItem(it) })
        }
    }
}
