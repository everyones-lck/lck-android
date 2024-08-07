package umc.everyones.lck.util

import android.content.Context
import android.content.SharedPreferences

class LoginManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "umc.everyones.lck.prefs"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_USER_TIER = "userTier"
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply()
    }

    fun getIsLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun clearLogin() {
        sharedPreferences.edit().remove(KEY_IS_LOGGED_IN).remove(KEY_USER_TIER).apply() // 티어 정보도 제거
    }

    // 사용자 티어 저장
    fun setUserTier(tier: String) {
        sharedPreferences.edit().putString(KEY_USER_TIER, tier).apply()
    }

    // 사용자 티어 불러오기
    fun getUserTier(): String? {
        return sharedPreferences.getString(KEY_USER_TIER, null)
    }
}
