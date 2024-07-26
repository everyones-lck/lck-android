package umc.everyones.lck.presentation.login

import android.view.LayoutInflater
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.DialogMyteamConfirmBinding
import umc.everyones.lck.databinding.FragmentSignupMyteamBinding
import umc.everyones.lck.presentation.base.BaseFragment

import android.widget.ImageView
import androidx.core.content.ContextCompat
import umc.everyones.lck.util.TeamData

@AndroidEntryPoint
class SignupMyteamFragment : BaseFragment<FragmentSignupMyteamBinding>(R.layout.fragment_signup_myteam) {

    private var selectedTeamName: String? = null
    private val args: SignupMyteamFragmentArgs by navArgs()

    override fun initObserver() {
        // No observers needed here
    }

    override fun initView() {
        val nickname = args.nickname
        val profileImageUri = args.profileImageUri

        setupTeamSelection { teamName ->
            selectedTeamName = teamName
        }

        binding.ivSignupMyteamNext.setOnClickListener {
            if (selectedTeamName == null) {
                showTeamConfirmDialog(nickname, profileImageUri)
            } else {
                navigateToSignupSuccess(nickname, profileImageUri, selectedTeamName)
            }
        }
    }

    private fun setupTeamSelection(onTeamSelected: (String?) -> Unit) {
        TeamData.teamLogos.forEach { (imageViewId, teamName) ->
            val imageView = binding.root.findViewById<ImageView>(imageViewId)
            imageView.setOnClickListener {
                if (selectedTeamName == teamName) {
                    selectedTeamName = null
                } else {
                    selectedTeamName = teamName
                }
                updateTeamSelectionUI()
                onTeamSelected(selectedTeamName)
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

    private fun showTeamConfirmDialog(nickname: String, profileImageUri: String?) {
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
            navigateToSignupSuccess(nickname, profileImageUri, selectedTeamName)
        }
    }

    private fun navigateToSignupSuccess(nickname: String, profileImageUri: String?, selectedTeamName: String?) {
        val action = SignupMyteamFragmentDirections
            .actionSignupMyteamFragmentToSignupSuccessFragment(
                nickname = nickname,
                profileImageUri = profileImageUri ?: "",
                selectedTeam = selectedTeamName ?: ""
            )
        findNavController().navigate(action)
    }
}
