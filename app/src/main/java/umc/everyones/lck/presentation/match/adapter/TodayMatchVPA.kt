package umc.everyones.lck.presentation.match.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import umc.everyones.lck.presentation.match.TodayMatchLckMatchFragment
import umc.everyones.lck.presentation.match.TodayMatchLckPogFragment

class TodayMatchVPA(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    // API 연결하면서 내부 Fragment 바꿀 예정
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TodayMatchLckMatchFragment()
            1 -> TodayMatchLckPogFragment()
            else -> throw IllegalStateException("Invalid position")
        }
    }
}