package umc.everyones.lck.presentation.party

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentViewingPartyBinding
import umc.everyones.lck.domain.model.party.ViewingPartyItem
import umc.everyones.lck.domain.model.response.party.ViewingPartyListModel
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

    private var writeResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            isWriteDone = result.data?.getBooleanExtra("isWriteDone", false) ?: false
        }
    }

    override fun initObserver() {
        repeatOnStarted {
            viewModel.fetchViewingPartyListPage().collectLatest{ data ->
                viewingPartyRVA?.submitData(data)
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
            val action = ViewingPartyFragmentDirections.actionViewingPartyFragmentToReadViewingPartyFragment(postId, shortLocation ?: "")
            navigator.navigate(action)
        }
        binding.rvViewingParty.adapter = viewingPartyRVA
        viewingPartyRVA?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    if (isWriteDone) {
                        binding.rvViewingParty.layoutManager?.scrollToPosition(0)
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewingPartyRVA?.refresh()
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