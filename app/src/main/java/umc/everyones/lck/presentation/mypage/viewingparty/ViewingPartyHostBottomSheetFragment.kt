package umc.everyones.lck.presentation.mypage.viewingparty

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import umc.everyones.lck.databinding.FragmentViewingPartyHostBottomSheetBinding
import umc.everyones.lck.domain.model.request.party.WriteViewingPartyModel
import umc.everyones.lck.presentation.party.read.ReadViewingPartyViewModel
import umc.everyones.lck.presentation.party.write.WriteViewingPartyActivity
import umc.everyones.lck.util.extension.toWriteViewingPartyDateFormat
import umc.everyones.lck.util.network.UiState

@AndroidEntryPoint
class ViewingPartyHostBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentViewingPartyHostBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val readViewModel : ReadViewingPartyViewModel by activityViewModels()
    private val viewModel: MyPageViewingPartyViewModel by activityViewModels()
    private val args: ViewingPartyHostBottomSheetFragmentArgs by navArgs()
    private var _myViewingPartyHostRVA : MyViewingPartyHostRVA?=null
    private val myViewingPartyHostRVA get() = _myViewingPartyHostRVA

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewingPartyHostBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var readResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (result.data?.getBooleanExtra("isReadMenuDone", false) == true) {
                myViewingPartyHostRVA?.refresh() // Refresh the list after reading a post
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTitle.text = args.title // 아이템 제목 설정

        binding.layoutViewingPartyEdit.setOnClickListener {
            readViewModel.fetchViewingParty(args.viewingPartyId)
            viewLifecycleOwner.lifecycleScope.launch {
                readViewModel.readViewingPartyEvent.collect { state ->
                    when (state) {
                        is UiState.Success -> {
                            val viewingParty = readViewModel.viewingParty
                            if (viewingParty != null) {
                                val intent = WriteViewingPartyActivity.editIntent(
                                    requireContext(),
                                    args.viewingPartyId,
                                    WriteViewingPartyModel(
                                        name = viewingParty.name,
                                        date = viewingParty.partyDate.toWriteViewingPartyDateFormat(),
                                        latitude = viewingParty.latitude,
                                        longitude = viewingParty.longitude,
                                        price = viewingParty.price.replace("₩", "").trim(),
                                        lowParticipate = viewingParty.participants.split("-")[0].trim(),
                                        highParticipate = viewingParty.participants.split("-")[1].replace(("[^\\d]").toRegex(), ""),
                                        qualify = viewingParty.qualify.replace("To.", ""),
                                        etc = viewingParty.etc,
                                        location = viewingParty.place,
                                        shortLocation = ""
                                    )
                                )
                                readResultLauncher?.launch(intent)
                                dismiss()
                            } else {
                                Toast.makeText(requireContext(), "데이터를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
                        is UiState.Failure -> {
                            Toast.makeText(requireContext(), "뷰잉파티를 조회하지 못했습니다.", Toast.LENGTH_SHORT).show()
                        }
                        is UiState.Loading -> {
                            // 로딩 상태 처리
                        }
                        is UiState.Empty -> {
                            Toast.makeText(requireContext(), "데이터가 없습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }


        binding.layoutViewingPartyCancel.setOnClickListener {
            cancelViewingParty(args.viewingPartyId)
            dismiss() // BottomSheetDialogFragment 닫기
        }
    }

    private fun cancelViewingParty(id: Long) {
        viewModel.cancleHostViewingPartyMypage(id)
        Toast.makeText(requireContext(), "개최가 취소되었습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "ViewingPartyHostBottomSheet"
        const val CATEGORY = "Host" // MyPageViewingPartyGuestFragment의 CATEGORY와 동일하게 유지
    }
}