package umc.everyones.lck.presentation.mypage

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import umc.everyones.lck.presentation.home.HomeFragment

class MyPageViewingPartyPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2 // 탭의 수

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MyPageViewingPartyGuestFragment() // 실제 Fragment로 대체
            1 -> MyPageViewingPartyHostFragment() // 실제 Fragment로 대체
            else -> throw IllegalStateException("Unexpected position: $position")
        }
    }
}
