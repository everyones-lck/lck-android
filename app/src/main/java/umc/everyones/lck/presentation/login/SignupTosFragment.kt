package umc.everyones.lck.presentation.login

import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.DialogNicknameConfirmBinding
import umc.everyones.lck.databinding.FragmentSignupTosBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class SignupTosFragment : BaseFragment<FragmentSignupTosBinding>(R.layout.fragment_signup_tos) {
    //
    private val viewModel: SignupViewModel by activityViewModels()
    private val navigator by lazy { findNavController() }

    private var isAgree1Checked = false
    private var isAgree2Checked = false

    override fun initObserver() {

    }

    override fun initView() {

        binding.tvSignupTosDetailsAgree1.setOnClickListener {
            // 세부 정보 보기 클릭 시 다이얼로그 표시
            showDetailsDialog1()
        }

        binding.tvSignupTosDetailsAgree2.setOnClickListener {
            // 세부 정보 보기 클릭 시 다이얼로그 표시
            showDetailsDialog2()
        }

        binding.ivSignupTosNext.setOnClickListener {
            if (!isAgree1Checked || !isAgree2Checked) {
                Toast.makeText(requireContext(), "모든 동의 항목을 체크해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                navigateToSignupNickname()
            }
        }

        binding.viewSignupTosAgreeCircle.setOnClickListener {
            toggleAgreement(1)
        }

        binding.viewSignupTosAgree1Circle.setOnClickListener {
            toggleAgreement(2)
        }
    }

    private fun showDetailsDialog1() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_signup_tos_details_agree_2, null)

        val dialogBinding = DialogNicknameConfirmBinding.bind(dialogView)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        val layoutParams = dialog.window?.attributes
        layoutParams?.dimAmount = 0.8f
        dialog.window?.attributes = layoutParams

        dialogBinding.btnConfirm.setOnSingleClickListener {
            dialog.dismiss()
            toggleAgreement(1)
        }
    }

    private fun showDetailsDialog2() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_signup_tos_details_agree_1, null)

        val dialogBinding = DialogNicknameConfirmBinding.bind(dialogView)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        val layoutParams = dialog.window?.attributes
        layoutParams?.dimAmount = 0.8f
        dialog.window?.attributes = layoutParams

        dialogBinding.btnConfirm.setOnSingleClickListener {
            dialog.dismiss()
            toggleAgreement(2)
        }
    }

    private fun navigateToSignupNickname() {
        navigator.navigate(R.id.action_signupTosFragment_to_signupNicknameFragment)
    }

    private fun toggleAgreement(agreementNumber: Int) {
        if (agreementNumber == 1) {
            isAgree1Checked = !isAgree1Checked
            updateCircleView(binding.viewSignupTosAgreeCircle, isAgree1Checked)
        } else if (agreementNumber == 2) {
            isAgree2Checked = !isAgree2Checked
            updateCircleView(binding.viewSignupTosAgree1Circle, isAgree2Checked)
        }
    }

    private fun updateCircleView(view: View, isChecked: Boolean) {
        view.background = if (isChecked) {
            ContextCompat.getDrawable(requireContext(), R.drawable.shape_selected_oval)
        } else {
            ContextCompat.getDrawable(requireContext(), R.drawable.shape_oval)
        }
    }
}