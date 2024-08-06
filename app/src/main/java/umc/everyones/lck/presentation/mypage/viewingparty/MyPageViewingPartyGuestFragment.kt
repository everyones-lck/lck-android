// MyPageViewingPartyGuestFragment.kt
package umc.everyones.lck.presentation.mypage.viewingparty

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentMypageViewingPartyGuestBinding
import umc.everyones.lck.presentation.base.BaseFragment

class MyPageViewingPartyGuestFragment : BaseFragment<FragmentMypageViewingPartyGuestBinding>(R.layout.fragment_mypage_viewing_party_guest) {

    private val viewModel: MyPageViewingPartyViewModel by viewModels()
    private lateinit var adapter: MyPageViewingPartyAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        initView()
        initObserver()
    }

    override fun initView() {
        adapter = MyPageViewingPartyAdapter(emptyList(), isHost = false) // isHost를 false로 설정
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@MyPageViewingPartyGuestFragment.adapter
        }
    }

    override fun initObserver() {
        viewModel.viewingPartyItems.observe(viewLifecycleOwner) { items ->
            adapter.updateItems(items)
        }
    }
}
