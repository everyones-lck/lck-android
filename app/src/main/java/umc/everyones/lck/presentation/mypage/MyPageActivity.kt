// MyPageActivity.kt
package umc.everyones.lck.presentation.mypage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.presentation.MainActivity

@AndroidEntryPoint
class MyPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        // Setting up Navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_mypage) as? NavHostFragment
        val navController = navHostFragment?.navController

    }
}
