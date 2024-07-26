package umc.everyones.lck.presentation.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.DialogProfileConfirmBinding
import umc.everyones.lck.databinding.FragmentSignupProfileBinding
import umc.everyones.lck.presentation.base.BaseFragment

@AndroidEntryPoint
class SignupProfileFragment : BaseFragment<FragmentSignupProfileBinding>(R.layout.fragment_signup_profile) {

    private val viewModel: SignupProfileViewModel by viewModels()
    private val REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 2
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>
    private var profileImageUri: Uri? = null
    private val args: SignupProfileFragmentArgs by navArgs()

    override fun initObserver() {
        viewModel.profileImageUri.observe(viewLifecycleOwner) { uri ->
            uri?.let {
                binding.ivSignupProfilePicture.setImageURI(it)
            }
        }
    }

    override fun initView() {
        val nickname = args.nickname

        binding.ivSignupProfilePicture.setImageResource(android.R.color.transparent)

        pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    profileImageUri = uri
                    binding.ivSignupProfilePicture.setImageURI(uri)
                }
            }
        }

        // 프로필 사진 추가 버튼 클릭 리스너
        binding.ivSignupProfilePlus.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_PERMISSION_READ_EXTERNAL_STORAGE)
            }
        }

        // 다음 버튼 클릭 리스너
        binding.ivSignupProfileNext.setOnClickListener {
            if (profileImageUri != null) {
                navigateToSignupMyTeam(nickname)
            } else {
                showProfileDialog(nickname)
            }
        }

        // 갤러리 열기 버튼 클릭 리스너
        binding.ivSignupProfilePicture.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }

    private fun showProfileDialog(nickname: String) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_profile_confirm, null)
        val dialogBinding = DialogProfileConfirmBinding.bind(dialogView)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        val layoutParams = dialog.window?.attributes
        layoutParams?.dimAmount = 0.8f
        dialog.window?.attributes = layoutParams

        dialogBinding.btnChange.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnConfirm.setOnClickListener {
            dialog.dismiss()
            navigateToSignupMyTeam(nickname)
        }
    }

    private fun navigateToSignupMyTeam(nickname: String) {
        val action = SignupProfileFragmentDirections
            .actionSignupProfileFragmentToSignupMyteamFragment(
                nickname,
                profileImageUri?.toString() ?: "",  // 빈 문자열을 기본값으로 사용
                "default_team"  // 기본값을 사용하거나, 적절한 값을 설정
            )
        findNavController().navigate(action)
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
