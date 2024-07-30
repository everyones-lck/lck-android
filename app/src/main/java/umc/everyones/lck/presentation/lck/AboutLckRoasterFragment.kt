package umc.everyones.lck.presentation.lck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.R

class AboutLckRoasterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about_lck_roaster, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_about_lck_roaster)
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = PlayerAdapter(getPlayers())
    }

    private fun getPlayers(): List<PlayerData> {
        return listOf(
            PlayerData(R.drawable.img_about_lck_player, R.drawable.img_about_lck_player_team_color_t1, "Faker", R.drawable.img_about_lck_player_team_logo_t1, R.drawable.img_about_lck_player_position),
            PlayerData(R.drawable.img_about_lck_player, R.drawable.img_about_lck_player_team_color_t1, "Faker", R.drawable.img_about_lck_player_team_logo_t1, R.drawable.img_about_lck_player_position),
            PlayerData(R.drawable.img_about_lck_player, R.drawable.img_about_lck_player_team_color_t1, "Faker", R.drawable.img_about_lck_player_team_logo_t1, R.drawable.img_about_lck_player_position),
            PlayerData(R.drawable.img_about_lck_player, R.drawable.img_about_lck_player_team_color_t1, "Faker", R.drawable.img_about_lck_player_team_logo_t1, R.drawable.img_about_lck_player_position)
        )
    }
}
