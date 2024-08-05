package umc.everyones.lck.presentation.lck

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayout
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentAboutLckTeamBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.lck.data.PlayerData
import umc.everyones.lck.presentation.lck.util.OnPlayerItemClickListener

class AboutLckTeamFragment : BaseFragment<FragmentAboutLckTeamBinding>(R.layout.fragment_about_lck_team),
    OnPlayerItemClickListener {

    override fun initObserver() {

    }

    override fun initView() {
        val tabLayout: TabLayout = binding.tbAboutLckTeam
        val backButton: ImageView = binding.ivAboutLckTeamPre
        val nextButton: ImageView = binding.ivAboutLckTeamNext

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val fragment = when (tab?.position) {
                    0 -> AboutLckRoasterFragment()
                    1 -> AboutLckCoachesFragment()
                    2 -> AboutLckClRoasterFragment()
                    else -> throw IllegalArgumentException("Invalid tab position")
                }
                childFragmentManager.beginTransaction()
                    .replace(R.id.fcv_about_lck_team_container, fragment)
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
                .replace(R.id.fcv_about_lck_to_team_container, AboutLCKFragment())
                .addToBackStack(null)
                .commit()
        }

        nextButton.setOnClickListener {
            childFragmentManager.beginTransaction()
                .replace(R.id.fcv_about_lck_team_to_history_container, AboutLckTeamHistoryFragment())
                .addToBackStack(null)
                .commit()
        }

        tabLayout.getTabAt(0)?.select()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        val tabLayout: TabLayout = binding.tbAboutLckTeam

        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.fcv_about_lck_team_container, AboutLckRoasterFragment()) // 초기 프래그먼트
                .commit()

            tabLayout.getTabAt(0)?.select()
            updateTabTitleFont(tabLayout.getTabAt(0), true)
        }
    }

    override fun onPlayerItemClick(player: PlayerData) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fcv_about_lck_team_to_history_container, AboutLckTeamPlayerFragment())
            .addToBackStack(null)
            .commit()
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
