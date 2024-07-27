package umc.everyones.lck.presentation.lck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import umc.everyones.lck.databinding.ItemAboutLckNoMatchesBinding

class ItemAboutLckNoMatchesFragment : Fragment() {

    private var _binding: ItemAboutLckNoMatchesBinding? = null
    private val binding get() = _binding!!
    /*private var matchData2: MatchData? = null*/


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ItemAboutLckNoMatchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    /*private fun bindData() {
        matchData2?.let { data ->
            binding.ivAboutLckTeam1.setImageResource(data.imageResId1)
            binding.ivAboutLckTeam2.setImageResource(data.imageResId2)
            binding.tvMatchTitle.text = data.matchTitle
            binding.tvMatchTime.text = data.matchTime
        }
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
