package umc.everyones.lck.presentation.lck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import umc.everyones.lck.databinding.ItemAboutLckBinding

class ItemAboutLckFragment : Fragment() {

    private var _binding: ItemAboutLckBinding? = null
    private val binding get() = _binding!!
    private var matchData: MatchData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ItemAboutLckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
    }

    private fun bindData() {
        matchData?.let { data ->
            binding.ivAboutLckTeam1.setImageResource(data.imageResId1)
            binding.ivAboutLckTeam2.setImageResource(data.imageResId2)
            binding.tvMatchTitle.text = data.matchTitle
            binding.tvMatchTime.text = data.matchTime
            binding.ivAboutLckTeam3.setImageResource(data.imageResId3)
            binding.ivAboutLckTeam4.setImageResource(data.imageResId4)
            binding.tvMatchTitle2.text = data.matchTitle2
            binding.tvMatchTime2.text = data.matchTime2
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}