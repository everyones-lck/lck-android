package umc.everyones.lck.util

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class NicknameManager @Inject constructor(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("Nicknames", Context.MODE_PRIVATE)

    fun isNicknameDuplicate(nickname: String): Boolean {
        return sharedPreferences.contains(nickname)
    }

    fun addNickname(nickname: String) {
        sharedPreferences.edit().putString(nickname, nickname).apply()
    }

    fun clearAllNicknames() {
        sharedPreferences.edit().clear().apply()
    }
}
