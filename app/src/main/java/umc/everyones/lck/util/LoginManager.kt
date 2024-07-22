package umc.everyones.lck.util

import android.content.Context
import android.content.SharedPreferences

class LoginManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "umc.everyones.lck.prefs"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun clearLogin() {
        sharedPreferences.edit().remove(KEY_IS_LOGGED_IN).apply()
    }
}
