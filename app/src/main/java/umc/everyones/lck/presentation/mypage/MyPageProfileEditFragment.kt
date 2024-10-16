package umc.everyones.lck.presentation.mypage

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import umc.everyones.lck.R
import umc.everyones.lck.databinding.DialogMypageProfileLogoutBinding
import umc.everyones.lck.databinding.DialogNicknameConfirmBinding
import umc.everyones.lck.databinding.DialogProfileEditConfirmBinding
import umc.everyones.lck.databinding.FragmentMypageProfileEditBinding
import umc.everyones.lck.presentation.MainActivity
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.home.HomeFragment
import umc.everyones.lck.presentation.login.LoginActivity
import umc.everyones.lck.presentation.login.SignupViewModel
import umc.everyones.lck.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class MyPageProfileEditFragment : BaseFragment<FragmentMypageProfileEditBinding>(R.layout.fragment_mypage_profile_edit){

    private val myPageViewModel: MyPageViewModel by activityViewModels()
    private val signupViewModel: SignupViewModel by activityViewModels()
    private val navigator by lazy { findNavController() }

    override fun onResume() {
        super.onResume()
        setInitialState() // Fragment가 다시 보일 때 초기 상태 설정
    }

    override fun initObserver() {

        setInitialState()

        signupViewModel.isNicknameAvailable.observe(viewLifecycleOwner) { isAvailable ->
            if (isAvailable) {
                binding.viewMypageProfileEditNicknameBar.setBackgroundResource(R.drawable.shape_rect_4_green_line)
                binding.tvMypageNicknameDuplication.setTextColor(requireContext().getColor(R.color.success))
                binding.tvMypageNicknameDuplication.setBackgroundResource(R.drawable.shape_rect_12_green_line)
                binding.layoutMypageProfileEditValid.visibility = View.VISIBLE
                binding.layoutMypageProfileEditWarning4.visibility = View.GONE
            } else {
                binding.layoutMypageProfileEditWarning4.visibility = View.VISIBLE // 중복
                binding.viewMypageProfileEditNicknameBar.setBackgroundResource(R.drawable.shape_rect_4_red_line)
                binding.tvMypageNicknameDuplication.setTextColor(requireContext().getColor(R.color.warning))
                binding.tvMypageNicknameDuplication.setBackgroundResource(R.drawable.shape_rect_12_red_line)
            }
        }

        myPageViewModel.updateProfileResult.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                Timber.d("프로필 수정 성공")
            } else {
                Timber.e("프로필 수정 실패")
            }
        }
        myPageViewModel.profileData.observe(viewLifecycleOwner) { profile ->
            profile?.let {
                loadProfileImage(it.profileImageUrl)
            }
        }
    }

    override fun initView() {
        setInitialState() // 초기 상태 설정

        // 기존 닉네임을 가져와 hint로 설정
        myPageViewModel.nickName.observe(viewLifecycleOwner) { nickName ->
            binding.etMypageProfileEditNicknameName.hint = nickName
        }

        // 뒤로가기 버튼 클릭 시 이동
        binding.ivMypageProfileEditBack.setOnSingleClickListener {
            findNavController().navigateUp()
        }

        // 기본 이미지 사용 클릭 시
        binding.tvMypageProfileEditBasic.setOnSingleClickListener {
            binding.ivMypageProfileEditProfile.setImageResource(R.drawable.img_signup_profile)
            myPageViewModel.setProfileImageUri(Uri.parse("android.resource://${requireContext().packageName}/${R.drawable.img_signup_profile}")) // 기본 이미지 URI 설정
        }

        // 갤러리에서 이미지 선택 클릭 시
        binding.ivMypageProfileEditProfile.setOnSingleClickListener {
            openGallery() // 갤러리 열기
        }

        binding.etMypageProfileEditNicknameName.doOnTextChanged { text, _, _, _ ->
            val nickname = text.toString()
            val isDuplicated = false // 여기에 실제 중복 확인 로직을 추가해야 합니다.
            val isValid = validateNickname(nickname) // 유효성 검사 추가

            if (isValid) {
                // 유효한 닉네임일 경우
                if (!isDuplicated) {
                    binding.tvMypageNicknameDuplication.setTextColor(requireContext().getColor(R.color.white)) // 기본 색상
                    binding.tvMypageNicknameDuplication.setBackgroundResource(R.drawable.shape_rect_12_white_line) // 기본 배경
                    binding.tvMypageNicknameDuplication.setOnClickListener {
                        signupViewModel.checkNicknameAvailability(nickname) // 중복 확인 로직 호출
                    }
                    binding.tvMypageNicknameDuplication.isEnabled = true // 버튼 활성화
                } else {
                    // 중복된 경우
                    setNicknameUnavailableState() // 중복 상태 설정
                    binding.layoutMypageProfileEditWarning4.visibility = View.GONE // 중복 아님
                    binding.tvMypageNicknameDuplication.isEnabled = false // 버튼 비활성화
                }
            } else {
                // 유효하지 않은 경우
                setNicknameUnavailableState() // 닉네임이 비어 있을 때 상태 설정
                binding.tvMypageNicknameDuplication.isEnabled = false // 버튼 비활성화
            }
        }

        binding.tvMypageProfileEditTopbarEdit.setOnSingleClickListener {
            val nicknameInput = binding.etMypageProfileEditNicknameName.text.toString().trim()

            // 현재 프로필 이미지 URI 가져오기
            val currentProfileImageUri = myPageViewModel.profileUri.value

            // 업데이트할 닉네임 결정 (닉네임이 비어있으면 null로 설정)
            val finalNickname = if (nicknameInput.isNotEmpty()) {
                nicknameInput // 닉네임이 비어있지 않으면 입력값 사용
            } else {
                null // 닉네임이 비어있으면 null로 설정
            }

            // 프로필 이미지 결정
            val finalProfileImageUri = if (binding.ivMypageProfileEditProfile.drawable != null) {
                // 프로필 이미지가 변경된 경우
                val bitmap = (binding.ivMypageProfileEditProfile.drawable as BitmapDrawable).bitmap // Bitmap으로 변환
                val path = MediaStore.Images.Media.insertImage(requireContext().contentResolver, bitmap, "ProfileImage", null) // URI로 변환
                Uri.parse(path) // URI로 변환하여 반환
            } else {
                // 프로필 이미지가 변경되지 않은 경우 현재 프로필 이미지를 유지
                currentProfileImageUri
            }

            // 업데이트할 데이터가 유효할 경우 ViewModel 호출
            myPageViewModel.updateProfile(finalNickname, finalProfileImageUri)

            showConfirmDialog()
        }
    }

    private fun showConfirmDialog() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_profile_edit_confirm, null)
        val dialogBinding = DialogProfileEditConfirmBinding.bind(dialogView)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        val layoutParams = dialog.window?.attributes
        layoutParams?.dimAmount = 0.8f
        dialog.window?.attributes = layoutParams

        dialogBinding.btnConfirm.setOnClickListener {
            dialog.dismiss()
            navigator.navigate(R.id.action_myPageProfileEditFragment_to_myPageProfileFragment)
        }
    }


    private fun setInitialState() {
        binding.tvMypageNicknameDuplication.setTextColor(requireContext().getColor(R.color.nickname_gray)) // 회색
        binding.tvMypageNicknameDuplication.setBackgroundResource(R.drawable.shape_rect_12_gray_line) // 회색 배경
        binding.layoutMypageProfileEditWarning4.visibility = View.GONE // 초기 경고 숨기기
    }

    private fun setNicknameUnavailableState() {
        binding.tvMypageNicknameDuplication.setTextColor(requireContext().getColor(R.color.nickname_gray)) // 회색
        binding.tvMypageNicknameDuplication.setBackgroundResource(R.drawable.shape_rect_12_gray_line) // 회색 배경
    }

    private fun validateNickname(nickname: String): Boolean {
        var isValid = true
        // 닉네임 유효성 검사
        if (nickname.isEmpty()) {
            binding.layoutMypageProfileEditWarning1.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.layoutMypageProfileEditWarning1.visibility = View.GONE
        }
        if (nickname.length > 10) {
            binding.layoutMypageProfileEditWarning2.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.layoutMypageProfileEditWarning2.visibility = View.GONE
        }
        if (nickname.contains(" ")) {
            binding.layoutMypageProfileEditWarning3.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.layoutMypageProfileEditWarning3.visibility = View.GONE
        }
        return isValid
    }

    private fun loadProfileImage(uri: String?) {
        uri?.let {
            Glide. with(this)
                .load(it)
                .placeholder(R.drawable.img_signup_profile) // 기본 이미지
                .into(binding.ivMypageProfileEditProfile) // 프로필 이미지 뷰에 로드
        } ?: run {
            binding.ivMypageProfileEditProfile.setImageResource(R.drawable.img_signup_profile) // 기본 이미지 설정
        }
    }

    private fun openGallery() {
        // 갤러리에서 이미지 선택하기 위한 Intent
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        startActivityForResult(intent, GALLERY_REQUEST_CODE) // GALLERY_REQUEST_CODE는 상수로 정의해야 합니다.
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            data?.data?.let { uri ->
                binding.ivMypageProfileEditProfile.setImageURI(uri) // 선택한 이미지 미리보기
                myPageViewModel.setProfileImageUri(uri) // ViewModel에 URI 저장
            }
        }
    }

    companion object {
        private const val GALLERY_REQUEST_CODE = 1001
    }
}
