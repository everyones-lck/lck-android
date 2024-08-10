package umc.everyones.lck.presentation.match

import android.util.Log
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentTodayMatchLckPogBinding
import umc.everyones.lck.databinding.FragmentTodayMatchPredictionBinding
import umc.everyones.lck.domain.model.todayMatch.LckMatch
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.match.adapter.MatchPredictionRVA
import umc.everyones.lck.util.extension.showCustomSnackBar

class TodayMatchPredictionFragment : BaseFragment<FragmentTodayMatchPredictionBinding>(R.layout.fragment_today_match_prediction) {
    private lateinit var matchPredictionRVA: MatchPredictionRVA
    override fun initObserver() {

    }

    override fun initView() {
        goBackButton()
        matchVoteButton()
        setupRecyclerView()
    }

    private fun goBackButton() {
        binding.ivTodayMatchPredictionBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
    private fun matchVoteButton() {
        binding.tvTodayMatchPredictionVote.setOnClickListener {
            showCustomSnackBar(binding.root,"투표 되었습니다!")
        }
    }

    private fun setupRecyclerView() {
    matchPredictionRVA = MatchPredictionRVA { selectedMatch, selectedTeam ->
        // 사용자가 선택한 팀을 처리할 수 있습니다.
        // selectedTeam == 1이면 첫 번째 팀, 2이면 두 번째 팀이 선택됨을 의미
    }
    binding.rvTodayMatchPrediction.adapter = matchPredictionRVA

    // 전달된 팀 로고 리스트를 받는다.
    val teamLogos = arguments?.getIntArray("teamLogos")?.let { logos ->
        Log.d("TodayMatchPredictionFragment", "Received teamLogos: ${logos.joinToString()}")
        listOf(
            LckMatch(
                matchTitle = "",
                matchDate = "",
                team1Name = "팀 1", // 팀 이름을 실제 데이터로 대체
                team2Name = "팀 2", // 팀 이름을 실제 데이터로 대체
                team1LogoResId = logos[0],
                team1LogoBlur = 0,
                team2LogoResId = logos[1],
                team2LogoBlur = 0,
                team1WinRate = "",
                team2WinRate = ""
            )
        )
    } ?: emptyList()

    matchPredictionRVA.submitList(teamLogos)
    }
}