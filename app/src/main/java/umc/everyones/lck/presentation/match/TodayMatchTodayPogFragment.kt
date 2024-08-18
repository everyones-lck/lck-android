package umc.everyones.lck.presentation.match

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentTodayMatchTodayPogBinding
import umc.everyones.lck.domain.model.todayMatch.TodayPog
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.match.adapter.TodayPogPlayerRVA
import umc.everyones.lck.util.extension.showCustomSnackBar

@AndroidEntryPoint
class TodayMatchTodayPogFragment : BaseFragment<FragmentTodayMatchTodayPogBinding>(R.layout.fragment_today_match_today_pog) {
    private val viewModel: TodayMatchTodayPogViewModel by viewModels()
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
        viewModel.fetchTodayMatchVoteSetPog()
        viewModel.fetchTodayMatchVoteMatchPog()
        viewModel.voteSetPog()
        viewModel.voteMatchPog()
    }

    private fun goBackButton() {
        binding.ivTodayMatchTodayPogBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
    private fun pogVoteButton() {
        binding.tvTodayMatchTodayPogVote.setOnClickListener {
            showCustomSnackBar(binding.root,"투표 되었습니다!")
        }
    }

    private val pogPlayer = listOf(
        TodayPog(R.drawable.ic_profile_player),
        TodayPog(R.drawable.ic_t1),
        TodayPog(R.drawable.ic_gen_g),
        TodayPog(R.drawable.ic_kt_rolster),
        TodayPog(R.drawable.ic_profile_player)
    )

    private fun setupRecyclerView() {
        todayPogPlayerRVA1 = TodayPogPlayerRVA()
        binding.rvTodayMatchTodayPog1stVote.adapter = todayPogPlayerRVA1
        todayPogPlayerRVA1.submitList(pogPlayer)

        todayPogPlayerRVA2 = TodayPogPlayerRVA()
        binding.rvTodayMatchTodayPog2ndVote.adapter = todayPogPlayerRVA2
        todayPogPlayerRVA2.submitList(pogPlayer)

        todayPogPlayerRVA3 = TodayPogPlayerRVA()
        binding.rvTodayMatchTodayPog3rdVote.adapter = todayPogPlayerRVA3
        todayPogPlayerRVA3.submitList(pogPlayer)

        todayPogPlayerRVAMatch = TodayPogPlayerRVA()
        binding.rvTodayMatchTodayPogMatchVote.adapter = todayPogPlayerRVAMatch
        todayPogPlayerRVAMatch.submitList(pogPlayer)
    }

    private fun setupVoteImageViewClick() {
        binding.ivTodayMatchTodayPog1stVote.setOnClickListener {
            binding.ivTodayMatchTodayPog1stVote.visibility = View.GONE
            binding.rvTodayMatchTodayPog1stVote.visibility = View.VISIBLE
        }
        binding.ivTodayMatchTodayPog2ndVote.setOnClickListener {
            binding.ivTodayMatchTodayPog2ndVote.visibility = View.GONE
            binding.rvTodayMatchTodayPog2ndVote.visibility = View.VISIBLE
        }
        binding.ivTodayMatchTodayPog3rdVote.setOnClickListener {
            binding.ivTodayMatchTodayPog3rdVote.visibility = View.GONE
            binding.rvTodayMatchTodayPog3rdVote.visibility = View.VISIBLE
        }
        binding.ivTodayMatchTodayPogMatchVote.setOnClickListener {
            binding.ivTodayMatchTodayPogMatchVote.visibility = View.GONE
            binding.rvTodayMatchTodayPogMatchVote.visibility = View.VISIBLE
        }
    }
}