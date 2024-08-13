package umc.everyones.lck.presentation.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import umc.everyones.lck.R
import umc.everyones.lck.databinding.DialogProfileConfirmBinding
import umc.everyones.lck.databinding.FragmentSignupProfileBinding
import umc.everyones.lck.presentation.base.BaseFragment

@AndroidEntryPoint
class SignupProfileFragment : BaseFragment<FragmentSignupProfileBinding>(R.layout.fragment_signup_profile) {

    private val viewModel: SignupViewModel by activityViewModels()
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>
    private var profileImageUri: Uri? = null
    private val navigator by lazy { findNavController() }

    // 권한 요청 코드 정의
    private val REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 1001

    override fun initObserver() {
        viewModel.profileImageUri.observe(viewLifecycleOwner) { uri ->
            Log.d("SignupProfileFragment", "Observed Profile Image URI: $uri")
            uri?.let {
                binding.ivSignupProfilePicture.setImageURI(it)
            }
        }

        viewModel.nickname.observe(viewLifecycleOwner) { nickname ->
            Log.d("SignupProfileFragment", "Observed Nickname: $nickname")
            // nickname을 사용하여 UI를 업데이트하거나 필요한 로직을 추가합니다.
        }
    }

    override fun initView() {
        // UI 초기화 및 클릭 리스너 설정
        binding.ivSignupProfilePicture.setImageResource(android.R.color.transparent)

        pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    profileImageUri = uri
                    binding.ivSignupProfilePicture.setImageURI(uri)
                    viewModel.setProfileImageUri(uri)
                }
            }
        }

        binding.ivSignupProfilePlus.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_PERMISSION_READ_EXTERNAL_STORAGE)
            }
        }

        binding.ivSignupProfileNext.setOnClickListener {
            if (profileImageUri != null) {
                viewModel.addUser(profileImageUri.toString(), "default_team")
                navigateToSignupMyTeam()
            } else {
                showProfileDialog()
            }
        }

        binding.ivSignupProfilePicture.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }

    private fun showProfileDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_profile_confirm, null)
        val dialogBinding = DialogProfileConfirmBinding.bind(dialogView)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        dialogBinding.btnChange.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnConfirm.setOnClickListener {
            dialog.dismiss()
            viewModel.addUser("", "default_team")
            navigateToSignupMyTeam()
        }
    }

    private fun navigateToSignupMyTeam() {
        navigator.navigate(R.id.action_signupProfileFragment_to_signupMyteamFragment)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_READ_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            }
        }
    }
}