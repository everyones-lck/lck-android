package umc.everyones.lck.presentation.lck

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentAboutLckRoasterBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.lck.adapter.PlayerAdapter
import umc.everyones.lck.presentation.lck.data.PlayerData
import umc.everyones.lck.presentation.lck.util.OnPlayerItemClickListener

@AndroidEntryPoint
class AboutLckRoasterFragment : BaseFragment<FragmentAboutLckRoasterBinding>(R.layout.fragment_about_lck_roaster) {

    private var listener: OnPlayerItemClickListener? = null

    override fun initObserver() {
    }

    override fun initView() {
        listener = findParentListener()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val recyclerView: RecyclerView = binding.rvAboutLckRoaster
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        listener?.let {
            recyclerView.adapter = PlayerAdapter(getPlayers(), it)
        }
    }

    private fun getPlayers(): List<PlayerData> {
        return listOf(
            PlayerData(R.drawable.img_about_lck_player, R.drawable.img_about_lck_player_team_color_t1, "Faker", R.drawable.img_about_lck_player_team_logo_t1, R.drawable.img_about_lck_player_position),
            PlayerData(R.drawable.img_about_lck_player, R.drawable.img_about_lck_player_team_color_t1, "Faker", R.drawable.img_about_lck_player_team_logo_t1, R.drawable.img_about_lck_player_position),
            PlayerData(R.drawable.img_about_lck_player, R.drawable.img_about_lck_player_team_color_t1, "Faker", R.drawable.img_about_lck_player_team_logo_t1, R.drawable.img_about_lck_player_position),
            PlayerData(R.drawable.img_about_lck_player, R.drawable.img_about_lck_player_team_color_t1, "Faker", R.drawable.img_about_lck_player_team_logo_t1, R.drawable.img_about_lck_player_position),
            PlayerData(R.drawable.img_about_lck_player, R.drawable.img_about_lck_player_team_color_t1, "Faker", R.drawable.img_about_lck_player_team_logo_t1, R.drawable.img_about_lck_player_position)
        )
    }

    //NavHostFragment의 호스팅으로 인해 발생하는 부모 프래그먼트 문제를 해결하기 위한 메서드
    private fun findParentListener(): OnPlayerItemClickListener? {
        var fragment = parentFragment
        while (fragment != null) {
            if (fragment is OnPlayerItemClickListener) {
                return fragment
            }
            fragment = fragment.parentFragment
        }
        throw ClassCastException("No parent fragment implements OnPlayerItemClickListener")
    }
}

