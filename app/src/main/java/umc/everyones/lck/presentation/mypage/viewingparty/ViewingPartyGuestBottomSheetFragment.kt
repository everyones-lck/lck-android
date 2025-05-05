package umc.everyones.lck.presentation.mypage.viewingparty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.databinding.FragmentViewingPartyGuestBottomSheetBinding

@AndroidEntryPoint
class ViewingPartyGuestBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentViewingPartyGuestBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MyPageViewingPartyViewModel by activityViewModels()
    private val args: ViewingPartyGuestBottomSheetFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewingPartyGuestBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTitle.text = args.title // 아이템 제목 설정

        binding.layoutViewingPartyCancel.setOnClickListener {
            deleteViewingParty(args.viewingPartyId)
            dismiss() // BottomSheetDialogFragment 닫기
        }
    }

    private fun deleteViewingParty(id: Long) {
        viewModel.cancleGuestViewingPartyMypage(id)
        Toast.makeText(requireContext(), "참여가 취소되었습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "ViewingPartyGuestBottomSheet"
        const val CATEGORY = "GUEST" // MyPageViewingPartyGuestFragment의 CATEGORY와 동일하게 유지
    }
}