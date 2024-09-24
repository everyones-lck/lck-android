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
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import umc.everyones.lck.R
import umc.everyones.lck.databinding.DialogMyteamConfirmBinding
import umc.everyones.lck.databinding.DialogProfileConfirmBinding
import umc.everyones.lck.databinding.FragmentSignupProfileBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class SignupProfileFragment : BaseFragment<FragmentSignupProfileBinding>(R.layout.fragment_signup_profile) {

    private val viewModel: SignupViewModel by activityViewModels()
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>
    private var profileImageUri: Uri? = null
    private val navigator by lazy { findNavController() }

    // 권한 요청 코드 정의
    private val REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 1001

    override fun initObserver() {
        viewModel.profileUri.observe(viewLifecycleOwner) { uri ->
            Log.d("SignupProfileFragment", "Observed Profile Image URI: $uri")
            uri?.let {
                binding.ivSignupProfilePicture.setImageURI(it)
            }
        }

        viewModel.nickName.observe(viewLifecycleOwner) { nickname ->
            Log.d("SignupProfileFragment", "닉네임: $nickname")
        }
    }
    override fun initView() {
        // UI 초기화 및 클릭 리스너 설정
        binding.ivSignupProfilePicture.setImageResource(android.R.color.transparent)

        // ActivityResultLauncher 설정
        pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    profileImageUri = uri
                    binding.ivSignupProfilePicture.setImageURI(uri) // 선택한 이미지 미리보기
                    viewModel.setProfileImageUri(uri) // ViewModel에 URI 저장
                }
            }
        }

        // 프로필 이미지 추가 버튼 클릭 리스너
        binding.ivSignupProfilePlus.setOnSingleClickListener {
            // 외부 저장소 읽기 권한 확인
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery() // 갤러리 열기
            } else {
                // 권한 요청
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_PERMISSION_READ_EXTERNAL_STORAGE)
            }
        }

        // 다음 버튼 클릭 리스너
        binding.ivSignupProfileNext.setOnSingleClickListener {
            if (profileImageUri != null) {
                navigateToSignupMyTeam() // 다음 화면으로 이동
            } else {
                showProfileConfirmDialog()
            }
        }

        // 프로필 이미지 클릭 리스너 (이미지 변경)
        binding.ivSignupProfilePicture.setOnSingleClickListener {
            openGallery() // 갤러리 열기
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        pickImageLauncher.launch(intent) // 갤러리 열기
    }

    private fun showProfileConfirmDialog() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_profile_confirm, null)
        val dialogBinding = DialogProfileConfirmBinding.bind(dialogView)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        val layoutParams = dialog.window?.attributes
        layoutParams?.dimAmount = 0.8f
        dialog.window?.attributes = layoutParams

        dialogBinding.btnChange.setOnSingleClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnConfirm.setOnSingleClickListener {
            dialog.dismiss()
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
                openGallery() // 권한이 승인되면 갤러리 열기
            } else {
                Toast.makeText(requireContext(), "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}