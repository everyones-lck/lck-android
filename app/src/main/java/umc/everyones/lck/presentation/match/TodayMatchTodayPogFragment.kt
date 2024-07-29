package umc.everyones.lck.presentation.match

import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentTodayMatchTodayPogBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.match.adapter.TodayPogPlayerRVA

class TodayMatchTodayPogFragment : BaseFragment<FragmentTodayMatchTodayPogBinding>(R.layout.fragment_today_match_today_pog) {
    private lateinit var todayPogPlayerRVA1: TodayPogPlayerRVA
    private lateinit var todayPogPlayerRVA2: TodayPogPlayerRVA
    private lateinit var todayPogPlayerRVA3: TodayPogPlayerRVA
    private lateinit var todayPogPlayerRVAMatch: TodayPogPlayerRVA
    override fun initObserver() {

    }

    override fun initView() {
        goBackButton()
        pogVoteButton()
        setupRecyclerView()
        setupVoteImageViewClick()
    }

    private fun goBackButton() {
        binding.ivTodayMatchTodayPogBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
    private fun pogVoteButton() {
        binding.tvTodayMatchTodayPogVote.setOnClickListener {
            Toast.makeText(requireContext(), "투표 되었습니다!", Toast.LENGTH_SHORT).show()
        }
    }
    private val pogPlayer = listOf(
        R.drawable.ic_profile_player,
        R.drawable.ic_profile_player,
        R.drawable.ic_profile_player,
        R.drawable.ic_profile_player,
        R.drawable.ic_profile_player
    )
    private fun setupRecyclerView() {
        // 리사이클러뷰 1
        todayPogPlayerRVA1 = TodayPogPlayerRVA(pogPlayer) { selectedPlayerIndex ->
            handleItemClick(todayPogPlayerRVA1, selectedPlayerIndex, binding.rvTodayMatchTodayPog1stVote)
        }
        setupRecyclerView(binding.rvTodayMatchTodayPog1stVote, todayPogPlayerRVA1)

        // 리사이클러뷰 2
        todayPogPlayerRVA2 = TodayPogPlayerRVA(pogPlayer) { selectedPlayerIndex ->
            handleItemClick(todayPogPlayerRVA2, selectedPlayerIndex, binding.rvTodayMatchTodayPog2ndVote)
        }
        setupRecyclerView(binding.rvTodayMatchTodayPog2ndVote, todayPogPlayerRVA2)

        // 리사이클러뷰 3
        todayPogPlayerRVA3 = TodayPogPlayerRVA(pogPlayer) { selectedPlayerIndex ->
            handleItemClick(todayPogPlayerRVA3, selectedPlayerIndex, binding.rvTodayMatchTodayPog3rdVote)
        }
        setupRecyclerView(binding.rvTodayMatchTodayPog3rdVote, todayPogPlayerRVA3)

        // 리사이클러뷰 Match
        todayPogPlayerRVAMatch = TodayPogPlayerRVA(pogPlayer) { selectedPlayerIndex ->
            handleItemClick(todayPogPlayerRVAMatch, selectedPlayerIndex, binding.rvTodayMatchTodayPogMatchVote)
        }
        setupRecyclerView(binding.rvTodayMatchTodayPogMatchVote, todayPogPlayerRVAMatch)
    }
    private fun setupRecyclerView(recyclerView: RecyclerView, adapter: TodayPogPlayerRVA) {
        recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            LinearSnapHelper().attachToRecyclerView(this)
        }
    }
    private fun handleItemClick(adapter: TodayPogPlayerRVA, selectedPlayerIndex: Int, recyclerView: RecyclerView) {
        if (selectedPlayerIndex == -1) {
            // 모든 아이템을 다시 보여주기
            adapter.updateData(pogPlayer)
        } else {
            // 선택된 아이템만 보여주기
            val selectedPlayer = pogPlayer[selectedPlayerIndex]
            adapter.updateData(listOf(selectedPlayer))
            // 가운데로 스크롤
            recyclerView.smoothScrollToPosition(0)
        }
    }

    private fun setupVoteImageViewClick() {
        binding.ivTodayMatchTodayPog1stVote.setOnClickListener {
            binding.ivTodayMatchTodayPog1stVote.visibility = View.GONE
            binding.rvTodayMatchTodayPog1stVote.visibility = View.VISIBLE
            todayPogPlayerRVA1.resetSelection()
        }
        binding.ivTodayMatchTodayPog2ndVote.setOnClickListener {
            binding.ivTodayMatchTodayPog2ndVote.visibility = View.GONE
            binding.rvTodayMatchTodayPog2ndVote.visibility = View.VISIBLE
            todayPogPlayerRVA2.resetSelection()
        }
        binding.ivTodayMatchTodayPog3rdVote.setOnClickListener {
            binding.ivTodayMatchTodayPog3rdVote.visibility = View.GONE
            binding.rvTodayMatchTodayPog3rdVote.visibility = View.VISIBLE
            todayPogPlayerRVA3.resetSelection()
        }
        binding.ivTodayMatchTodayPogMatchVote.setOnClickListener {
            binding.ivTodayMatchTodayPogMatchVote.visibility = View.GONE
            binding.rvTodayMatchTodayPogMatchVote.visibility = View.VISIBLE
            todayPogPlayerRVAMatch.resetSelection()
        }
    }
}