package umc.everyones.lck.presentation.mypage

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentMypageProfileEditBinding
import umc.everyones.lck.presentation.base.BaseFragment

@AndroidEntryPoint
class MyPageProfileEditFragment : BaseFragment<FragmentMypageProfileEditBinding>(R.layout.fragment_mypage_profile_edit){

    private val myPageViewModel: MyPageViewModel by activityViewModels()
    private val navigator by lazy { findNavController() }


    override fun initObserver() {
        myPageViewModel.updateProfileResult.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                Log.d(TAG,"프로필 수정 성공")
            } else {
                Log.e(TAG, "프로필 수정 실패")
            }
        }
    }


    override fun initView() {
        setInitialState() // 초기 상태 설정

        // 뒤로가기 버튼 클릭 시 이동
        binding.ivMypageProfileEditBack.setOnClickListener {
            findNavController().navigateUp()
        }

        // 기본 이미지 사용 클릭 시
        binding.tvMypageProfileEditBasic.setOnClickListener {
            setDefaultImage() // 기본 이미지로 설정
        }

        // 갤러리에서 이미지 선택 클릭 시
        binding.ivMypageProfileEditProfile.setOnClickListener {
            openGallery() // 갤러리 열기
        }

        // 중복 확인 버튼 클릭 시
        binding.tvMypageNicknameDuplication.setOnClickListener {
            val nickname = binding.etMypageProfileEditNicknameName.text.toString().trim()
            if (validateNickname(nickname)) {
                // 유효한 닉네임일 경우 중복 확인 로직 호출
                myPageViewModel.checkNicknameAndUpdateProfile(nickname, binding.ivMypageProfileEditProfile.drawable.constantState ==
                        resources.getDrawable(R.drawable.img_signup_profile).constantState) // 기본 이미지 여부 확인
            }
        }

        // 수정 버튼 클릭 시
        binding.tvMypageProfileEditTopbarEdit.setOnClickListener {
            validateAndUpdateProfile()
            navigator.navigate(R.id.action_myPageProfileEditFragment_to_myPageProfileFragment)
        }

        binding.etMypageProfileEditNicknameName.doOnTextChanged { text, _, _, _ ->
            val nickname = text.toString()
            val isDuplicated = false // 여기에 실제 중복 확인 로직을 추가해야 합니다.
            val isValid = isNicknameValid(nickname) // 유효성 검사 추가

            if (isValid) {
                // 유효한 닉네임일 경우
                if (!isDuplicated) {
                    binding.tvMypageNicknameDuplication.setTextColor(requireContext().getColor(R.color.white)) // 기본 색상
                    binding.tvMypageNicknameDuplication.setBackgroundResource(R.drawable.shape_rect_12_white_line) // 기본 배경
                    binding.tvMypageNicknameDuplication.setOnClickListener {
                        myPageViewModel.checkNicknameAndUpdateProfile(nickname, true) // 중복 확인 로직 호출
                    }
                    binding.layoutMypageProfileEditWarning4.visibility = View.GONE // 중복 아님
                    binding.tvMypageNicknameDuplication.isEnabled = true // 버튼 활성화
                } else {
                    // 중복된 경우
                    setNicknameUnavailableState() // 중복 상태 설정
                    binding.tvMypageNicknameDuplication.isEnabled = false // 버튼 비활성화
                }
            } else {
                // 유효하지 않은 경우
                setNicknameUnavailableState() // 닉네임이 비어 있을 때 상태 설정
                binding.tvMypageNicknameDuplication.isEnabled = false // 버튼 비활성화
            }
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

    private fun setDefaultImage() {
        // 기본 이미지로 설정
        binding.ivMypageProfileEditProfile.setImageResource(R.drawable.img_signup_profile)
    }

    private fun openGallery() {
        // 갤러리에서 이미지 선택하기 위한 Intent
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                // 선택한 이미지를 ImageView에 설정
                binding.ivMypageProfileEditProfile.setImageURI(uri)
                // 서버에 이미지를 전송하기 위한 준비
                // 여기서 프로필 이미지 URI를 ViewModel에 전달할 수 있습니다.
            }
        }
    }

    private fun validateAndUpdateProfile() {
        val nickname = binding.etMypageProfileEditNicknameName.text.toString().trim()
        if (validateNickname(nickname)) {
            // 프로필 업데이트 요청
            val isDefaultImage = binding.ivMypageProfileEditProfile.drawable.constantState ==
                    resources.getDrawable(R.drawable.img_signup_profile).constantState // 기본 이미지 확인
            myPageViewModel.checkNicknameAndUpdateProfile(nickname, isDefaultImage)
        }
    }

    private fun isNicknameValid(nickname: String): Boolean {
        return nickname.length in 1..15 // 닉네임 유효성 검사
    }

    companion object {
        private const val GALLERY_REQUEST_CODE = 1001
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

}
