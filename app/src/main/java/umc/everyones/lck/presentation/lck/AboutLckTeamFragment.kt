package umc.everyones.lck.presentation.lck

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentAboutLckTeamBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.lck.data.PlayerData
import umc.everyones.lck.presentation.lck.util.OnPlayerItemClickListener

@AndroidEntryPoint
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
                val navHostFragment = childFragmentManager.findFragmentById(R.id.fcv_about_lck_team) as NavHostFragment
                val navController = navHostFragment.navController
                when (tab?.position) {
                    0 -> navController.navigate(R.id.aboutLckRoasterFragment)
                    1 -> navController.navigate(R.id.aboutLckCoachesFragment)
                    2 -> navController.navigate(R.id.aboutLckClRoasterFragment)
                    else -> throw IllegalArgumentException("Invalid tab position")
                }
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
            findNavController().popBackStack()
        }

        nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_aboutLCKTeamFragment_to_aboutLckTeamHistoryFragment)
        }

        tabLayout.getTabAt(0)?.select()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        val tabLayout: TabLayout = binding.tbAboutLckTeam

        if (savedInstanceState == null) {
            val navHostFragment = childFragmentManager.findFragmentById(R.id.fcv_about_lck_team) as NavHostFragment
            navHostFragment.navController.navigate(R.id.aboutLckRoasterFragment)

            tabLayout.getTabAt(0)?.select()
            updateTabTitleFont(tabLayout.getTabAt(0), true)
        }
    }

    override fun onPlayerItemClick(player: PlayerData) {
        findNavController().navigate(R.id.action_aboutLCKTeamFragment_to_aboutLckTeamPlayerFragment)
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
