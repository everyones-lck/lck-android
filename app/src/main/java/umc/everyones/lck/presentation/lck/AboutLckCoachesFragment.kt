package umc.everyones.lck.presentation.lck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.R
import umc.everyones.lck.presentation.lck.adapter.PlayerAdapter
import umc.everyones.lck.presentation.lck.data.PlayerData
import umc.everyones.lck.presentation.lck.util.OnPlayerItemClickListener

class AboutLckCoachesFragment : Fragment() {

    private lateinit var listener: OnPlayerItemClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listener = parentFragment as OnPlayerItemClickListener
        return inflater.inflate(R.layout.fragment_about_lck_coaches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_about_lck_coaches)
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = PlayerAdapter(getPlayers(), listener)
    }

    private fun getPlayers(): List<PlayerData> {
        return listOf(
            PlayerData(R.drawable.img_about_lck_player, R.drawable.img_about_lck_player_team_color_t1, "Faker", R.drawable.img_about_lck_player_team_logo_t1, R.drawable.img_about_lck_player_position),
            PlayerData(R.drawable.img_about_lck_player, R.drawable.img_about_lck_player_team_color_t1, "Faker", R.drawable.img_about_lck_player_team_logo_t1, R.drawable.img_about_lck_player_position),
            PlayerData(R.drawable.img_about_lck_player, R.drawable.img_about_lck_player_team_color_t1, "Faker", R.drawable.img_about_lck_player_team_logo_t1, R.drawable.img_about_lck_player_position)
        )
    }
}
