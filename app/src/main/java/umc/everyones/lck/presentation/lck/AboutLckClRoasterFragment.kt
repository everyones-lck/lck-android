package umc.everyones.lck.presentation.lck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentAboutLckClRoasterBinding
import umc.everyones.lck.databinding.FragmentAboutLckCoachesBinding
import umc.everyones.lck.databinding.FragmentAboutLckRoasterBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.lck.adapter.PlayerAdapter
import umc.everyones.lck.presentation.lck.data.PlayerData
import umc.everyones.lck.presentation.lck.util.OnPlayerItemClickListener
@AndroidEntryPoint
class AboutLckClRoasterFragment : BaseFragment<FragmentAboutLckRoasterBinding>(R.layout.fragment_about_lck_roaster) {

    private val viewModel: AboutLckTeamViewModel by viewModels()
    private var listener: OnPlayerItemClickListener? = null

    override fun initObserver() {
        lifecycleScope.launch {
            viewModel.playerDetails.collect { result ->
                result?.onSuccess { playerDetails ->
                    val playerDataList = playerDetails.playerDetails.map { player ->
                        PlayerData(
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
        return when (teamId) {
            2 -> R.drawable.img_about_lck_player_team_color_t1
            3 -> R.drawable.img_about_lck_player_team_color_t1
            4 -> R.drawable.img_about_lck_player_team_color_t1
            // 필요한 만큼 추가
            else -> R.drawable.img_about_lck_player_team_color_geng
        }
    }

    private fun getTeamLogoResource(teamId: Int): Int {
        return when (teamId) {
            2 -> R.drawable.img_about_lck_player_team_logo_t1
            3 -> R.drawable.img_about_lck_player_team_logo_t1
            4 -> R.drawable.img_about_lck_player_team_logo_t1
            // 필요한 만큼 추가
            else -> R.drawable.img_about_lck_gen_g_gray
        }
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
