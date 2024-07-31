package umc.everyones.lck.presentation.lck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import umc.everyones.lck.R

class AboutLckTeamFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about_lck_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout: TabLayout = view.findViewById(R.id.tb_about_lck_team_t1)
        val backButton: ImageView = view.findViewById(R.id.iv_about_lck_team_t1_pre)

        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.fragment_about_lck_team_container, AboutLckRoasterFragment()) // 초기 프래그먼트
                .commit()

            tabLayout.getTabAt(0)?.select()
            updateTabTitleFont(tabLayout.getTabAt(0), true)
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val fragment = when (tab?.position) {
                    0 -> AboutLckRoasterFragment()
                    1 -> AboutLckCoachesFragment()
                    2 -> AboutLckClRoasterFragment()
                    else -> throw IllegalArgumentException("Invalid tab position")
                }
                childFragmentManager.beginTransaction()
                    .replace(R.id.fragment_about_lck_team_container, fragment)
                    .commit()
                updateTabTitleFont(tab, true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                updateTabTitleFont(tab, false)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // 탭 재선택 시 동작
            }
        })

        backButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_about_lck_container, AboutLCKFragment())
                .addToBackStack(null)
                .commit()
        }

        tabLayout.getTabAt(0)?.select()
    }

    private fun updateTabTitleFont(tab: TabLayout.Tab?, isSelected: Boolean) {
        tab?.let {
            val tabTextView = (it.view as ViewGroup).getChildAt(1) as? TextView
            if (isSelected) {
                tabTextView?.setTextAppearance(R.style.TextAppearance_LCK_Head1)
                tabTextView?.setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
            } else {
                tabTextView?.setTextAppearance(R.style.TextAppearance_LCK_Head2)
                tabTextView?.setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
            }
        }
    }
}
