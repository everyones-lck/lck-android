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
import umc.everyones.lck.presentation.lck.adapter.PlayerCareerAdapter
import umc.everyones.lck.presentation.lck.data.PlayerCareerData

class AboutLckTeamPlayerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about_lck_team_player, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_about_lck_team_player)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.isNestedScrollingEnabled = false

        val items = listOf(
            PlayerCareerData("Winning Career", listOf("2022 Worlds Championship", "2019 Rift Rivals", "2018 LCK Summer", "2022 Worlds Championship", "2019 Rift Rivals")),
            PlayerCareerData("History", listOf("2024 KT", "2023 KIA", "2022 DRX", "2021 Hanwha Life", "2020 DRX")),
        )

        val adapter = PlayerCareerAdapter(items)
        recyclerView.adapter = adapter

        val backButton = view.findViewById<ImageView>(R.id.iv_about_lck_team_player_pre)
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return view
    }
}
