package umc.everyones.lck.presentation

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import umc.everyones.lck.data.ProfileRepository

class SignupProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val profileRepository = ProfileRepository()
    val profileImageUri = MutableLiveData<Uri?>()

    fun setProfileImageUri(uri: Uri?) {
        profileRepository.setProfileImageUri(uri)
        profileImageUri.value = uri
    }

    fun getProfileImageUri(): Uri? {
        return profileRepository.getProfileImageUri()
    }
}
