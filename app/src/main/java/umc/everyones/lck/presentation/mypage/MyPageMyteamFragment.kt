package umc.everyones.lck.presentation.mypage

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
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

    private var selectedTeamId: Int? = 1 // 기본 팀 ID로 초기화
    private val myPageViewModel: MyPageViewModel by activityViewModels()
    private val navigator by lazy { findNavController() }

    override fun initObserver() {
        myPageViewModel.teamId.observe(viewLifecycleOwner) { teamId ->
            Timber.d("Observed teamId: $teamId")
            selectedTeamId = teamId
        }
    }


    override fun initView() {
        setInitialState()
        setupTeamSelection()
        if (selectedTeamId == null) {
            setInitialState()
        } else {
            setButton()
        }

        binding.tvMypageMyteamNext.setOnSingleClickListener {

            val teamIdToUpdate = selectedTeamId ?: 1

            lifecycleScope.launch {
                // 팀 업데이트 호출 및 결과 처리
                myPageViewModel.updateTeam(teamIdToUpdate) { isSuccess ->
                    if (isSuccess) {
                        Timber.d("Team update successful. Navigating to next fragment with team ID: $teamIdToUpdate")
                        Toast.makeText(
                            requireContext(),
                            "Team 변경 되었습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // API 호출 실패 시 토스트 메시지 표시
                        Timber.d("Team update failed.")
                        Toast.makeText(
                            requireContext(),
                            "My Team은 한달에 한 번 변경 가능합니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.ivMypageMyteamBack.setOnSingleClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setInitialState() {
        binding.tvMypageMyteamNext.setBackgroundResource(R.drawable.shape_rect_4_grayscale_700_line_new_bg_fill)
        binding.tvMypageMyteamNext.setTextColor(requireContext().getColor(R.color.grayscale_700)) // 회색
    }

    private fun setButton() {
        binding.tvMypageMyteamNext.setBackgroundResource(R.drawable.shape_rect_4_gray_line_black_fill)
        binding.tvMypageMyteamNext.setTextColor(requireContext().getColor(R.color.grayscale_100)) // 회색
    }

    private fun setupTeamSelection() {
        TeamData.teamMyPageLogos.forEach { (linearLayoutId, teamId) -> // LinearLayout의 ID 사용
            val linearLayout = binding.root.findViewById<LinearLayout>(linearLayoutId)
            linearLayout?.setOnClickListener {
                selectedTeamId = if (selectedTeamId == teamId) {
                    null
                } else {
                    teamId
                }
                updateTeamSelectionUI()

                val teamIdToSet = selectedTeamId ?: 1
                myPageViewModel.setTeamId(teamIdToSet)
            }
        }
    }

    private fun updateTeamSelectionUI() {
        TeamData.teamMyPageLogos.forEach { (linearLayoutId, teamId) -> // LinearLayout의 ID 사용
            val linearLayout = binding.root.findViewById<LinearLayout>(linearLayoutId)
            linearLayout?.let {
                val drawableRes = if (teamId == selectedTeamId) {
                    R.drawable.shape_team_background_selected
                } else {
                    R.drawable.shape_team_background
                }
                it.background = ContextCompat.getDrawable(requireContext(), drawableRes)
            }
        }
    }
}
