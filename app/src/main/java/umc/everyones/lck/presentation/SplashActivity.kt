package umc.everyones.lck.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import umc.everyones.lck.R
import umc.everyones.lck.util.LoginManager

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val loginManager = LoginManager(this)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = if (loginManager.isLoggedIn()) {
                Intent(this, MainActivity::class.java) // 로그인된 상태라면 홈 화면으로
            } else {
                Intent(this, LoginActivity::class.java) // 로그인되지 않은 상태라면 로그인 화면으로
            }
            startActivity(intent)
            finish()
        }, 2000) // 2초 후 전환
    }
}
