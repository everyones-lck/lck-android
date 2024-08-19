package umc.everyones.lck.presentation.party

import android.app.Activity
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentViewingPartyBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.mypage.MyPageActivity
import umc.everyones.lck.presentation.party.adapter.ViewingPartyRVA
import umc.everyones.lck.presentation.party.write.WriteViewingPartyActivity
import umc.everyones.lck.util.extension.repeatOnStarted
import umc.everyones.lck.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class ViewingPartyFragment : BaseFragment<FragmentViewingPartyBinding>(R.layout.fragment_viewing_party) {
    private var _viewIngPartyRVA: ViewingPartyRVA? = null
    private val viewingPartyRVA get() = _viewIngPartyRVA
    private val viewModel: ViewingPartyViewModel by activityViewModels()
    private val navigator by lazy {
        findNavController()
    }
    private var isWriteDone = false
    private var isRefreshed = false

    private var writeResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            isWriteDone = result.data?.getBooleanExtra("isWriteDone", false) ?: false
            if(isWriteDone){
                viewingPartyRVA?.refresh()
            }
        }
    }

    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            viewModel.viewingPartyListPage.collectLatest{ data ->
                viewingPartyRVA?.submitData(data)
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.isRefreshNeeded.collect { isRefreshNeeded ->
                if(isRefreshNeeded) {
                    viewingPartyRVA?.refresh()
                    viewModel.setIsRefreshNeeded(false)
                    isRefreshed = true
                }
            }
        }
    }

    override fun initView() {
        initViewingPartyRVAdapter()
        goToWriteViewingParty()
        goMyPage()
    }

    private fun goToWriteViewingParty(){
        binding.fabViewingPartyWrite.setOnSingleClickListener {
            writeResultLauncher.launch(WriteViewingPartyActivity.newIntent(requireContext()))
        }
    }

    private fun initViewingPartyRVAdapter(){
        _viewIngPartyRVA = ViewingPartyRVA { postId, shortLocation ->
            isRefreshed = false
            val action = ViewingPartyFragmentDirections.actionViewingPartyFragmentToReadViewingPartyFragment(postId, shortLocation ?: "")
            navigator.navigate(action)
        }

        viewingPartyRVA?.addLoadStateListener { combinedLoadStates ->
            with(binding){
                if(combinedLoadStates.source.refresh is LoadState.Loading){
                    binding.rvViewingParty.layoutManager?.scrollToPosition(0)
                }
                ivViewingPartyLoadingImg.isVisible = combinedLoadStates.source.refresh is LoadState.Loading
                rvViewingParty.isVisible = combinedLoadStates.source.refresh is LoadState.NotLoading
            }
        }

        binding.rvViewingParty.adapter = viewingPartyRVA
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewIngPartyRVA = null
    }

    private fun goMyPage(){
        binding.ivMyPage.setOnSingleClickListener {
            startActivity(MyPageActivity.newIntent(requireContext()))
        }
    }
}