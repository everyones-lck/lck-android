package umc.everyones.lck.presentation.lck.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import umc.everyones.lck.presentation.lck.AboutLckClRoasterFragment
import umc.everyones.lck.presentation.lck.AboutLckCoachesFragment
import umc.everyones.lck.presentation.lck.AboutLckRoasterFragment

class TeamVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AboutLckRoasterFragment()
            1 -> AboutLckCoachesFragment()
            2 -> AboutLckClRoasterFragment()
            else -> throw IllegalArgumentException("Invalid tab position")
        }
    }
}