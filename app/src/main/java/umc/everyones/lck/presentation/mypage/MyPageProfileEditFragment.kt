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

    private val viewModel: MyPageViewModel by activityViewModels()
    private val signupViewModel: SignupViewModel by activityViewModels()
    private val navigator by lazy { findNavController() }
    private var profileImageUri: Uri? = null

    private val photoPickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            result.data?.data?.let { uri ->
                profileImageUri = uri
                binding.ivMypageProfileEditProfile.setImageURI(uri) // 선택한 이미지 미리보기
                viewModel.setProfileImageUri(uri) // ViewModel에 URI 저장
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setInitialState() // Fragment가 다시 보일 때 초기 상태 설정
    }

    override fun initObserver() {

        setInitialState()

        signupViewModel.isNicknameAvailable.observe(viewLifecycleOwner) { isAvailable ->
            if (isAvailable) {
                binding.etMypageProfileEditNicknameName.setBackgroundResource(R.drawable.shape_rect_4_green_line)
                binding.etMypageProfileEditNicknameName.setTextColor(requireContext().getColor(R.color.success))
                binding.layoutMypageProfileEditValid.visibility = View.VISIBLE
                binding.layoutMypageProfileEditWarning4.visibility = View.GONE
            } else {
                binding.layoutMypageProfileEditWarning4.visibility = View.VISIBLE // 중복
                binding.etMypageProfileEditNicknameName.setBackgroundResource(R.drawable.shape_rect_4_red_line)
                binding.etMypageProfileEditNicknameName.setTextColor(requireContext().getColor(R.color.warning))
            }
        }

        viewModel.updateProfileResult.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                Timber.d("프로필 수정 성공")
            } else {
                Timber.e("프로필 수정 실패")
            }
        }
        viewModel.profileData.observe(viewLifecycleOwner) { profile ->
            profile?.let {
                loadProfileImage(it.profileImageUrl)
            }
        }
    }

    override fun initView() {
        setInitialState() // 초기 상태 설정

        // 뒤로가기 버튼 클릭 시 이동
        binding.ivMypageProfileEditBack.setOnSingleClickListener {
            findNavController().navigateUp()
        }

        // 기본 이미지 사용 클릭 시
        binding.tvMypageProfileEditBasic.setOnSingleClickListener {
            binding.ivMypageProfileEditProfile.setImageResource(R.drawable.img_signup_profile)
            viewModel.setProfileImageUri(Uri.parse("android.resource://${requireContext().packageName}/${R.drawable.img_signup_profile}")) // 기본 이미지 URI 설정
        }

        binding.ivMypageProfileEditProfile.setOnSingleClickListener {
            openPhotoPicker() // 갤러리 열기
        }

        binding.etMypageProfileEditNicknameName.doOnTextChanged { text, _, _, _ ->
            val nickname = text.toString()
            val isDuplicated = false // 여기에 실제 중복 확인 로직을 추가해야 합니다.
            val isValid = validateNickname(nickname) // 유효성 검사 추가
            if (nickname.isEmpty()) {
                // 사용자가 닉네임을 모두 지웠을 경우
                setInitialState() // UI를 초기 상태로 (경고 메시지 등 숨김)
                binding.tvMypageProfileEditTopbarEdit.isEnabled = true // 빈 닉네임으로도 수정 가능하도록 (또는 false로 하고 완료 못하게)
                // 정책에 따라 다름. 만약 빈 닉네임으로 업데이트하는 것을 허용한다면 true
                // 혹은 초기 닉네임과 같다면 비활성화, 다르면 활성화
            }
            binding.tvMypageProfileEditTopbarEdit.isEnabled = false

            if (isValid) {
                // 유효한 닉네임일 경우
                if (!isDuplicated) {
                    binding.etMypageProfileEditNicknameName.setTextColor(requireContext().getColor(R.color.white)) // 기본 색상
                    binding.etMypageProfileEditNicknameName.setBackgroundResource(R.drawable.shape_rect_4_white_line) // 기본 배경
                    binding.etMypageProfileEditNicknameName.setOnClickListener {
                        signupViewModel.checkNicknameAvailability(nickname) // 중복 확인 로직 호출
                        binding.tvMypageProfileEditTopbarEdit.isEnabled = true
                    }
                    binding.etMypageProfileEditNicknameName.isEnabled = true // 버튼 활성화
                } else {
                    binding.layoutMypageProfileEditWarning4.visibility = View.GONE // 중복 아님
                }
            }
        }

        // 프로필 수정 완료 클릭 리스너
        binding.tvMypageProfileEditTopbarEdit.setOnSingleClickListener {
            val nicknameInput = binding.etMypageProfileEditNicknameName.text.toString().trim()
            val currentProfileImageUri = viewModel.profileUri.value

            val finalNickname = if (nicknameInput.isNotEmpty()) {
                nicknameInput
            } else {
                null
            }

            val finalProfileImageUri = profileImageUri ?: currentProfileImageUri // 선택된 이미지가 없으면 현재 이미지 유지

            viewModel.updateProfile(finalNickname, finalProfileImageUri)

            navigator.navigate(R.id.action_myPageProfileEditFragment_to_myPageProfileFragment)
        }
    }

    private fun setInitialState() {
        binding.etMypageProfileEditNicknameName.setTextColor(requireContext().getColor(R.color.nickname_gray)) // 회색
        binding.etMypageProfileEditNicknameName.setBackgroundResource(R.drawable.shape_rect_4_white_line)
        binding.layoutMypageProfileEditValid.visibility = View.GONE
        binding.layoutMypageProfileEditWarning1.visibility = View.GONE
        binding.layoutMypageProfileEditWarning2.visibility = View.GONE
        binding.layoutMypageProfileEditWarning3.visibility = View.GONE
        binding.layoutMypageProfileEditWarning4.visibility = View.GONE // 초기 경고 숨기기
    }

    private fun validateNickname(nickname: String): Boolean {
        var isValid = true
        // 닉네임 유효성 검사
        if (nickname.isNotEmpty() && nickname.length > 10) {
            binding.etMypageProfileEditNicknameName.setBackgroundResource(R.drawable.shape_rect_4_white_line)
            binding.layoutMypageProfileEditWarning2.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.layoutMypageProfileEditWarning2.visibility = View.GONE
        }
        if (nickname.isNotEmpty() && nickname.contains(" ")) {
            binding.etMypageProfileEditNicknameName.setBackgroundResource(R.drawable.shape_rect_4_white_line)
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

    private fun openPhotoPicker() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*" // 모든 이미지 타입 선택
        }
        photoPickerLauncher.launch(intent) // 갤러리 열기
    }
}
