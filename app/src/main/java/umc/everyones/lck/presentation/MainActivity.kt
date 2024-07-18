package umc.everyones.lck.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import umc.everyones.lck.R
import umc.everyones.lck.databinding.ActivityMainBinding
import umc.everyones.lck.presentation.base.BaseActivity
import umc.everyones.lck.presentation.community.WritePostViewModel

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var navController: NavController
    private val writePostViewModel: WritePostViewModel by viewModels()
    override fun initView() {
        initNavigator()
    }

    override fun initObserver() {

    }

    private fun initNavigator() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.mainBnv.setupWithNavController(navController)
        navController.addOnDestinationChangedListener{ _, destination, _ ->
            if(destination.id in bnvInvisibleDestinationList){
                lifecycleScope.launch {
                    //delay(100)
                    binding.mainBnv.visibility=  View.GONE
                }
            } else {
                lifecycleScope.launch {
                    //delay(100)
                    binding.mainBnv.visibility=  View.VISIBLE
                }
            }
        }
    }

    companion object {
        val bnvInvisibleDestinationList = listOf(R.id.writePostFragment, R.id.readPostFragment)
    }
}