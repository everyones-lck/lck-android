package umc.everyones.lck.presentation.community

import android.app.Activity
import android.content.Intent
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentCommunityBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.community.adapter.PostListVPA
import umc.everyones.lck.presentation.mypage.MyPageActivity
import umc.everyones.lck.util.extension.toCategoryPosition

@AndroidEntryPoint
class CommunityFragment : BaseFragment<FragmentCommunityBinding>(R.layout.fragment_community) {
    private val readPostViewModel: ReadPostViewModel by viewModels()
    private val writePostViewModel: WritePostViewModel by activityViewModels()
    private val navigator by lazy {
        findNavController()
    }

    private var _postListVPA: PostListVPA? = null
    private val postListVPA get() = _postListVPA

    // 글 작성 시 선택한 카테고리 화면으로 이동
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val category = result.data?.getStringExtra("category") ?: ""
            binding.vpCommunityPostList.currentItem = category.toCategoryPosition()
        }
    }

    override fun initObserver() {
    }

    override fun initView() {
        goToWritePost()
        initPostListVPAdapter()
        setupMypageButton()
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

    // 글 작성 화면으로 이동
    private fun goToWritePost(){
        binding.fabCommunityWriteBtn.setOnClickListener {
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

    private fun setupMypageButton() {
        binding.ivMyPage.setOnClickListener {

            val intent = Intent(requireContext(), MyPageActivity::class.java)
            startActivity(intent)
            requireActivity().finish() // Finish the current activity
        }
    }
}