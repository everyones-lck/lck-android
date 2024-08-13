package umc.everyones.lck.presentation.login

import android.app.Application
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import umc.everyones.lck.domain.model.user.UserItem
import umc.everyones.lck.util.NicknameManager
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    spf: SharedPreferences,
    application: Application,
    private val nicknameManager: NicknameManager
) : AndroidViewModel(application) {

    private val _profileImageUri = MutableLiveData<Uri?>()
    val profileImageUri: LiveData<Uri?> get() = _profileImageUri

    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> get() = _nickname

    private val users = mutableListOf<UserItem>()

    private val _kakaoUserId = MutableStateFlow<String>("")
    val kakaoUserId = _kakaoUserId.asStateFlow()

    private val _teamId = MutableStateFlow<String>("")
    val teamId = _teamId.asStateFlow()

    fun setProfileImageUri(uri: Uri?) {
        _profileImageUri.value = uri
        Log.d("SignupViewModel", "Profile Image URI set to: $uri")
    }

    fun setNickname(nickname: String) {
        _nickname.value = nickname
        Log.d("SignupViewModel", "Nickname set to: $nickname")
    }

    fun setKakaoUserId(kakaoUserId: String){
        _kakaoUserId.value = kakaoUserId
    }

    fun addUser(profileImageUri: String, team: String, tier: String = "Bronze") {
        val nick = _nickname.value
        val userId = _kakaoUserId.value
        if (nick != null) {
            viewModelScope.launch {
                try {
                    if (nicknameManager.isNicknameDuplicate(nick)) {
                        Log.e("SignupViewModel", "Nickname already exists")
                    } else {
                        val user = UserItem(
                            kakaoUserId = userId,
                            nickname = nick,
                            profileUri = profileImageUri,
                            teamId = team,
                            tier = tier
                        )
                        users.add(user)
                        nicknameManager.addNickname(nick)
                        Log.d("SignupViewModel", "User added: $user")
                    }
                } catch (e: Exception) {
                    Log.e("SignupViewModel", "Error adding user", e)
                }
            }
        } else {
            Log.e("SignupViewModel", "Nickname is null")
        }
    }

    suspend fun getUser(nickname: String): UserItem? {
        return users.find { it.nickname == nickname }
    }

    fun getCurrentUser(): UserItem? {
        val currentNickname = _nickname.value
        Log.d("SignupViewModel", "Getting user for nickname: $currentNickname")
        return if (currentNickname != null) {
            users.find { it.nickname == currentNickname }
        } else {
            null
        }
    }
}