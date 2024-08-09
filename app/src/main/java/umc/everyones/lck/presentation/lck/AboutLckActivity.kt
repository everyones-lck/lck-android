package umc.everyones.lck.presentation.lck

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R

@AndroidEntryPoint
class AboutLckActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_lck)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_about_lck) as NavHostFragment
        val navController = navHostFragment.navController
    }
}
