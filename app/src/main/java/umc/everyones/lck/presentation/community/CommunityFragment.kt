package umc.everyones.lck.presentation.community

import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentCommunityBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.community.adapter.PostListVPA

@AndroidEntryPoint
class CommunityFragment : BaseFragment<FragmentCommunityBinding>(R.layout.fragment_community) {
    private val postListVPA by lazy {
        PostListVPA(this)
    }
    override fun initObserver() {

    }

    override fun initView() {
        initPostListVPAdapter()
    }

    private fun initPostListVPAdapter(){
        val tabTitles = listOf("잡담", "응원", "FA", "거래", "질문", "후기")
        with(binding){
            vpCommunityPostList.adapter = postListVPA

            TabLayoutMediator(tabCommunityCategory, vpCommunityPostList) { tab, position ->
                tab.text = tabTitles[position]
            }.attach()
        }
    }
}