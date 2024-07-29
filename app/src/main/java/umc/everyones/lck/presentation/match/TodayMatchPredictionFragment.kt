package umc.everyones.lck.presentation.match

import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentTodayMatchLckPogBinding
import umc.everyones.lck.databinding.FragmentTodayMatchPredictionBinding
import umc.everyones.lck.presentation.base.BaseFragment

class TodayMatchPredictionFragment : BaseFragment<FragmentTodayMatchPredictionBinding>(R.layout.fragment_today_match_prediction) {
    override fun initObserver() {

    }

    override fun initView() {
        goBackButton()
        matchVoteButton()
        setupListeners()
    }

    private fun goBackButton() {
        binding.ivTodayMatchPredictionBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
    private fun matchVoteButton() {
        binding.tvTodayMatchPredictionVote.setOnClickListener {
            Toast.makeText(requireContext(), "투표 되었습니다!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupListeners() {
        binding.btnTodayMatchPredictionRadio1.setOnClickListener {
            binding.rgTodayMatchPredictionGroup.check(binding.btnTodayMatchPredictionRadio1.id)
        }

        binding.btnTodayMatchPredictionRadio2.setOnClickListener {
            binding.rgTodayMatchPredictionGroup.check(binding.btnTodayMatchPredictionRadio2.id)
        }

        binding.rgTodayMatchPredictionGroup.findViewById<ConstraintLayout>(R.id.layout_today_match_prediction_container1)
            .setOnClickListener {
                binding.rgTodayMatchPredictionGroup.check(binding.btnTodayMatchPredictionRadio1.id)
            }

        binding.rgTodayMatchPredictionGroup.findViewById<ConstraintLayout>(R.id.layout_today_match_prediction_container2)
            .setOnClickListener {
                binding.rgTodayMatchPredictionGroup.check(binding.btnTodayMatchPredictionRadio2.id)
            }
    }
}