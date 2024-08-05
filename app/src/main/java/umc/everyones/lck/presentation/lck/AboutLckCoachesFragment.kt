package umc.everyones.lck.presentation.lck

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentAboutLckCoachesBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.lck.adapter.PlayerAdapter
import umc.everyones.lck.presentation.lck.data.PlayerData
import umc.everyones.lck.presentation.lck.util.OnPlayerItemClickListener

class AboutLckCoachesFragment : BaseFragment<FragmentAboutLckCoachesBinding>(R.layout.fragment_about_lck_coaches) {

    private lateinit var listener: OnPlayerItemClickListener

    override fun initObserver() {

    }

    override fun initView() {
        listener = parentFragment as OnPlayerItemClickListener
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val recyclerView: RecyclerView = binding.rvAboutLckCoaches
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
