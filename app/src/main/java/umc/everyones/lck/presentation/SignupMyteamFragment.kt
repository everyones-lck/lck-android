package umc.everyones.lck.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import umc.everyones.lck.R
import umc.everyones.lck.databinding.DialogMyteamConfirmBinding
import umc.everyones.lck.databinding.FragmentSignupMyteamBinding

class SignupMyteamFragment : Fragment(R.layout.fragment_signup_myteam) {

    private var _binding: FragmentSignupMyteamBinding? = null
    private val binding get() = _binding!!

    private var selectedTeamView: ImageView? = null
    private var selectedTeamName: String? = null
    private var nickname: String? = null
    private var profileImageUri: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignupMyteamBinding.bind(view)

        nickname = arguments?.getString("nickname")
        profileImageUri = arguments?.getString("profileImageUri")

        val teamViews = mapOf(
            R.id.iv_signup_myteam_hanhwa to "Hanhwa",
            R.id.iv_signup_myteam_gen_g to "Gen.G",
            R.id.iv_signup_myteam_t1 to "T1",
            R.id.iv_signup_myteam_kwangdong_freecs to "Kwangdong Freecs",
            R.id.iv_signup_myteam_bnk to "BNK",
            R.id.iv_signup_myteam_nongshim_red_force to "Nongshim Red Force",
            R.id.iv_signup_myteam_drx to "DRX",
            R.id.iv_signup_myteam_ok_saving_bank_brion to "OK Saving Bank Brion",
            R.id.iv_signup_myteam_dplus_kia to "Dplus Kia",
            R.id.iv_signup_myteam_kt_rolster to "KT Rolster"
        )

        teamViews.forEach { (viewId, teamName) ->
            val teamView = view.findViewById<ImageView>(viewId)
            teamView.setOnClickListener {
                toggleTeamSelection(teamView, teamName)
            }
        }

        binding.ivSignupMyteamNext.setOnClickListener {
            if (selectedTeamView == null) {
                showTeamConfirmDialog()
            } else {
                val bundle = Bundle().apply {
                    putString("selectedTeam", selectedTeamName)
                    putString("nickname", nickname)
                    putString("profileImageUri", profileImageUri)
                }
                findNavController().navigate(R.id.action_signupMyteamFragment_to_signupSuccessFragment, bundle)
            }
        }
    }

    private fun toggleTeamSelection(view: ImageView, teamName: String) {
        if (selectedTeamView == view) {
            view.setBackgroundResource(R.drawable.shape_team_background)
            selectedTeamView = null
            selectedTeamName = null
        } else {
            selectedTeamView?.setBackgroundResource(R.drawable.shape_team_background)
            view.setBackgroundResource(R.drawable.shape_team_background_selected)
            selectedTeamView = view
            selectedTeamName = teamName
        }
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
            val bundle = Bundle().apply {
                putString("selectedTeam", selectedTeamName)
                putString("nickname", nickname)
                putString("profileImageUri", profileImageUri)
            }
            findNavController().navigate(R.id.action_signupMyteamFragment_to_signupSuccessFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
