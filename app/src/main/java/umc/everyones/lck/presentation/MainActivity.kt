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
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import umc.everyones.lck.R
import umc.everyones.lck.databinding.ActivityMainBinding
import umc.everyones.lck.presentation.base.BaseActivity
import umc.everyones.lck.presentation.login.LoginActivity
import umc.everyones.lck.presentation.mypage.MyPageFragment
import umc.everyones.lck.util.LoginManager
import umc.everyones.lck.presentation.community.WritePostViewModel

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navController: NavController
    private val writePostViewModel: WritePostViewModel by viewModels()
    override fun initView() {
        initNavigator()
        setupLogoutButton()
        setupMypageButton()
    }

    override fun initObserver() {
        // Implement if needed
    }

    private fun initNavigator() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.mainBnv.setupWithNavController(navController)
    }

    /*임시 로그아웃 구현이라 삭제 부탁드립니다*/
    private fun setupLogoutButton() {
        binding.btnLogout.setOnClickListener {
            val loginManager = LoginManager(this)
            loginManager.clearLogin()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    /*임시 마이페이지 이동 구현이라 삭제 부탁드립니다*/
    private fun setupMypageButton() {
        binding.btnmypage.setOnClickListener{
            val intent = Intent(this, MyPageFragment::class.java)
            startActivity(intent)
        }
    }
}
