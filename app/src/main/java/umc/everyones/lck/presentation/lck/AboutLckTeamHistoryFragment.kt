package umc.everyones.lck.presentation.lck

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentAboutLckTeamHistoryBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.lck.adapter.HistoryAdapter
import umc.everyones.lck.presentation.lck.data.HistoryData

@AndroidEntryPoint
class AboutLckTeamHistoryFragment : BaseFragment<FragmentAboutLckTeamHistoryBinding>(R.layout.fragment_about_lck_team_history) {

    override fun initObserver() {

    }

    override fun initView() {
        initRecyclerView()
        initBackButton()
    }

    private fun initRecyclerView() {
        val recyclerView: RecyclerView = binding.rvAboutLckHistory
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.isNestedScrollingEnabled = false

        val items = listOf(
            HistoryData("Winning History", listOf("2018 LCK Summer", "2017 KeSPA Cup", "2014 LCK Summer", "2014 IEM", "2013 MLG Winter", "2013 MLG Winter", "2013 MLG Winter", "2013 MLG Winter", "2013 MLG Winter", "2013 MLG Winter", "2013 MLG Winter")),
            HistoryData("Recent Performance", listOf("2024 LCK Spring - 5th", "2023 Worlds Championship - Quarter final", "2023 LCK Summer - 3rd", "2023 LCK Spring - 3rd", "2022 LCK Summer - 5th")),
            HistoryData("History Of Roaster", listOf("2024 PerfectT | Pyosik | Bdd | Deft | BeryL", "2023 Kiin | Cuzz | Bdd | Aiming | Lehands", "2022 Rascal | Cuzz | Aria | Aiming | Life", "2021 Doran | Blank | Ucal | Noah | Zzus", "2020 Smeb | bonO | Kuro | Aiming | TusiN"))
        )

        val adapter = HistoryAdapter(items)
        recyclerView.adapter = adapter
    }

    private fun initBackButton() {
        val backButton = binding.ivAboutLckTeamHistoryPre
        backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
