package umc.everyones.lck.presentation.lck

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentAboutLckTeamPlayerBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.lck.adapter.PlayerCareerAdapter
import umc.everyones.lck.presentation.lck.data.PlayerCareerData

class AboutLckTeamPlayerFragment : BaseFragment<FragmentAboutLckTeamPlayerBinding>(R.layout.fragment_about_lck_team_player) {
    override fun initObserver() {

    }

    override fun initView() {
        initRecyclerView()
        initBackButton()
    }

    private fun initRecyclerView() {
        val recyclerView: RecyclerView = binding.rvAboutLckTeamPlayer
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.isNestedScrollingEnabled = false

        val items = listOf(
            PlayerCareerData("Winning Career", listOf("2022 Worlds Championship", "2019 Rift Rivals", "2018 LCK Summer", "2022 Worlds Championship", "2019 Rift Rivals")),
            PlayerCareerData("History", listOf("2024 KT", "2023 KIA", "2022 DRX", "2021 Hanwha Life", "2020 DRX")),
        )

        val adapter = PlayerCareerAdapter(items)
        recyclerView.adapter = adapter
    }
    private fun initBackButton() {
        val backButton = binding.ivAboutLckTeamPlayerPre
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}
