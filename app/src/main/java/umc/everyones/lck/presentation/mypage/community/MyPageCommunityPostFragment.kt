package umc.everyones.lck.presentation.mypage.community

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentMypageCommunityPostBinding
import umc.everyones.lck.presentation.base.BaseFragment

@AndroidEntryPoint
class MyPageCommunityPostFragment : BaseFragment<FragmentMypageCommunityPostBinding>(R.layout.fragment_mypage_community_post) {

    private val viewModel: MyPageCommunityViewModel by viewModels()
    private lateinit var adapter: MyPageCommunityItemAdapter

    override fun initView() {
        adapter = MyPageCommunityItemAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        binding.viewModel = viewModel  // ViewModel을 데이터 바인딩에 연결
    }

    override fun initObserver() {
        viewModel.posts.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts.map { CommunityItem.PostItem(it) })
        }
    }
}
