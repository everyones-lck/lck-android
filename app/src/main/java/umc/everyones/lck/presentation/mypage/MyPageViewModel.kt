package umc.everyones.lck.presentation.mypage

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import umc.everyones.lck.User
import umc.everyones.lck.presentation.login.SignupViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user

    // 사용자 정보를 로드하는 함수
    fun loadUser(nickname: String?, signupViewModel: SignupViewModel) {
        viewModelScope.launch {
            if (nickname != null) {
                val currentUser = signupViewModel.getUser(nickname)
                _user.value = currentUser
                Log.d("MyPageViewModel", "Loaded user: $currentUser")
            } else {
                Log.d("MyPageViewModel", "Nickname is null")
                _user.value = null
            }
        }
    }
}
