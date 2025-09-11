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
import umc.everyones.lck.data.dto.response.about_lck.LckPlayerDetailsResponseDto
import umc.everyones.lck.databinding.FragmentAboutLckTeamHistoryBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.lck.adapter.HistoryAdapter
import umc.everyones.lck.presentation.lck.data.HistoryData
import umc.everyones.lck.presentation.mypage.MyPageActivity
import umc.everyones.lck.util.extension.repeatOnStarted
import umc.everyones.lck.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class AboutLckTeamHistoryFragment : BaseFragment<FragmentAboutLckTeamHistoryBinding>(R.layout.fragment_about_lck_team_history) {

    private val viewModel: AboutLckTeamHistoryViewModel by viewModels()
    private var teamId: Int? = null

    private var isWinningHistoryOpen = false
    private var isRecentPerformanceOpen = false
    private var isHistoryOfRoasterOpen = false

    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            viewModel.winningHistory.collect { seasonNameList ->
                updateWinningHistory(seasonNameList)
            }
        }
        viewLifecycleOwner.repeatOnStarted {
            viewModel.recentPerformances.collect { recentPerformances ->
                updateRecentPerformances(recentPerformances)
            }
        }
        viewLifecycleOwner.repeatOnStarted {
            viewModel.historyOfRoaster.collect { historyOfRoaster ->
                updateHistoryOfRoaster(historyOfRoaster)
            }
        }

    }

    override fun initView() {
        setupInitialArrowIcons()
        setupSectionClickListeners()
        initBackButton()
        fetchTeamData()
        setupTeamInfo()
    }

    private fun setupInitialArrowIcons() {
        binding.ivAboutLckTeamWinningHistoryDown.setImageResource(R.drawable.ic_aboutlck_arrow_down)
        binding.ivAboutLckTeamRecentPerformanceDown.setImageResource(R.drawable.ic_aboutlck_arrow_down)
        binding.ivAboutLckTeamHistoryOfRosterDown.setImageResource(R.drawable.ic_aboutlck_arrow_down)
    }

    private fun setupSectionClickListeners() {
        binding.ivAboutLckTeamWinningHistoryDown.setOnClickListener {
            toggleSection(
                binding.rvAboutLckTeamWinningHistory,
                binding.ivAboutLckTeamWinningHistoryDown,
                isWinningHistoryOpen,
                {isWinningHistoryOpen = it },
                viewModel.winningHistory.value,
                "Winning History"
            )
        }

        binding.ivAboutLckTeamRecentPerformanceDown.setOnClickListener {
            toggleSection(
                binding.rvAboutLckTeamRecentPerformance,
                binding.ivAboutLckTeamRecentPerformanceDown,
                isRecentPerformanceOpen ,
                {isRecentPerformanceOpen = it },
                viewModel.recentPerformances.value,
                "Recent Performance"
            )
        }

        binding.ivAboutLckTeamHistoryOfRosterDown.setOnClickListener {
            toggleSection(
                binding.rvAboutLckTeamHistoryOfRoster,
                binding.ivAboutLckTeamHistoryOfRosterDown,
                isHistoryOfRoasterOpen,
                {isHistoryOfRoasterOpen = it},
                viewModel.historyOfRoaster.value,
                "History Of Roaster"
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

    private fun setupTeamInfo() {
        val args = arguments?.let { AboutLckTeamFragmentArgs.fromBundle(it) }
        binding.tvAboutLckTeamHistoryTitle.text = args?.teamName
        binding.ivAboutLckTeamHistoryUniform.setImageResource(getTeamResource(teamId))
    }

    private fun fetchTeamData() {
        teamId = arguments?.let { AboutLckTeamHistoryFragmentArgs.fromBundle(it).teamId  }
        val page = 0
        val size = 10

        teamId?.let {
            viewModel.fetchLckWinningHistory(it, page, size)
            viewModel.fetchLckRecentPerformances(it, page, size)
            viewModel.fetchLckHistoryOfRoaster(it, page, size)
        } ?: run {
            Timber.e("Error: teamId is null")
        }
    }

    private fun updateWinningHistory(seasonNameList: List<String>) {
        binding.rvAboutLckTeamWinningHistory.adapter = HistoryAdapter().apply {
            setData("Winning History", seasonNameList)
        }
    }

    private fun updateRecentPerformances(recentPerformances: List<String>) {
        binding.rvAboutLckTeamRecentPerformance.adapter = HistoryAdapter().apply {
            setData("Recent Performance", recentPerformances)
        }
    }

    private fun updateHistoryOfRoaster(historyOfRoaster: List<String>) {
        binding.rvAboutLckTeamHistoryOfRoster.adapter = HistoryAdapter().apply {
            setData("History Of Roaster", historyOfRoaster)
        }
    }

    private fun initBackButton() {
        val backButton = binding.ivAboutLckTeamHistoryPre
        backButton.setOnSingleClickListener{
            findNavController().popBackStack()
        }
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
