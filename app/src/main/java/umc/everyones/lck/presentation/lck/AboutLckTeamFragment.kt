package umc.everyones.lck.presentation.lck

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentAboutLckTeamBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.lck.adapter.TeamVPAdapter
import umc.everyones.lck.presentation.lck.data.PlayerData
import umc.everyones.lck.presentation.lck.util.OnPlayerItemClickListener
import com.google.android.material.tabs.TabLayoutMediator
import umc.everyones.lck.data.dto.response.about_lck.LckPlayerDetailsResponseDto
import umc.everyones.lck.domain.model.about_lck.AboutLckPlayerDetailsModel

@AndroidEntryPoint
class AboutLckTeamFragment : BaseFragment<FragmentAboutLckTeamBinding>(R.layout.fragment_about_lck_team),
    OnPlayerItemClickListener {

    private val viewModel: AboutLckTeamViewModel by viewModels()
    private val navigator by lazy { findNavController() }
    private lateinit var pagerAdapter: TeamVPAdapter

    override fun initObserver() {

    }

    override fun initView() {
        val tabLayout: TabLayout = binding.tbAboutLckTeam
        val viewPager: ViewPager2 = binding.vpAboutLckTeam
        val backButton: ImageView = binding.ivAboutLckTeamPre
        val nextButton: ImageView = binding.ivAboutLckTeamNext

        pagerAdapter = TeamVPAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "LCK Roaster"
                1 -> "Coaches"
                2 -> "LCK CL Roaster"
                else -> throw IllegalArgumentException("Invalid tab position")
            }
        }.attach()


        backButton.setOnClickListener {
            navigator.popBackStack()
        }

        nextButton.setOnClickListener {
            navigator.navigate(R.id.action_aboutLCKTeamFragment_to_aboutLckTeamHistoryFragment)
        }
        // 예시 값으로 API 호출
        val teamId = 1
        val seasonName = "2024 Spring"
        val player_role = AboutLckPlayerDetailsModel.PlayerRole.LCK_ROSTER

        // ViewModel을 통해 API 호출
        viewModel.fetchLckPlayerDetails(teamId, seasonName, player_role)
    }

    override fun onPlayerItemClick(player: PlayerData) {
        navigator.navigate(R.id.action_aboutLCKTeamFragment_to_aboutLckTeamPlayerFragment)
    }

}
