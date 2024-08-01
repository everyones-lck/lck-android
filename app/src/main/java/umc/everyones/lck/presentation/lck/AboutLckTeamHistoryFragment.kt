package umc.everyones.lck.presentation.lck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.R

class AboutLckTeamHistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about_lck_team_history, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_about_lck_history)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.isNestedScrollingEnabled = false

        val items = listOf(
            HistoryData("Winning History", listOf("2018 LCK Summer", "2017 KeSPA Cup", "2014 LCK Summer", "2014 IEM", "2013 MLG Winter","2013 MLG Winter","2013 MLG Winter","2013 MLG Winter","2013 MLG Winter","2013 MLG Winter","2013 MLG Winter")),
            HistoryData("Recent Performance", listOf("2024 LCK Spring - 5th", "2023 Worlds Championship - Quarter final", "2023 LCK Summer - 3rd", "2023 LCK Spring - 3rd", "2022 LCK Summer - 5th")),
            HistoryData("History Of Roaster", listOf("2024 PerfectT | Pyosik | Bdd | Deft | BeryL", "2023 Kiin | Cuzz | Bdd | Aiming | Lehands", "2022 Rascal | Cuzz | Aria | Aiming | Life", "2021 Doran | Blank | Ucal | Noah | Zzus", "2020 Smeb | bonO | Kuro | Aiming | TusiN"))
        )

        val adapter = HistoryAdapter(items)
        recyclerView.adapter = adapter

        val backButton = view.findViewById<ImageView>(R.id.iv_about_lck_team_history_pre)
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return view
    }
}
