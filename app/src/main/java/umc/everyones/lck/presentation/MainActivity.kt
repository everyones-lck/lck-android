package umc.everyones.lck.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import umc.everyones.lck.R
import umc.everyones.lck.databinding.ActivityMainBinding
import umc.everyones.lck.presentation.base.BaseActivity
import umc.everyones.lck.presentation.login.LoginActivity
import umc.everyones.lck.presentation.mypage.MyPageFragment
import umc.everyones.lck.util.LoginManager
import umc.everyones.lck.presentation.community.WritePostViewModel
import umc.everyones.lck.presentation.home.HomeViewModel
import umc.everyones.lck.util.extension.repeatOnStarted

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navController: NavController
    private val homeViewModel: HomeViewModel by viewModels()
    private val writePostViewModel: WritePostViewModel by viewModels()
    override fun initView() {
        initNavigator()
    }

    override fun initObserver() {
        repeatOnStarted {
            homeViewModel.navigateEvent.collect{
                binding.mainBnv.selectedItemId = it
            }
        }
    }

    private fun initNavigator() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.mainBnv.setupWithNavController(navController)
    }
}
