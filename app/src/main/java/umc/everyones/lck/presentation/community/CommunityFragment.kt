package umc.everyones.lck.presentation.community

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentCommunityBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.community.adapter.PostListVPA
import umc.everyones.lck.presentation.community.read.ReadPostViewModel
import umc.everyones.lck.presentation.community.write.WritePostActivity
import umc.everyones.lck.presentation.community.write.WritePostViewModel
import umc.everyones.lck.presentation.mypage.MyPageActivity
import umc.everyones.lck.util.extension.setOnSingleClickListener
import umc.everyones.lck.util.extension.toCategoryPosition

@AndroidEntryPoint
class CommunityFragment : BaseFragment<FragmentCommunityBinding>(R.layout.fragment_community) {
    private val communityViewModel: CommunityViewModel by activityViewModels()

    private var _postListVPA: PostListVPA? = null
    private val postListVPA get() = _postListVPA

    // 글 작성 시 선택한 카테고리 화면으로 이동
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val category = result.data?.getStringExtra("category") ?: ""
            binding.vpCommunityPostList.currentItem = category.toCategoryPosition()
            val isWriteDone = result.data?.getBooleanExtra("isWriteDone", false) ?: false
            if (isWriteDone){
                communityViewModel.refreshCategoryPage(category)
            }
        }
    }

    override fun initObserver() {
    }

    override fun initView() {
        goToWritePost()
        initPostListVPAdapter()
        goMyPage()
    }

    private fun initPostListVPAdapter(){
        _postListVPA = PostListVPA(this)
        with(binding){
            vpCommunityPostList.adapter = postListVPA

            TabLayoutMediator(tabCommunityCategory, vpCommunityPostList) { tab, position ->
                tab.text = tabTitles[position]
            }.attach()

            tabCommunityCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    communityViewModel.refreshCategoryPage(tab?.text?.toString() ?: "잡담")
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            })
        }
    }

    // 글 작성 화면으로 이동
    private fun goToWritePost(){
        binding.fabCommunityWriteBtn.setOnSingleClickListener {
            resultLauncher.launch(WritePostActivity.newIntent(requireContext()))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _postListVPA = null
    }

    companion object {
        private val tabTitles = listOf("잡담", "응원", "FA", "거래", "질문", "후기")
    }

    private fun goMyPage(){
        binding.ivMyPage.setOnSingleClickListener {
            startActivity(MyPageActivity.newIntent(requireContext()))
        }
    }
}