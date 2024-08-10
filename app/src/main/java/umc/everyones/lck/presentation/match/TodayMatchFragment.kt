package umc.everyones.lck.presentation.match

import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentTodayMatchBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.match.adapter.TodayMatchVPA
import umc.everyones.lck.presentation.mypage.MyPageActivity
import umc.everyones.lck.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class TodayMatchFragment : BaseFragment<FragmentTodayMatchBinding>(R.layout.fragment_today_match) {
    override fun initObserver() {

    }

    override fun initView() {
        setupTabs()
        goMyPage()
    }

    private fun setupTabs() {
        val tabLayout = binding.tabTodayMatchTitle

        // 탭 추가
        tabLayout.addTab(tabLayout.newTab().setText("LCK Match"))
        tabLayout.addTab(tabLayout.newTab().setText("LCK POG"))

        with(binding){
            vpTodayMatchContainer.adapter = TodayMatchVPA(this@TodayMatchFragment)

            TabLayoutMediator(tabTodayMatchTitle, vpTodayMatchContainer){tab, position ->
                tab.text = tabTitles[position]
            }.attach()
        }
    }

    private fun goMyPage(){
        binding.ivMyPage.setOnSingleClickListener {
            startActivity(MyPageActivity.newIntent(requireContext()))
        }
    }

    companion object {
        private val tabTitles = listOf("LCK Match", "LCK POG")
    }
}