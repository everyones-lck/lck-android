package umc.everyones.lck.presentation.login

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import umc.everyones.lck.R
import umc.everyones.lck.databinding.DialogMyteamConfirmBinding
import umc.everyones.lck.databinding.FragmentSignupMyteamBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.util.TeamData

@AndroidEntryPoint
class SignupMyteamFragment : BaseFragment<FragmentSignupMyteamBinding>(R.layout.fragment_signup_myteam) {

    private var selectedTeamName: String? = null
    private val viewModel: SignupViewModel by activityViewModels()
    private val navigator by lazy { findNavController() }

    override fun initObserver() {
        // No specific observers are needed for now
    }

    override fun initView() {
        val profileImageUri = viewModel.profileImageUri.value?.toString()

        setupTeamSelection()

        binding.ivSignupMyteamNext.setOnClickListener {
            if (selectedTeamName == null) {
                showTeamConfirmDialog(profileImageUri)
            } else {
                lifecycleScope.launch {
                    // User 추가
                    viewModel.addUser(profileImageUri ?: "", selectedTeamName ?: "default_team")

                    findNavController().navigate(R.id.action_signupMyteamFragment_to_signupSuccessFragment)
                }
            }
        }
    }

    private fun setupTeamSelection() {
        TeamData.teamLogos.forEach { (imageViewId, teamName) ->
            val imageView = binding.root.findViewById<ImageView>(imageViewId)
            imageView.setOnClickListener {
                selectedTeamName = if (selectedTeamName == teamName) {
                    null // 선택된 팀을 다시 클릭하면 선택 해제
                } else {
                    teamName
                }
                updateTeamSelectionUI()
            }
        }
    }

    private fun updateTeamSelectionUI() {
        TeamData.teamLogos.forEach { (imageViewId, teamName) ->
            val imageView = binding.root.findViewById<ImageView>(imageViewId)
            val drawable = if (teamName == selectedTeamName) {
                ContextCompat.getDrawable(requireContext(), R.drawable.shape_team_background_selected)
            } else {
                ContextCompat.getDrawable(requireContext(), R.drawable.shape_team_background)
            }
            imageView.background = drawable
        }
    }

    private fun showTeamConfirmDialog(profileImageUri: String?) {
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
                viewModel.addUser(profileImageUri ?: "", selectedTeamName ?: "default_team")

                findNavController().navigate(R.id.action_signupMyteamFragment_to_signupSuccessFragment)
            }
        }
    }
}
