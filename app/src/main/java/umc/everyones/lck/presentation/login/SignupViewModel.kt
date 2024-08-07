package umc.everyones.lck.presentation.login

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import umc.everyones.lck.User
import umc.everyones.lck.UserDao
import umc.everyones.lck.util.NicknameManager
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    application: Application,
    private val userDao: UserDao,
    private val nicknameManager: NicknameManager
) : AndroidViewModel(application) {

    private val _profileImageUri = MutableLiveData<Uri?>()
    val profileImageUri: LiveData<Uri?> get() = _profileImageUri

    private val _nickname = MutableLiveData<String?>()
    val nickname: LiveData<String?> get() = _nickname

    fun setProfileImageUri(uri: Uri?) {
        _profileImageUri.value = uri
        Log.d("SignupViewModel", "Profile Image URI set to: $uri")
    }

    fun setNickname(nick: String) {
        _nickname.value = nick
        Log.d("SignupViewModel", "setNickname called with: $nick")
        Log.d("SignupViewModel", "Current nickname LiveData value: ${_nickname.value}")
    }

    fun addUser(profileImageUri: String, team: String, tier: String = "Bronze") {
        val nick = _nickname.value
        if (nick != null) {
            viewModelScope.launch {
                try {
                    val user = User(
                        name = nick,
                        profileUri = profileImageUri,
                        team = team,
                        tier = tier
                    )
                    userDao.insertUser(user)
                    Log.d("SignupViewModel", "User added successfully: $user")
                } catch (e: Exception) {
                    Log.e("SignupViewModel", "Error adding user", e)
                }
            }
        } else {
            Log.e("SignupViewModel", "Nickname is null")
        }
    }

    suspend fun getUser(nickname: String): User? {
        return userDao.getUserByNickname(nickname)
    }
}