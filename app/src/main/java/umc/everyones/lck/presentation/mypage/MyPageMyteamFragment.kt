package umc.everyones.lck.presentation.mypage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import umc.everyones.lck.R
import umc.everyones.lck.databinding.DialogMyteamConfirmBinding
import umc.everyones.lck.databinding.FragmentMypageMyteamBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.util.TeamData

@AndroidEntryPoint
class MyPageMyteamFragment : BaseFragment<FragmentMypageMyteamBinding>(R.layout.fragment_mypage_myteam) {

    private var selectedTeamName: String? = null
    private var isScrollViewExpanded = false // Track the state of the ScrollView

    override fun initObserver() {
        // ViewModel 관찰이 필요한 경우 추가
    }

    override fun initView() {
        setupTeamSelection { teamName ->
            selectedTeamName = teamName
            Log.d("MyPageMyteamFragment", "Selected team: $selectedTeamName")
        }

        binding.tvMypageMyteamTopbarEdit.setOnClickListener {
            if (selectedTeamName == null) {
                showTeamConfirmDialog()
            } else {
                lifecycleScope.launch {
                    try {
                        Log.d("MyPageMyteamFragment", "Navigated to next fragment with team: $selectedTeamName")
                    } catch (e: Exception) {
                        Log.e("MyPageMyteamFragment", "Error navigating", e)
                    }
                }
            }
        }

        // Initialize the ScrollView and Arrow Button
        binding.layoutMypageMyteamList.visibility = View.GONE
        binding.btnMypageMyteamArrow.setImageResource(R.drawable.ic_arrow_down)

        // Set up click listener for the arrow button
        binding.btnMypageMyteamArrow.setOnClickListener {
            toggleScrollView()
        }

        binding.ivMypageMyteamBack.setOnClickListener{
            requireActivity().onBackPressed()
        }
    }

    private fun setupTeamSelection(onTeamSelected: (String?) -> Unit) {
        TeamData.myteamLogos.forEach { (imageViewId, teamName) ->
            val imageView = binding.root.findViewById<ImageView>(imageViewId)
            imageView.setOnClickListener {
                selectedTeamName = if (selectedTeamName == teamName) null else teamName
                updateTeamSelectionUI()
                onTeamSelected(selectedTeamName)
            }
        }
    }

    private fun updateTeamSelectionUI() {
        TeamData.myteamLogos.forEach { (imageViewId, teamName) ->
            val imageView = binding.root.findViewById<ImageView>(imageViewId)
            val drawable = if (teamName == selectedTeamName) {
                ContextCompat.getDrawable(requireContext(), R.drawable.shape_team_background_selected)
            } else {
                ContextCompat.getDrawable(requireContext(), R.drawable.shape_team_background)
            }
            imageView.background = drawable
        }
    }

    private fun toggleScrollView() {
        if (isScrollViewExpanded) {
            // Collapse ScrollView
            binding.layoutMypageMyteamList.visibility = View.GONE
            binding.btnMypageMyteamArrow.setImageResource(R.drawable.ic_arrow_down)
            // Optionally, add collapse animation here
        } else {
            // Expand ScrollView
            binding.layoutMypageMyteamList.visibility = View.VISIBLE
            binding.btnMypageMyteamArrow.setImageResource(R.drawable.ic_arrow_up)
            // Optionally, add expand animation here
        }
        isScrollViewExpanded = !isScrollViewExpanded
    }

    private fun showTeamConfirmDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_myteam_confirm, null)
        val dialogBinding = DialogMyteamConfirmBinding.bind(dialogView)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        val layoutParams = dialog.window?.attributes
        layoutParams?.dimAmount = 0.8f
        dialog.window?.attributes = layoutParams

        dialogBinding.btnChange.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnConfirm.setOnClickListener {
            dialog.dismiss()
            lifecycleScope.launch {
                try {
                    // 필요에 따라 사용자 추가 로직을 작성합니다.
                    Log.d("MyPageMyteamFragment", "Navigated to next fragment with default team")
                } catch (e: Exception) {
                    Log.e("MyPageMyteamFragment", "Error navigating", e)
                }
            }
        }
    }
}
