package umc.everyones.lck.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentHomeBinding
import umc.everyones.lck.domain.model.response.home.HomeTodayMatchModel
import umc.everyones.lck.domain.model.todayMatch.LckMatch
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.login.LoginActivity
import umc.everyones.lck.presentation.mypage.MyPageActivity
import umc.everyones.lck.util.LoginManager
import umc.everyones.lck.presentation.home.adapter.HomeMatchContentVPA
import umc.everyones.lck.presentation.home.adapter.HomeMatchResultRVA

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by activityViewModels()
    override fun initObserver() {
        viewModel.matchData.observe(viewLifecycleOwner, Observer { matchData ->
            if (matchData?.todayMatches.isNullOrEmpty()) {
                // 경기가 없는 경우
                updateMatchContent(emptyList())
            } else {
                // 경기가 있는 경우
                updateMatchContent(matchData!!.todayMatches)
            }
            updateMatchResults(matchData?.recentMatchResults ?: emptyList())
        })
    }

    override fun initView() {
        viewModel.fetchHomeTodayMatchInformation()

        // 초기 UI 설정
        goMatchResult()
        goAboutLck()
        goCommunity()
        goViewingParty()
    }

    private fun updateMatchContent(todayMatches: List<HomeTodayMatchModel.TodayMatchesModel>) {
        val homeMatchContentVPA = HomeMatchContentVPA(todayMatches) {
            viewModel.setNavigateEvent(R.id.todayMatchTab)
        }
        binding.vpHomeMatchContent.adapter = homeMatchContentVPA
        binding.indicatorHomeDot.attachTo(binding.vpHomeMatchContent)
    }

    private fun updateMatchResults(recentMatchResults: List<HomeTodayMatchModel.RecentMatchResultModel>) {
        val homeMatchResultRVA = HomeMatchResultRVA(recentMatchResults)
        binding.rvHomeMatchResult.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvHomeMatchResult.setHasFixedSize(true)
        binding.rvHomeMatchResult.adapter = homeMatchResultRVA
    }

    private fun goMatchResult() {
        binding.layoutHomeMatchResult.setOnClickListener {
            viewModel.setNavigateEvent(R.id.todayMatchTab)
        }
    }

    private fun goAboutLck() {
        binding.ivHomeAboutLckBox.setOnClickListener {
            viewModel.setNavigateEvent(R.id.aboutLCKFragment)
        }
    }

    private fun goCommunity() {
        binding.ivHomeCommunityBox.setOnClickListener {
            viewModel.setNavigateEvent(R.id.communityFragment)
        }
    }

    private fun goViewingParty() {
        binding.ivHomeViewingPartyBox.setOnClickListener {
            viewModel.setNavigateEvent(R.id.viewingPartyTab)
        }
    }
}
