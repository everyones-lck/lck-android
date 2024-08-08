package umc.everyones.lck.presentation.login

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import umc.everyones.lck.data.ProfileRepository
import javax.inject.Inject

@HiltViewModel
class SignupProfileViewModel @Inject constructor(
    application: Application,
    private val profileRepository: ProfileRepository
) : AndroidViewModel(application) {

    val profileImageUri = MutableLiveData<Uri?>()

    fun setProfileImageUri(uri: Uri?) {
        profileRepository.setProfileImageUri(uri)
        profileImageUri.value = uri
    }

    fun getProfileImageUri(): Uri? {
        return profileRepository.getProfileImageUri()
    }
}
