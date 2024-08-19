package umc.everyones.lck.presentation.lck

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentAboutLckRoasterBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.lck.adapter.PlayerAdapter
import umc.everyones.lck.presentation.lck.data.PlayerData
import umc.everyones.lck.presentation.lck.util.OnPlayerItemClickListener

@AndroidEntryPoint
class AboutLckRoasterFragment : BaseFragment<FragmentAboutLckRoasterBinding>(R.layout.fragment_about_lck_roaster) {

    private val viewModel: AboutLckTeamViewModel by viewModels({requireParentFragment()})
    private var listener: OnPlayerItemClickListener? = null

    override fun initObserver() {
        lifecycleScope.launch {
            viewModel.playerDetails.collect { result ->
                result?.onSuccess { playerDetails ->
                    val playerDataList = playerDetails.playerDetails.map { player ->
                        PlayerData(
                            playerId = player.playerId,
                            playerImg = player.profileImageUrl,
                            teamColor = getTeamColorResource(viewModel.teamId.value ?: 0),
                            name = player.playerName,
                            teamLogo = getTeamLogoResource(viewModel.teamId.value ?: 0),
                            position = R.drawable.img_about_lck_player_position // 임시로 고정값 사용
                        )
                    }
                    listener?.let {
                        binding.rvAboutLckRoaster.adapter = PlayerAdapter(playerDataList, it)
                    }
                }?.onFailure {
                    Log.e("AboutLckRoasterFragment", "Failed to fetch player details: ${it.message}")
                }
            }
        }
    }

    override fun initView() {
        listener = findParentListener()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val recyclerView: RecyclerView = binding.rvAboutLckRoaster
        recyclerView.layoutManager = GridLayoutManager(context, 3)
    }

    private fun getTeamColorResource(teamId: Int): Int {
        val colorRes = when (teamId) {
            2 -> R.drawable.img_about_lck_player_team_color_t1
            3 -> R.drawable.img_about_lck_player_team_color_t1
            4 -> R.drawable.img_about_lck_player_team_color_t1
            5 -> R.drawable.img_about_lck_player_team_color_t1
            6 -> R.drawable.img_about_lck_player_team_color_t1
            else -> R.drawable.img_about_lck_player_team_color_geng
        }
        return colorRes
    }

    private fun getTeamLogoResource(teamId: Int): Int {
        val logoRes = when (teamId) {
            2 -> R.drawable.img_about_lck_gen_g_gray
            3 -> R.drawable.img_about_lck_hanhwa_gray
            4 -> R.drawable.img_about_lck_dk_gray
            5 -> R.drawable.img_about_lck_player_team_logo_t1
            6 -> R.drawable.img_about_lck_kt_gray
            7 -> R.drawable.img_about_lck_gwangdong_gray
            8 -> R.drawable.img_about_lck_bnk_gray
            9 -> R.drawable.img_about_lck_red_force_gray
            10 -> R.drawable.img_about_lck_drx_gray
            11 -> R.drawable.img_about_lck_ok_gray
            else ->R.drawable.img_about_lck_ok_gray
        }
        return logoRes
    }

    private fun findParentListener(): OnPlayerItemClickListener? {
        var fragment = parentFragment
        while (fragment != null) {
            if (fragment is OnPlayerItemClickListener) {
                return fragment
            }
            fragment = fragment.parentFragment
        }
        return null
    }
}


