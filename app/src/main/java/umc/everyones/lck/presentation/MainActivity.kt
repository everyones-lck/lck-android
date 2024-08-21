package umc.everyones.lck.presentation

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.ActivityMainBinding
import umc.everyones.lck.presentation.base.BaseActivity
import umc.everyones.lck.presentation.community.write.WritePostViewModel
import umc.everyones.lck.presentation.home.HomeViewModel
import umc.everyones.lck.presentation.party.ViewingPartyViewModel
import umc.everyones.lck.util.extension.repeatOnStarted
import umc.everyones.lck.util.extension.setupWithNavControllerCustom

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navController: NavController
    private val homeViewModel: HomeViewModel by viewModels()
    private val writePostViewModel: WritePostViewModel by viewModels()
    private val viewingPartyViewModel: ViewingPartyViewModel by viewModels()
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
        binding.mainBnv.setupWithNavControllerCustom(navController){
            if(it.itemId == R.id.viewingPartyTab){
                viewingPartyViewModel.setIsRefreshNeeded(true)
            }
            true
        }
    }

    companion object {
        fun writePartyDoneIntent(context: Context, isWriteDone: Boolean) =
            Intent(context, MainActivity::class.java).apply {
                putExtra("isWriteDone", isWriteDone)
            }

        fun writePostDoneIntent(context: Context, isWriteDone: Boolean, category: String) =
            Intent(context, MainActivity::class.java).apply {
                putExtra("isWriteDone", isWriteDone)
                putExtra("category", category)
            }

        fun readMenuDoneIntent(context: Context, isReadMenuDone: Boolean) =
            Intent(context, MainActivity::class.java).apply {
                putExtra("isReadMenuDone", isReadMenuDone)
            }
    }
}
