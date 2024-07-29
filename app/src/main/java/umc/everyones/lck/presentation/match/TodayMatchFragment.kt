package umc.everyones.lck.presentation.match

import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentTodayMatchBinding
import umc.everyones.lck.presentation.base.BaseFragment

@AndroidEntryPoint
class TodayMatchFragment : BaseFragment<FragmentTodayMatchBinding>(R.layout.fragment_today_match) {
    override fun initObserver() {

    }

    override fun initView() {
        setupTabs()
    }

    private fun setupTabs() {
        val tabLayout = binding.tabTodayMatchTitle

        // 탭 추가
        tabLayout.addTab(tabLayout.newTab().setText("LCK Match"))
        tabLayout.addTab(tabLayout.newTab().setText("LCK POG"))

        // 초기 화면 설정
        replaceFragment(TodayMatchLckMatchFragment())

        // 탭 선택 리스너 설정
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val fragment: Fragment = when (tab.position) {
                    0 -> TodayMatchLckMatchFragment()
                    1 -> TodayMatchLckPogFragment()
                    else -> throw IllegalStateException("Unexpected tab position")
                }
                replaceFragment(fragment)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.commit {
            replace(R.id.fcv_today_match_fragment, fragment)
        }
    }
}