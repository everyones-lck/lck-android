package umc.everyones.lck.presentation.community.adapter

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import umc.everyones.lck.presentation.community.PostListFragment

class PostListVPA(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> PostListFragment()
            1 -> PostListFragment()
            2 -> PostListFragment()
            3 -> PostListFragment()
            4 -> PostListFragment()
            5 -> PostListFragment()
            else -> {PostListFragment()}
        }
    }
}