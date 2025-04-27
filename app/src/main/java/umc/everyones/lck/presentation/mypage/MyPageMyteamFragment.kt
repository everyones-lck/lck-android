package umc.everyones.lck.presentation.mypage

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentMypageMyteamBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.util.TeamData
import umc.everyones.lck.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class MyPageMyteamFragment : BaseFragment<FragmentMypageMyteamBinding>(R.layout.fragment_mypage_myteam) {

    private var selectedTeamId: Int = 1 // 기본 팀 ID로 초기화
    private val myPageViewModel: MyPageViewModel by activityViewModels()
    private val navigator by lazy { findNavController() }

    override fun initObserver() {
        myPageViewModel.inquiryProfile()
        myPageViewModel.teamId.observe(viewLifecycleOwner) { teamId ->
            Timber.d("Observed teamId: $teamId") // teamId 로그 추가

            // 기존 팀 로고와 이름을 반영 (처음 로딩 시)
            // val teamLogoResId = TeamData.mypageMyteam[teamId] ?: R.drawable.ic_mypage_myteam_empty // 기본 로고 설정
            val teamName = TeamData.teamNames[teamId] // 팀 ID로 팀 이름 가져오기

            // 팀 로고와 이름 업데이트
            // binding.tvMypageMyteamTeam.setBackgroundResource(teamLogoResId)
            binding.tvMypageMyteamMyTier.text = teamName

            // 선택된 팀 ID 초기화 및 UI 업데이트
            selectedTeamId = teamId // 현재 팀 ID로 선택된 팀 ID 설정
           // updateTeamSelectionUI() // UI 업데이트
        }
    }


    override fun initView() {
        //setupTeamSelection()

        binding.tvMypageMyteamTopbarEdit.setOnSingleClickListener {
            // 선택된 팀이 없을 경우 기본 팀 ID(1)로 설정
            val teamIdToUpdate = selectedTeamId ?: 1

            lifecycleScope.launch {
                try {
                    // 팀 업데이트 호출
                    myPageViewModel.updateTeam(teamIdToUpdate)

                    // 팀 ID 관찰 (한 번만 등록)
                    myPageViewModel.teamId.observe(viewLifecycleOwner) { teamId ->
                        Timber.d("Observed teamId: $teamId") // teamId 로그 추가

                        // 기존 팀 로고를 반영 (처음 로딩 시)
                        val teamLogoResId = TeamData.mypageMyteam[teamId]
                        val teamName = TeamData.teamNames[teamId] // 팀 ID로 팀 이름 가져오기

                        if (teamLogoResId != null) {
                            // binding.tvMypageMyteamTeam.setBackgroundResource(teamLogoResId)
                            binding.tvMypageMyteamTeam.text = teamName
                        } else {
                            // 기본 로고 설정 (예: 선택된 팀이 없을 경우)
                            // binding.tvMypageMyteamTeam.setBackgroundResource(R.drawable.ic_mypage_myteam_empty)
                        }

                        // 팀 이름 업데이트
                        binding.tvMypageMyteamTeam.text = teamName ?: "-"
                    }

                    // 프로필 조회 (팀 업데이트 후)
                    myPageViewModel.inquiryProfile()

                    Timber.d( "Navigated to next fragment with team ID: $teamIdToUpdate")

                } catch (e: Exception) {
                    Timber.e("Error navigating", e)
                }
            }
        }

        binding.ivMypageMyteamBack.setOnSingleClickListener {
            findNavController().navigateUp()
        }
    }


/*    private fun setupTeamSelection() {
        TeamData.myteamLogos.forEach { (imageViewId, teamId) ->
            val imageView = binding.root.findViewById<ImageView>(imageViewId)
            if (imageView != null) {
                imageView.setOnSingleClickListener {
                    selectedTeamId = teamId
                    Timber.d("Selected team ID: $selectedTeamId")
                    updateTeamSelectionUI()
                    updateTeamInfoUI(selectedTeamId)
                }
            } else {
                Timber.e("ImageView with ID $imageViewId not found in layout.")
            }
        }
    }*/

    private fun updateTeamInfoUI(teamId: Int) {
        val teamName = TeamData.teamNames[teamId]
        binding.tvMypageMyteamMyTier.text = teamName ?: "-"
        // 필요하다면 팀 로고 업데이트 로직 추가
        val teamLogoResId = TeamData.mypageMyteam[teamId]
        // if (teamLogoResId != null) {
        //     binding.ivMypageMyteamTeamLogo.setImageResource(teamLogoResId)
        // }
    }

    //팀 선택 시 색상 변경
    /*private fun updateTeamSelectionUI() {
        TeamData.myteamLogos.forEach { (imageViewId, teamId) ->
            val imageView = binding.root.findViewById<ImageView>(imageViewId)
            val drawableRes = if (teamId == selectedTeamId) { // selectedTeamId로 변경
                R.drawable.shape_team_background_selected
            } else {
                R.drawable.shape_team_background
            }
            imageView.background = ContextCompat.getDrawable(requireContext(), drawableRes)
        }
    }*/
}
