package umc.everyones.lck.presentation.match

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentTodayMatchLckPogBinding
import umc.everyones.lck.domain.model.response.match.CommonTodayMatchPogModel
import umc.everyones.lck.domain.model.todayMatch.LckPog
import umc.everyones.lck.domain.model.todayMatch.Player
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.match.adapter.LckPogMatchRVA
import umc.everyones.lck.presentation.match.adapter.LckPogPlayerRVA

@AndroidEntryPoint
class TodayMatchLckPogFragment : BaseFragment<FragmentTodayMatchLckPogBinding>(R.layout.fragment_today_match_lck_pog) {
    private val viewModel: TodayMatchLckPogViewModel by viewModels()
    private val todayViewModel: TodayMatchLckMatchViewModel by activityViewModels()
    private lateinit var lckPogMatchRVA: LckPogMatchRVA
    private var tabIndex = 0

    override fun initObserver() {
        // 세트 수를 받아와서 탭 레이아웃 설정
        viewModel.setCount.observe(viewLifecycleOwner) { setCountModel ->
            lckPogMatchRVA.updateSetCount(setCountModel.setCount)
            Timber.d("Set Count: ${setCountModel.setCount}")
        }
//        // 각 세트의 POG 데이터를 관찰하여 리사이클러뷰 업데이트
//        viewModel.setPogData.observe(viewLifecycleOwner) { pogData ->
//            pogData?.let {
//                lckPogMatchRVA.updatePlayers(listOf(CommonTodayMatchPogModel(it.id, it.name, it.profileImageUrl, it.seasonInfo, it.matchNumber, it.matchDate, tabIndex)))
//                Timber.d("Set POG Data: $pogData")
//            }
//        }
//        // 매치 POG 데이터를 관찰하여 리사이클러뷰 업데이트
//        viewModel.matchPogData.observe(viewLifecycleOwner) { pogData ->
//            pogData?.let {
//                lckPogMatchRVA.updatePlayers(listOf(CommonTodayMatchPogModel(it.id, it.name, it.profileImageUrl, it.seasonInfo, it.matchNumber, it.matchDate, tabIndex)))
//                Timber.d("Match POG Data: $pogData")
//            }
//        }

        viewModel.pogData.observe(viewLifecycleOwner) { response ->
            response?.let {
                lckPogMatchRVA.updatePlayers(listOf(CommonTodayMatchPogModel(it.seasonInfo, it.matchNumber, it.matchDate, it.setPogResponses, it.matchPogResponse, tabIndex)))
                Timber.d("POG Data: $response")
            }
        }


        // ViewModel의 matchData를 관찰하여 matchId를 가져와서 사용
        todayViewModel.matchData.observe(viewLifecycleOwner) { matchData ->
            if (matchData == null || matchData.matchResponses.isEmpty()) {
                // 경기가 없을 때
                binding.layoutTodayMatchPogNoMatch.visibility = View.VISIBLE
                binding.rvTodayMatchLckPogContainer.visibility = View.GONE
            } else {
                // 경기가 있을 때
                binding.layoutTodayMatchPogNoMatch.visibility = View.GONE
                binding.rvTodayMatchLckPogContainer.visibility = View.VISIBLE

                val matchId = matchData.matchResponses.firstOrNull()?.matchId ?: return@observe

                // 세트 수를 가져오기 위해 matchId를 사용
                viewModel.fetchTodayMatchSetCount(matchId)
                Timber.d("Match ID: $matchId")

                // 초기 데이터 로드
                viewModel.fetchTodayMatchPog(matchId)

                // 탭 선택 리스너 설정
                viewModel.selectedTabIndex.observe(viewLifecycleOwner) { tabIndex ->
                    this.tabIndex = tabIndex
                    Timber.d("Tab selected: $tabIndex")
                }
            }
        }
    }

    override fun initView() {
        setupRecyclerView()
    }
    private fun setupRecyclerView() {
        // 어댑터 설정 (초기에는 기본 setCount로 설정)
        lckPogMatchRVA = LckPogMatchRVA(
            setCount = 1,  // 초기값
            onTabSelected = { tabIndex ->
                viewModel.updateSelectedTab(tabIndex)
                Timber.d("frtabIndex %s", tabIndex.toString())
            }
        )
        binding.rvTodayMatchLckPogContainer.layoutManager = LinearLayoutManager(context)
        binding.rvTodayMatchLckPogContainer.adapter = lckPogMatchRVA
        binding.rvTodayMatchLckPogContainer.itemAnimator = null

    }

}