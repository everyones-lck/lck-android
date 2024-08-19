package umc.everyones.lck.presentation.lck

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
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

    private var teamLogoUrl: String? = null

    override fun initObserver() {

    }

    override fun initView() {
        val tabLayout: TabLayout = binding.tbAboutLckTeam
        val viewPager: ViewPager2 = binding.vpAboutLckTeam
        val backButton: ImageView = binding.ivAboutLckTeamPre
        val nextButton: ImageView = binding.ivAboutLckTeamNext
        val teamTitleTextView: TextView = binding.tvAboutLckTeamTitle
        val teamLogoImageView: ImageView = binding.ivAboutLckTeamLogo

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


        // Safe Args로 전달된 teamId 수신
        val teamId = arguments?.let { AboutLckTeamFragmentArgs.fromBundle(it).teamId }
        val teamName = arguments?.let { AboutLckTeamFragmentArgs.fromBundle(it).teamName }
        teamLogoUrl = arguments?.let { AboutLckTeamFragmentArgs.fromBundle(it).teamLogoUrl }

        Log.d("AboutLckTeamFragment", "teamId: $teamId, teamName: $teamName, teamLogoUrl: $teamLogoUrl")

        teamId?.let {
            viewModel.setTeamId(it)
            Log.d("AboutLckTeamFragment", "Team ID set in ViewModel: $it")
        }

        teamName?.let {
            teamTitleTextView.text = it
        }

        teamLogoUrl?.let {
            Glide.with(this)
                .load(it)
                .into(teamLogoImageView)
        }

        nextButton.setOnClickListener {
            teamId?.let { id ->
                val action = AboutLckTeamFragmentDirections
                    .actionAboutLCKTeamFragmentToAboutLckTeamHistoryFragment(
                        teamId = id,
                        teamName = teamName ?: "",
                        teamLogoUrl = teamLogoUrl ?: ""
                    )
                navigator.navigate(action)
            } ?: Log.e("AboutLckTeamFragment", "teamId is null, cannot navigate")
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                teamId?.let {
                    val playerRole = when (position) {
                        0 -> LckPlayerDetailsResponseDto.PlayerRole.LCK_ROSTER
                        1 -> LckPlayerDetailsResponseDto.PlayerRole.COACH
                        2 -> LckPlayerDetailsResponseDto.PlayerRole.LCK_CL_ROSTER
                        else -> throw IllegalArgumentException("Invalid tab position")
                    }
                    Log.d("AboutLckTeamFragment", "Tab selected: $position, PlayerRole: $playerRole, Team ID: $it")
                    viewModel.fetchLckPlayerDetails(it, "2024 Summer", playerRole)
                } ?: Log.e("AboutLckTeamFragment", "teamId is null during onPageSelected")
            }
        })
    }

    override fun onPlayerItemClick(player: PlayerData) {
        val action = AboutLckTeamFragmentDirections.actionAboutLCKTeamFragmentToAboutLckTeamPlayerFragment(player.playerId,teamLogoUrl?:" ")
        navigator.navigate(action)
    }
}
