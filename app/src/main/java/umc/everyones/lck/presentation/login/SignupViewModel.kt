package umc.everyones.lck.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupViewModel : ViewModel() {

    private val _nicknameStatus = MutableLiveData<String?>()
    val nicknameStatus: LiveData<String?> get() = _nicknameStatus

    private val _isNicknameValid = MutableLiveData<Boolean>()
    val isNicknameValid: LiveData<Boolean> get() = _isNicknameValid

    fun checkNicknameValidity(nickname: String) {
        // 1. Check length constraints
        if (nickname.isEmpty()) {
            _nicknameStatus.value = "닉네임을 입력해주세요."
            _isNicknameValid.value = false
            return
        }
        if (nickname.length > 10) {
            _nicknameStatus.value = "닉네임은 최대 10글자까지 입력 가능합니다."
            _isNicknameValid.value = false
            return
        }

        // 2. Check for spaces
        if (nickname.contains(" ")) {
            _nicknameStatus.value = "닉네임 사이에는 공백을 입력할 수 없습니다."
            _isNicknameValid.value = false
            return
        }

        // 3. Check for duplication
        viewModelScope.launch {
            val isDuplicate = checkNicknameDuplication(nickname)
            if (isDuplicate) {
                _nicknameStatus.value = "다른 사용자와 중복되는 닉네임입니다."
                _isNicknameValid.value = false
            } else {
                _nicknameStatus.value = "사용 가능한 닉네임입니다."
                _isNicknameValid.value = true
            }
        }
    }

    private suspend fun checkNicknameDuplication(nickname: String): Boolean {
        return withContext(Dispatchers.IO) {
            // 서버에 닉네임 중복 체크 API 요청
            // 이 부분은 실제 API 호출로 대체해야 합니다.
            // 예시로 항상 false를 반환하게 하겠습니다.
            false
        }
    }
}
