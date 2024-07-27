package umc.everyones.lck.presentation.lck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, AboutLckRoasterFragment()) // 초기 프래그먼트
                .commit()
        }

        // 탭 클릭 시 프래그먼트를 교체합니다.
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val fragment = when (tab?.position) {
                    0 -> AboutLckRoasterFragment()
                    1 -> AboutLckCoachesFragment()
                    2 -> AboutLckClRoasterFragment()
                    else -> throw IllegalArgumentException("Invalid tab position")
                }
                childFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit()
                // 선택된 탭의 타이틀 폰트 변경
                updateTabTitleFont(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // 선택 해제 시 특별한 동작이 필요하지 않으면 비워둡니다.
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // 탭 재선택 시 특별한 동작이 필요하지 않으면 비워둡니다.
            }
        })

        // 초기 탭 선택
        tabLayout.getTabAt(0)?.select()
    }

    private fun updateTabTitleFont(tab: TabLayout.Tab?) {
        tab?.let {
            val tabTextView = it.customView as? TextView
            tabTextView?.setTextAppearance(requireContext(), R.style.TextAppearance_LCK_Head1)
        }
    }
}
