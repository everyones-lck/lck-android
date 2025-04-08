package umc.everyones.lck.presentation.lck

import android.view.View
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentAboutLckTeamPlayerBinding
import umc.everyones.lck.domain.model.about_lck.AboutLckPlayerModel
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.lck.adapter.HistoryAdapter
import umc.everyones.lck.presentation.lck.adapter.PlayerCareerAdapter
import umc.everyones.lck.presentation.lck.data.PlayerCareerData
import umc.everyones.lck.presentation.mypage.MyPageActivity
import umc.everyones.lck.util.extension.repeatOnStarted
import umc.everyones.lck.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class AboutLckTeamPlayerFragment : BaseFragment<FragmentAboutLckTeamPlayerBinding>(R.layout.fragment_about_lck_team_player) {
    private val viewModel: AboutLckPlayerCareerViewModel by viewModels()
    private lateinit var adapter: PlayerCareerAdapter

    private var isWinningCareerOpen = false
    private var isHistoryOpen = false

    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            viewModel.winningCareer.collect { seasonNames ->
                updateWinningCareer(seasonNames)
            }
        }
        viewLifecycleOwner.repeatOnStarted {
            viewModel.history.collect { seasonTeamDetails ->
                updateHistory(seasonTeamDetails)
            }
        }
        viewLifecycleOwner.repeatOnStarted {
            viewModel.player.collect { player ->
                if (player != null) {
                    updatePlayerUI(player)
                } else {
                    Timber.e("Player data is null")
                }
            }
        }
    }

    override fun initView() {
        setupTeamInfo()
        initBackButton()
        setupInitialArrowIcons()
        setupSectionClickListeners()
        val playerId = arguments?.let { AboutLckTeamPlayerFragmentArgs.fromBundle(it).playerId }

        val page = 0
        val size = 10

        playerId?.let {
            viewModel.fetchLckWinningCareer(it, page, size)
            viewModel.fetchLckHistory(it, page, size)
            viewModel.fetchLckPlayer(it)
        } ?: run {
            Timber.e("Error: teamId is null")
        }
    }

    private fun setupTeamInfo() {
        val args = arguments?.let { AboutLckTeamPlayerFragmentArgs.fromBundle(it) }
        binding.tvAboutLckTeamPlayerTitle.text = args?.teamName
    }

    private fun setupInitialArrowIcons() {
        binding.ivAboutLckTeamPlayerWinningCareerDown.setImageResource(R.drawable.ic_aboutlck_arrow_down)
        binding.ivAboutLckTeamPlayerHistoryDown.setImageResource(R.drawable.ic_aboutlck_arrow_down)
    }

    private fun setupSectionClickListeners() {
        binding.ivAboutLckTeamPlayerWinningCareerDown.setOnClickListener {
            toggleSection(
                binding.rvAboutLckTeamPlayerWinningCareer,
                binding.ivAboutLckTeamPlayerWinningCareerDown,
                isWinningCareerOpen,
                {isWinningCareerOpen = it },
                viewModel.winningCareer.value,
                "Winning History"
            )
        }

        binding.ivAboutLckTeamPlayerHistoryDown.setOnClickListener {
            toggleSection(
                binding.rvAboutLckTeamPlayerHistory,
                binding.ivAboutLckTeamPlayerHistoryDown,
                isHistoryOpen ,
                {isHistoryOpen = it },
                viewModel.history.value,
                "Recent Performance"
            )
        }
    }

    private fun toggleSection(
        recyclerView: RecyclerView,
        arrowImageView: ImageView,
        isOpenFlag: Boolean,
        onFlagToggle: (Boolean) -> Unit,
        detailList: List<String>,
        title: String
    ) {
        if (isOpenFlag) {
            recyclerView.visibility = View.GONE
            arrowImageView.setImageResource(R.drawable.ic_aboutlck_arrow_down)
            onFlagToggle(false)
        } else {
            recyclerView.visibility = View.VISIBLE
            arrowImageView.setImageResource(R.drawable.ic_aboutlck_arrow_up)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = HistoryAdapter(detailList, title)
            onFlagToggle(true)
        }
    }

    private fun updateWinningCareer(seasonNameList: List<String>) {
        binding.rvAboutLckTeamPlayerWinningCareer.adapter = PlayerCareerAdapter().apply {
            setData("Winning Career", seasonNameList)
        }
    }

    private fun updateHistory(seasonNameList: List<String>) {
        binding.rvAboutLckTeamPlayerHistory.adapter = PlayerCareerAdapter().apply {
            setData("History", seasonNameList)
        }
    }

    private fun updatePlayerUI(player: AboutLckPlayerModel) {

        binding.tvAboutLckTeamPlayerNickName.text = player.nickName
        binding.tvAboutLckTeamPlayerBirth.text = player.birthDate.substring(0, 10).replace("-", ".")
        val positionText = when (player.position.toString()) {
            "JUNGLE" -> "JGL"
            "SUPPORT" -> "SPT"
            else -> player.position.toString()
        }
        binding.tvAboutLckTeamPlayerPosition.text = positionText
        binding.tvAboutLckTeamPlayerRealName.text = player.realName

        val positionIcon = when (player.position.name) {
            "TOP" -> R.drawable.ic_top
            "JUNGLE" -> R.drawable.ic_jgl
            "MID" -> R.drawable.ic_mid
            "BOT" -> R.drawable.ic_bot
            "SUPPORT" -> R.drawable.ic_support
            "COACH" -> R.drawable.ic_coach
            else -> R.drawable.ic_top
        }
        binding.ivAboutLckTeamPlayerPosition.setImageResource(positionIcon)
    }

    private fun initBackButton() {
        val backButton = binding.ivAboutLckTeamPlayerPre
        backButton.setOnSingleClickListener {
            findNavController().popBackStack()
        }
    }
}
