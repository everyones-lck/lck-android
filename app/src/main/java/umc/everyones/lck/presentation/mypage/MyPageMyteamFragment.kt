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

    private var initialServerTeamId: Int? = null // 서버에서 가져온 초기 팀 ID (이전 선택)
    private var currentSelectedTeamId: Int? = null // 사용자가 현재 화면에서 선택한 팀 ID

    private val myPageViewModel: MyPageViewModel by activityViewModels()
    private val navigator by lazy { findNavController() }

    override fun initObserver() {
        myPageViewModel.teamId.observe(viewLifecycleOwner) { teamIdFromServer ->
            Timber.d("Observed teamId from ViewModel: $teamIdFromServer")
            if (initialServerTeamId == null) { // 처음 한 번만 초기 팀 ID로 설정
                initialServerTeamId = teamIdFromServer
                currentSelectedTeamId = teamIdFromServer // 초기에는 서버 값을 현재 선택으로 간주
            }
            updateTeamSelectionUI()
            updateButtonState()
        }
    }

    override fun initView() {
        setupTeamSelection() // 팀 선택 리스너 설정

        // 초기 UI 업데이트는 initObserver에서 ViewModel 데이터 수신 후 처리

        binding.tvMypageMyteamNext.setOnSingleClickListener {
            if (currentSelectedTeamId == null) {
                Toast.makeText(requireContext(), "팀을 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnSingleClickListener
            }

            // 초기 서버 팀 ID와 현재 선택한 팀 ID가 같으면 변경 요청 X (선택 사항)
            if (currentSelectedTeamId == initialServerTeamId) {
                Toast.makeText(requireContext(), "이미 선택된 팀입니다. 다른 팀을 선택해주세요.", Toast.LENGTH_LONG).show()
                // 또는 그냥 이전 화면으로 이동 등의 처리
                // navigator.navigateUp()
                return@setOnSingleClickListener
            }

            lifecycleScope.launch {
                myPageViewModel.updateTeam(currentSelectedTeamId!!) { isSuccess ->
                    if (isSuccess) {
                        Timber.d("Team update successful for team ID: $currentSelectedTeamId")
                        Toast.makeText(requireContext(), "응원 팀이 변경되었습니다.", Toast.LENGTH_SHORT).show()

                        // 성공 시, 현재 선택된 팀을 새로운 초기 팀으로 간주
                        initialServerTeamId = currentSelectedTeamId
                        // currentSelectedTeamId는 그대로 유지하거나, 화면을 벗어나면 null로 할 수 있음
                        // 여기서는 화면에 남아있다고 가정하고 UI와 버튼 상태 다시 업데이트

                        updateTeamSelectionUI()
                        updateButtonState()
                        // navigator.navigate(R.id.action_myPageMyteamFragment_to_myPageFragment) // 예시: 마이페이지 메인으로 이동
                    } else {
                        Timber.d("Team update failed.")
                        Toast.makeText(requireContext(), "응원 팀은 한 달에 한 번만 변경 가능합니다.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        binding.ivMypageMyteamBack.setOnSingleClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setInitialButtonState() {
        binding.tvMypageMyteamNext.setBackgroundResource(R.drawable.shape_rect_4_grayscale_700_line_new_bg_fill)
        binding.tvMypageMyteamNext.setTextColor(requireContext().getColor(R.color.grayscale_700))
        binding.tvMypageMyteamNext.isEnabled = false
    }

    private fun setActiveButtonState() {
        binding.tvMypageMyteamNext.setBackgroundResource(R.drawable.shape_rect_4_gray_line_black_fill) // 활성화 시 배경 (흰색 계열)
        binding.tvMypageMyteamNext.setTextColor(requireContext().getColor(R.color.grayscale_100)) // 활성화 시 텍스트 색상
        binding.tvMypageMyteamNext.isEnabled = true
    }

    private fun updateButtonState() {
        // 현재 선택된 팀이 있고, 그 팀이 서버에서 가져온 초기 팀과 다를 때만 버튼 활성화
        if (currentSelectedTeamId != null && currentSelectedTeamId != initialServerTeamId) {
            setActiveButtonState()
        } else {
            setInitialButtonState()
        }
    }

    private fun setupTeamSelection() {
        TeamData.teamMyPageLogos.forEach { (linearLayoutId, teamId) ->
            val linearLayout = binding.root.findViewById<LinearLayout>(linearLayoutId)
            linearLayout?.setOnClickListener {
                // 이전에 선택된 팀이 현재 클릭된 팀과 같으면, 선택 해제 (선택 사항: 현재 로직에서는 해제 안 함)
                // if (currentSelectedTeamId == teamId) {
                // currentSelectedTeamId = null
                // } else {
                currentSelectedTeamId = teamId
                // }
                updateTeamSelectionUI()
                updateButtonState()
            }
        }
    }

    private fun updateTeamSelectionUI() {
        TeamData.teamMyPageLogos.forEach { (linearLayoutId, teamId) ->
            val linearLayout = binding.root.findViewById<LinearLayout>(linearLayoutId)
            linearLayout?.let {
                if (teamId == currentSelectedTeamId) {
                    // "선택됨" 상태 (흰색 배경)
                    it.background = ContextCompat.getDrawable(requireContext(), R.drawable.shape_team_background_selected) // 흰색 배경 Drawable
                } else {
                    // "선택 안됨" 상태 (기본 배경)
                    it.background = ContextCompat.getDrawable(requireContext(), R.drawable.shape_team_background) // 기본 배경 Drawable
                }
            }
        }
    }
}
