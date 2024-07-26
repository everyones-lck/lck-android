package umc.everyones.lck.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentHomeBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.login.LoginActivity
import umc.everyones.lck.presentation.mypage.MyPageActivity
import umc.everyones.lck.util.LoginManager

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun initObserver() {

    }

    override fun initView() {
        setupLogoutButton()
        setupMypageButton()
    }

    private fun setupLogoutButton() {
        binding.btnLogout.setOnClickListener {
            // Use the Fragment's context for LoginManager
            val loginManager = LoginManager(requireContext())
            loginManager.clearLogin()

            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish() // Finish the current activity
        }
    }

    private fun setupMypageButton() {
        binding.btnmypage.setOnClickListener {

            val intent = Intent(requireContext(), MyPageActivity::class.java)
            startActivity(intent)
            requireActivity().finish() // Finish the current activity
        }
    }
}
