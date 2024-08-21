package umc.everyones.lck.presentation.lck

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentAboutLckCoachesBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.lck.adapter.PlayerAdapter
import umc.everyones.lck.presentation.lck.data.PlayerData
import umc.everyones.lck.presentation.lck.util.OnPlayerItemClickListener

@AndroidEntryPoint
class AboutLckCoachesFragment : BaseFragment<FragmentAboutLckCoachesBinding>(R.layout.fragment_about_lck_coaches) {

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
                            isCaptain = player.isCaptain,
                            position = player.position
                        )
                    }
                    listener?.let {
                        binding.rvAboutLckCoaches.adapter = PlayerAdapter(playerDataList, it)
                    }
                }?.onFailure {
                }
            }
        }
    }

    override fun initView() {
        listener = findParentListener()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val recyclerView: RecyclerView = binding.rvAboutLckCoaches
        recyclerView.layoutManager = GridLayoutManager(context, 3)
    }


    private fun getTeamColorResource(teamId: Int): Int {
        val colorRes = when (teamId) {
            2 -> R.drawable.img_about_lck_player_team_color_geng
            3 -> R.drawable.img_about_lck_player_team_color_hanwha
            4 -> R.drawable.img_about_lck_player_team_color_kia
            5 -> R.drawable.img_about_lck_player_team_color_t1_kt_kdf
            6 -> R.drawable.img_about_lck_player_team_color_t1_kt_kdf
            7 -> R.drawable.img_about_lck_player_team_color_t1_kt_kdf
            8 -> R.drawable.img_about_lck_player_team_color_bnk
            9 -> R.drawable.img_about_lck_player_team_color_t1_kt_kdf
            10 -> R.drawable.img_about_lck_player_team_color_drx
            11 -> R.drawable.img_about_lck_player_team_color_ok
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