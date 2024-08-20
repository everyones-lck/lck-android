package umc.everyones.lck.presentation.login

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.everyones.lck.R
import umc.everyones.lck.data.SignupUserData
import umc.everyones.lck.databinding.FragmentSignupSuccessBinding
import umc.everyones.lck.domain.model.user.UserItem
import umc.everyones.lck.presentation.MainActivity
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.util.TeamData
import java.io.ByteArrayOutputStream
import java.io.File

@AndroidEntryPoint
class SignupSuccessFragment : BaseFragment<FragmentSignupSuccessBinding>(R.layout.fragment_signup_success) {

    private val viewModel: SignupViewModel by activityViewModels()

    override fun initObserver() {

    }

    override fun initView() {
        // Next 버튼 클릭 처리
        binding.ivSignupSuccessNext.setOnClickListener {
            Intent(requireContext(), MainActivity::class.java).apply {
                startActivity(this)
            }
            requireActivity().finish()
        }
    }
}