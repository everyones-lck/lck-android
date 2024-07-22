package umc.everyones.lck.data

import android.net.Uri

class ProfileRepository {

    private var profileImageUri: Uri? = null

    fun setProfileImageUri(uri: Uri?) {
        profileImageUri = uri
    }

    fun getProfileImageUri(): Uri? {
        return profileImageUri
    }
}
