package umc.everyones.lck.presentation.community.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import umc.everyones.lck.presentation.community.list.FreeAgentListFragment
import umc.everyones.lck.presentation.community.list.PostListFragment
import umc.everyones.lck.presentation.community.list.QuestionListFragment
import umc.everyones.lck.presentation.community.list.ReviewListFragment
import umc.everyones.lck.presentation.community.list.SmallTalkListFragment
import umc.everyones.lck.presentation.community.list.SupportListFragment
import umc.everyones.lck.presentation.community.list.TradeListFragment

class PostListVPA(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 6
    }

    // API 연결하면서 내부 Fragment 바꿀 예정
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SmallTalkListFragment()
            1 -> SupportListFragment()
            2 -> FreeAgentListFragment()
            3 -> TradeListFragment()
            4 -> QuestionListFragment()
            5 -> ReviewListFragment()
            else -> throw IllegalStateException("Invalid position")
        }
    }
}