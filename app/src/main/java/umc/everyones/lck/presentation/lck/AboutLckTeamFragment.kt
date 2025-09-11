package umc.everyones.lck.presentation.lck

import androidx.fragment.app.viewModels
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import umc.everyones.lck.R
import umc.everyones.lck.data.dto.response.about_lck.LckPlayerDetailsResponseDto
import umc.everyones.lck.databinding.FragmentAboutLckTeamBinding
import umc.everyones.lck.domain.model.about_lck.AboutLckPlayerDetailsModel
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.lck.adapter.PlayerAdapter
import umc.everyones.lck.presentation.lck.data.PlayerData
import umc.everyones.lck.presentation.lck.util.OnPlayerItemClickListener
import umc.everyones.lck.presentation.mypage.MyPageActivity
import umc.everyones.lck.util.extension.repeatOnStarted
import umc.everyones.lck.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class AboutLckTeamFragment : BaseFragment<FragmentAboutLckTeamBinding>(R.layout.fragment_about_lck_team),
    OnPlayerItemClickListener {

    private val viewModel: AboutLckTeamViewModel by viewModels()
    private val navigator by lazy { findNavController() }

    private var teamLogoUrl: String? = null
    private var teamId: Int? = null

    private var isRosterOpen = false
    private var isClRosterOpen = false
    private var isCoachOpen = false

    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            viewModel.playerDetails.collect { playerDetails ->
                playerDetails?.let { updatePlayerSections(it) }
            }
        }
    }

    override fun initView() {
        receiveSafeArgs()
        setupButtons()
        setupTeamInfo()
        setupRecyclerViews()
        setupInitialArrowIcons()
        setupSectionClickListeners()
    }

    private fun receiveSafeArgs() {
        val args = arguments?.let { AboutLckTeamFragmentArgs.fromBundle(it) }
        teamId = args?.teamId
        teamLogoUrl = args?.teamLogoUrl
        teamId?.let { viewModel.setTeamId(it) } ?: Timber.e("Team ID is null")
    }

    private fun setupButtons() {
        binding.ivAboutLckTeamPre.setOnSingleClickListener { navigator.popBackStack() }

        binding.ivAboutLckTeamNext.setOnSingleClickListener {
            teamId?.let {
                val action = AboutLckTeamFragmentDirections.actionAboutLCKTeamFragmentToAboutLckTeamHistoryFragment(
                    teamId = it,
                    teamName = arguments?.let { AboutLckTeamFragmentArgs.fromBundle(it).teamName } ?: "",
                    teamLogoUrl = teamLogoUrl ?: ""
                )
                navigator.navigate(action)
            } ?: Timber.e("teamId is null, cannot navigate")
        }
    }

    private fun setupTeamInfo() {
        val args = arguments?.let { AboutLckTeamFragmentArgs.fromBundle(it) }

        binding.tvAboutLckTeamTitle.text = args?.teamName
        binding.ivAboutLckTeamLogo.setImageResource(getTeamResource(teamId))
    }

    private fun setupRecyclerViews() {
        binding.rvAboutLckRolster.layoutManager = LinearLayoutManager(context)
        binding.rvAboutLckClRolster.layoutManager = LinearLayoutManager(context)
        binding.rvAboutLckCoach.layoutManager = LinearLayoutManager(context)
    }

    private fun setupInitialArrowIcons() {
        binding.ivAboutLckRolsterDown.setImageResource(R.drawable.ic_aboutlck_arrow_down)
        binding.ivAboutLckClRolsterDown.setImageResource(R.drawable.ic_aboutlck_arrow_down)
        binding.ivAboutLckCoachDown.setImageResource(R.drawable.ic_aboutlck_arrow_down)
    }

    private fun setupSectionClickListeners() {
        binding.ivAboutLckRolsterDown.setOnClickListener {
            toggleSection(
                LckPlayerDetailsResponseDto.PlayerRole.LCK_ROSTER,
                binding.rvAboutLckRolster,
                binding.ivAboutLckRolsterDown,
                isRosterOpen
            ) { isRosterOpen = it }
        }

        binding.ivAboutLckClRolsterDown.setOnClickListener {
            toggleSection(
                LckPlayerDetailsResponseDto.PlayerRole.LCK_CL_ROSTER,
                binding.rvAboutLckClRolster,
                binding.ivAboutLckClRolsterDown,
                isClRosterOpen
            ) { isClRosterOpen = it }
        }

        binding.ivAboutLckCoachDown.setOnClickListener {
            toggleSection(
                LckPlayerDetailsResponseDto.PlayerRole.COACH,
                binding.rvAboutLckCoach,
                binding.ivAboutLckCoachDown,
                isCoachOpen
            ){ isCoachOpen = it}
        }
    }

    private fun toggleSection(
        role: LckPlayerDetailsResponseDto.PlayerRole,
        recyclerView: View,
        arrowImageView: ImageView,
        isOpenFlag: Boolean,
        onFlagToggle: (Boolean) -> Unit
    ) {
        if (isOpenFlag) {
            recyclerView.visibility = View.GONE
            arrowImageView.setImageResource(R.drawable.ic_aboutlck_arrow_down)
            onFlagToggle(false)
        } else {
            recyclerView.visibility = View.VISIBLE
            arrowImageView.setImageResource(R.drawable.ic_aboutlck_arrow_up)
            onFlagToggle(true)

            teamId?.let {
                viewModel.fetchLckPlayerDetails(it, "2025 LCK", role)
            }
        }
    }
    private fun updatePlayerSections(data: AboutLckPlayerDetailsModel) {
        val teamId = viewModel.teamId.value ?: return
        //val roleType = data.playerDetails.firstOrNull()?.playerRole
        val roleType = data.playerDetails.firstOrNull()?.playerRole ?: return

        val playerList = data.playerDetails
            .sortedByDescending { it.isCaptain }
            .map {
                val displayRole = when (roleType) {
                    AboutLckPlayerDetailsModel.PlayerRole.COACH ->
                        if (it.isCaptain) "Main" else "Coach"
                    else ->
                        if (it.isCaptain) "Leader" else "Member"
                }

                PlayerData(
                    playerId = it.playerId,
                    playerImg = it.profileImageUrl,
                    teamColor = getTeamResource(teamId),
                    name = it.playerName,
                    teamLogo = 0,
                    isCaptain = it.isCaptain,
                    position = it.position,
                    displayRole = displayRole
                )
            }

        val clickListener: OnPlayerItemClickListener? =
            if (roleType == AboutLckPlayerDetailsModel.PlayerRole.LCK_ROSTER) this else null

        val adapter = PlayerAdapter(playerList, clickListener)
        when (roleType) {
            AboutLckPlayerDetailsModel.PlayerRole.LCK_ROSTER -> binding.rvAboutLckRolster.adapter = adapter
            AboutLckPlayerDetailsModel.PlayerRole.LCK_CL_ROSTER -> binding.rvAboutLckClRolster.adapter = adapter
            AboutLckPlayerDetailsModel.PlayerRole.COACH -> binding.rvAboutLckCoach.adapter = adapter
            else -> Timber.e("Unknown role or empty list")
        }
        Timber.d("playerRole = ${data.playerDetails.firstOrNull()?.playerRole}")
    }

    override fun onPlayerItemClick(player: PlayerData) {
        val action = AboutLckTeamFragmentDirections.actionAboutLCKTeamFragmentToAboutLckTeamPlayerFragment(
            player.playerId,
            teamName = arguments?.let { AboutLckTeamFragmentArgs.fromBundle(it).teamName } ?: "",
            teamLogoUrl ?: " "
        )
        navigator.navigate(action)
    }

    private fun getTeamResource(teamId: Int?): Int {
        return when (teamId) {
            2 -> R.drawable.img_aboutlck_uniform_geng
            3 -> R.drawable.img_aboutlck_uniform_hanwha
            4 -> R.drawable.img_aboutlck_uniform_dk
            5 -> R.drawable.img_aboutlck_uniform_t1
            6 -> R.drawable.img_aboutlck_uniform_kt
            7 -> R.drawable.img_aboutlck_uniform_dnf
            8 -> R.drawable.img_aboutlck_uniform_bnk
            9 -> R.drawable.img_aboutlck_uniform_ns
            10 -> R.drawable.img_aboutlck_uniform_drx
            11 -> R.drawable.img_aboutlck_uniform_ok
            else -> R.drawable.img_aboutlck_uniform_geng
        }
    }
}