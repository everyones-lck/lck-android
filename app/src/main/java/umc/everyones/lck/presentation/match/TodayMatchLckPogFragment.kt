package umc.everyones.lck.presentation.match

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
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
    private lateinit var lckPogMatchRVA: LckPogMatchRVA
    private var tabIndex = 0
//    private lateinit var lckPogPlayerRVA: LckPogPlayerRVA

    override fun initObserver() {
        // 세트 수를 받아와서 탭 레이아웃 설정
        viewModel.setCount.observe(viewLifecycleOwner) { setCountModel ->
            lckPogMatchRVA.updateSetCount(setCountModel.setCount)
            Log.d("TodayMatchLckPogFragment", "Set Count: ${setCountModel.setCount}")
        }
//        // 각 세트의 POG 데이터를 관찰하여 리사이클러뷰 업데이트
//        viewModel.setPogData.observe(viewLifecycleOwner) { pogData ->
//            pogData?.let {
//                lckPogMatchRVA.submitList(listOf(it)) // 데이터를 리스트로 변환하여 전달
//                Log.d("TodayMatchLckPogFragment", "Set POG Data: $pogData")
//            }
//        }
        // 각 세트의 POG 데이터를 관찰하여 리사이클러뷰 업데이트
        viewModel.setPogData.observe(viewLifecycleOwner) { pogData ->
            pogData?.let {
                lckPogMatchRVA.updatePlayers(listOf(it))
                Log.d("TodayMatchLckPogFragment", "Set POG Data: $pogData")
            }
        }

//        // 매치 POG 데이터를 관찰하여 리사이클러뷰 업데이트
//        viewModel.matchPogData.observe(viewLifecycleOwner) { pogData ->
//            pogData?.let {
//                lckPogMatchRVA.submitList(listOf(it))  // 매치별 POG 데이터를 업데이트
//                Log.d("TodayMatchLckPogFragment", "Match POG Data: $pogData")
//            }
//        }
        // 매치 POG 데이터를 관찰하여 리사이클러뷰 업데이트
        viewModel.matchPogData.observe(viewLifecycleOwner) { pogData ->
            pogData?.let {
                lckPogMatchRVA.updatePlayers(listOf(CommonTodayMatchPogModel(it.id, it.name, it.profileImageUrl, it.seasonInfo, it.matchNumber, it.matchDate, tabIndex)))
                Log.d("TodayMatchLckPogFragment", "Match POG Data: $pogData")
            }
        }
//        // 탭 선택 시 세트별 POG 데이터를 불러오기
//        viewModel.selectedTabIndex.observe(viewLifecycleOwner) { tabIndex ->
//            val matchId = 3L // 실제 matchId를 사용
//            if (tabIndex < (viewModel.setCount.value?.setCount ?: 0)) {
//                viewModel.fetchTodayMatchSetPog(tabIndex + 1, matchId)
//            } else {
//                viewModel.fetchTodayMatchMatchPog(matchId)
//            }
//            Log.d("TodayMatchLckPogFragment", "Tab selected: $tabIndex")
//        }
        // 탭 선택 시 세트별 POG 데이터를 불러오기
        viewModel.selectedTabIndex.observe(viewLifecycleOwner) { tabIndex ->
            val matchId = 3L
            this.tabIndex = tabIndex
            if (tabIndex < (viewModel.setCount.value?.setCount ?: 0)) {
                viewModel.fetchTodayMatchSetPog(tabIndex + 1, matchId)
            } else {
                viewModel.fetchTodayMatchMatchPog(matchId)
            }
            Log.d("TodayMatchLckPogFragment", "Tab selected: $tabIndex")
        }
    }

    override fun initView() {
        setupRecyclerView()
        viewModel.fetchTodayMatchSetCount(3)
//        viewModel.fetchTodayMatchSetPog(1,3)
//        viewModel.fetchTodayMatchMatchPog(3)
        viewModel.updateSelectedTab(0)
    }
    private fun setupRecyclerView() {
        // 어댑터 설정 (초기에는 기본 setCount로 설정)
        lckPogMatchRVA = LckPogMatchRVA(
            setCount = 1,  // 초기값
            onTabSelected = { tabIndex ->
                viewModel.updateSelectedTab(tabIndex)
                Log.d("frtabIndex", tabIndex.toString())
            }
        )
        binding.rvTodayMatchLckPogContainer.layoutManager = LinearLayoutManager(context)
        binding.rvTodayMatchLckPogContainer.adapter = lckPogMatchRVA

    }
//    private fun updateRecyclerView(pogData: List<CommonTodayMatchPogModel>) {
//        lckPogMatchRVA.updateItems(pogData)
//    }

}