package umc.everyones.lck.presentation.mypage.community

import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentMypageCommunityBinding
import umc.everyones.lck.presentation.base.BaseFragment

@AndroidEntryPoint
class MyPageCommunityFragment : BaseFragment<FragmentMypageCommunityBinding>(R.layout.fragment_mypage_community) {

    override fun initObserver() {

    }
    override fun initView() {
        // 뒤로가기 버튼 클릭 시 동작
        binding.ivMypageCommunityBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        // ViewPager2와 TabLayout 초기화
        val pagerAdapter = MyPageCommunityPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabMypageCommunityPostComment, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "My Post"
                1 -> "My Comment"
                else -> throw IllegalStateException("Invalid position: $position")
            }
        }.attach()
    }
}
