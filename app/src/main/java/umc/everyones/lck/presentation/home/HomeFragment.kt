package umc.everyones.lck.presentation.home

import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.DialogSignupTosDetailsAgree1Binding
import umc.everyones.lck.databinding.DialogSignupTosDetailsAgree2Binding
import umc.everyones.lck.databinding.FragmentHomeBinding
import umc.everyones.lck.domain.model.response.home.HomeTodayMatchModel
import umc.everyones.lck.domain.model.response.match.TodayMatchInformationModel
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.home.adapter.HomeMatchContentVPA
import umc.everyones.lck.presentation.home.adapter.HomeMatchResultRVA
import umc.everyones.lck.presentation.match.TodayMatchLckMatchViewModel
import umc.everyones.lck.presentation.mypage.MyPageActivity
import umc.everyones.lck.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by activityViewModels()
    private val matchViewModel: TodayMatchLckMatchViewModel by activityViewModels()
    override fun initObserver() {
        viewModel.matchData.observe(viewLifecycleOwner, Observer { matchData ->
//            if (matchData?.todayMatches.isNullOrEmpty()) {
//                // 경기가 없는 경우
//                updateMatchContent(emptyList())
//            } else {
//                // 경기가 있는 경우
//                updateMatchContent(matchData!!.todayMatches)
//            }
            updateMatchResults(matchData?.recentMatchResults ?: emptyList())
        })
        matchViewModel.matchData.observe(viewLifecycleOwner, Observer { todayMatch ->
            if (todayMatch?.matchResponses.isNullOrEmpty()) {
                // 경기가 없는 경우
                updateMatchContent(emptyList())
            } else {
                // 경기가 있는 경우
                updateMatchContent(todayMatch!!.matchResponses)
            }
        })
    }

    override fun initView() {
        viewModel.fetchHomeTodayMatchInformation()
        matchViewModel.fetchTodayMatchInformation()
        goMyPage()
        setUpService()
    }

    private fun updateMatchContent(todayMatches: List<TodayMatchInformationModel.MatchResponsesModel>) {
        val homeMatchContentVPA = HomeMatchContentVPA(todayMatches) {
            viewModel.setNavigateEvent(R.id.todayMatchTab)
        }
        binding.vpHomeMatchContent.adapter = homeMatchContentVPA
        binding.indicatorHomeDot.attachTo(binding.vpHomeMatchContent)
    }

    private fun updateMatchResults(recentMatchResults: List<HomeTodayMatchModel.RecentMatchResultModel>) {
        val homeMatchResultRVA = HomeMatchResultRVA(recentMatchResults)
        binding.rvHomeMatchResult.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvHomeMatchResult.setHasFixedSize(true)
        binding.rvHomeMatchResult.adapter = homeMatchResultRVA
    }

    private fun goMyPage(){
        binding.ivHomeMyPage.setOnSingleClickListener {
            startActivity(MyPageActivity.newIntent(requireContext()))
        }
    }

    private fun setUpService() {
        binding.tvHomePrivacyPolicy.setOnSingleClickListener {
            showDetailsDialog1()
        }
        binding.tvHomeTermsOfService.setOnSingleClickListener {
            showDetailsDialog2()
        }
    }

    private fun showDetailsDialog1() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_signup_tos_details_agree_1, null)

        val dialogBinding = DialogSignupTosDetailsAgree1Binding.bind(dialogView)

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
        }
    }

    private fun showDetailsDialog2() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_signup_tos_details_agree_2, null)

        val dialogBinding = DialogSignupTosDetailsAgree2Binding.bind(dialogView)

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
        }
    }
}
