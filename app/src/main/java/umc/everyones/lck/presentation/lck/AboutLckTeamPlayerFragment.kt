package umc.everyones.lck.presentation.lck

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentAboutLckTeamPlayerBinding
import umc.everyones.lck.domain.model.about_lck.AboutLckPlayerModel
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.lck.adapter.HistoryAdapter
import umc.everyones.lck.presentation.lck.adapter.PlayerCareerAdapter
import umc.everyones.lck.presentation.lck.data.PlayerCareerData
import umc.everyones.lck.presentation.mypage.MyPageActivity
import umc.everyones.lck.util.extension.setOnSingleClickListener

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
        lifecycleScope.launchWhenStarted {
            viewModel.player.collect { result ->
                result?.onSuccess { player ->
                    updatePlayerUI(player)
                }?.onFailure {
                    Log.e("AboutLckTeamPlayerFragment", "Failed to fetch player data: ${it.message}")
                }
            }
        }
    }

    override fun initView() {
        initRecyclerView()
        initBackButton()
        goMyPage()
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

    private fun updatePlayerUI(player: AboutLckPlayerModel) {

        val teamLogoUrl = arguments?.let { AboutLckTeamPlayerFragmentArgs.fromBundle(it).teamLogoUrl }
        teamLogoUrl?.let {
            Glide.with(this)
                .load(it)
                .into(binding.ivAboutLckTeamPlayerLogo) // 적절한 ImageView에 teamLogoUrl 로드
        }

        Glide.with(this)
            .load(player.playerProfileImageUrl)
            .into(binding.ivAboutLckTeamPlayerImg)

        binding.tvAboutLckTeamPlayerNickName.text = player.nickName
        binding.tvAboutLckTeamPlayerBirth.text = player.birthDate.substring(0, 10).replace("-", ".")
        binding.tvAboutLckTeamPlayerPosition.text = player.position.toString()
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
        backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun goMyPage(){
        binding.ivMyPage.setOnSingleClickListener {
            startActivity(MyPageActivity.newIntent(requireContext()))
        }
    }
}
