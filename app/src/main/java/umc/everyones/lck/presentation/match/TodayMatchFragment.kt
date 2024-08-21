package umc.everyones.lck.presentation.match

import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentTodayMatchBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.match.adapter.TodayMatchVPA

@AndroidEntryPoint
class TodayMatchFragment : BaseFragment<FragmentTodayMatchBinding>(R.layout.fragment_today_match) {
    private val viewModel: TodayMatchViewModel by viewModels()
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

        with(binding){
            vpTodayMatchContainer.adapter = TodayMatchVPA(this@TodayMatchFragment)

            TabLayoutMediator(tabTodayMatchTitle, vpTodayMatchContainer){tab, position ->
                tab.text = tabTitles[position]
            }.attach()
        }
    }

    companion object {
        private val tabTitles = listOf("LCK Match", "LCK POG")
    }
}