package umc.everyones.lck.presentation.community

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentCommunityBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.community.adapter.PostListVPA
import umc.everyones.lck.util.extension.repeatOnStarted

@AndroidEntryPoint
class CommunityFragment : BaseFragment<FragmentCommunityBinding>(R.layout.fragment_community) {
    private val viewModel: ReadPostViewModel by viewModels()
    private val navigator by lazy {
        findNavController()
    }

    private var _postListVPA: PostListVPA? = null
    private val postListVPA get() = _postListVPA

    override fun initObserver() {
        repeatOnStarted {
            viewModel.postId.collect{
                if(it > 0 && navigator.currentDestination?.id == R.id.communityFragment) {
                    val action =
                        CommunityFragmentDirections.actionCommunityFragmentToReadPostFragment(it)
                    navigator.navigate(action)
                }
            }
        }
    }

    override fun initView() {
        goToWritePost()
        initPostListVPAdapter()
    }

    private fun initPostListVPAdapter(){
        _postListVPA = PostListVPA(this)
        with(binding){
            vpCommunityPostList.adapter = postListVPA

            TabLayoutMediator(tabCommunityCategory, vpCommunityPostList) { tab, position ->
                tab.text = tabTitles[position]
            }.attach()
        }
    }

    private fun goToWritePost(){
        binding.fabCommunityWriteBtn.setOnClickListener {
            navigator.navigate(R.id.action_communityFragment_to_writePostFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _postListVPA = null
    }

    companion object {
        private val tabTitles = listOf("잡담", "응원", "FA", "거래", "질문", "후기")
    }
}