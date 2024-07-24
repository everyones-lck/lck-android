package umc.everyones.lck.presentation.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.DialogProfileConfirmBinding
import umc.everyones.lck.databinding.FragmentSignupProfileBinding

@AndroidEntryPoint
class SignupProfileFragment : Fragment(R.layout.fragment_signup_profile) {

    private var _binding: FragmentSignupProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SignupProfileViewModel

    private val REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 2
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>
    private var profileImageUri: Uri? = null
    private var nickname: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignupProfileBinding.bind(view)
        viewModel = ViewModelProvider(this).get(SignupProfileViewModel::class.java)

        nickname = arguments?.getString("nickname")

        // Set default empty image initially
        binding.ivSignupProfilePicture.setImageResource(android.R.color.transparent)

        // Initialize activity result launcher for picking image
        pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    profileImageUri = uri
                    viewModel.setProfileImageUri(uri)
                    binding.ivSignupProfilePicture.setImageURI(uri)
                }
            }
        }

        // Observe profile image URI changes
        viewModel.profileImageUri.observe(viewLifecycleOwner) { uri ->
            uri?.let {
                binding.ivSignupProfilePicture.setImageURI(it)
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
                navigateToSignupMyTeam()
            } else {
                showProfileDialog()
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

    private fun showProfileDialog() {
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
            navigateToSignupMyTeam()
        }
    }

    private fun navigateToSignupMyTeam() {
        val bundle = Bundle().apply {
            putString("nickname", nickname)
            profileImageUri?.let {
                putString("profileImageUri", it.toString())
            }
        }
        findNavController().navigate(R.id.action_signupProfileFragment_to_signupMyteamFragment, bundle)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_READ_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
