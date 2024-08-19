package umc.everyones.lck.presentation.lck

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentAboutLckTeamPlayerBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.lck.adapter.HistoryAdapter
import umc.everyones.lck.presentation.lck.adapter.PlayerCareerAdapter
import umc.everyones.lck.presentation.lck.data.PlayerCareerData

@AndroidEntryPoint
class AboutLckTeamPlayerFragment : BaseFragment<FragmentAboutLckTeamPlayerBinding>(R.layout.fragment_about_lck_team_player) {
    private val viewModel: AboutLckPlayerCareerViewModel by viewModels()
    private lateinit var adapter: PlayerCareerAdapter
    override fun initObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.winningCareer.collect { seasonNames ->
                updateWinningCareer(seasonNames)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.history.collect { seasonTeamDetails ->
                updateHistory(seasonTeamDetails)
            }
        }

    }

    override fun initView() {
        initRecyclerView()
        initBackButton()
        val playerId = arguments?.let { AboutLckTeamPlayerFragmentArgs.fromBundle(it).playerId }

        val page = 0
        val size = 10

        playerId?.let {
            viewModel.fetchLckWinningCareer(it, page, size)
            viewModel.fetchLckHistory(it, page, size)
            viewModel.fetchLckPlayer(it)
        } ?: run {
            Log.e("AboutLckTeamHistoryFragment", "Error: teamId is null")
        }
    }
    private fun initRecyclerView() {
        val recyclerView: RecyclerView = binding.rvAboutLckTeamPlayer
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.isNestedScrollingEnabled = false

        val items = mutableListOf(
            PlayerCareerData("Winning Career", emptyList()),
            PlayerCareerData("History", emptyList())
        )

        adapter = PlayerCareerAdapter(items)
        recyclerView.adapter = adapter
    }

    private fun updateWinningCareer(seasonNames: List<String>) {
        val items = adapter.getItems().toMutableList()

        val winningCareerIndex = items.indexOfFirst { it.title == "Winning Career" }
        if ( winningCareerIndex!= -1) {
            items[ winningCareerIndex] = items [winningCareerIndex].copy(details = seasonNames)
            adapter.updateItems(items)
        }
    }

    private fun updateHistory(seasonTeamDetails: List<String>) {
        val items = adapter.getItems().toMutableList()

        val HistoryIndex = items.indexOfFirst { it.title == "History" }
        if ( HistoryIndex != -1) {
            items[ HistoryIndex] = items [HistoryIndex].copy(details = seasonTeamDetails)
            adapter.updateItems(items)
        }
    }

    private fun initBackButton() {
        val backButton = binding.ivAboutLckTeamPlayerPre
        backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
