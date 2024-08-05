package umc.everyones.lck.presentation.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentMypageViewingPartyBinding
import umc.everyones.lck.presentation.base.BaseFragment

class MyPageViewingPartyFragment : BaseFragment<FragmentMypageViewingPartyBinding>(R.layout.fragment_mypage_viewing_party) {

    private val viewModel: MyPageViewingPartyViewModel by viewModels()

    override fun initView() {
        setupViewPager()
        setupTabLayout()

        binding.ivMypageViewingPartyBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun initObserver() {
        // ViewModel 관찰 코드가 필요할 경우 추가
    }

    private fun setupViewPager() {
        val adapter = MyPageViewingPartyPagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
    }

    private fun setupTabLayout() {
        TabLayoutMediator(binding.tabMypageViewingPartyGuestHost, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Guest"
                1 -> "Host"
                else -> throw IllegalStateException("Unexpected position: $position")
            }
        }.attach()
    }
}
