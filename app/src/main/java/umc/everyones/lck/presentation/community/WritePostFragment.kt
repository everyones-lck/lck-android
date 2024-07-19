package umc.everyones.lck.presentation.community

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentWritePostBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.community.adapter.WriteMediaRVA
import umc.everyones.lck.presentation.community.adapter.SpinnerAdapter
import umc.everyones.lck.util.GridSpaceItemDecoration
import umc.everyones.lck.util.extension.showCustomSnackBar
import umc.everyones.lck.util.extension.validateMaxLength


class WritePostFragment : BaseFragment<FragmentWritePostBinding>(R.layout.fragment_write_post) {
    private val navigator by lazy {
        findNavController()
    }
    private val writePostViewModel: WritePostViewModel by activityViewModels()

    private var _writeMediaRVA: WriteMediaRVA? = null
    private val writeMediaRVA get() = _writeMediaRVA

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            openMediaPicker()
        } else {
            Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private val selectMediaLauncher = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri> ->
        handleMediaUris(uris)
    }


    override fun initObserver() {

    }

    override fun initView() {
        initMediaRVAdapter()
        initSpinnerAdapter()
        validatePostTitle()
        validatePostBody()
        writeDone()

        binding.ivWriteClose.setOnClickListener {
            navigator.navigateUp()
        }
    }


    private fun validatePostTitle(){
        binding.etWriteTitle.validateMaxLength(20,
            onLengthExceeded = { showCustomSnackBar("제목은 최대 20자까지 입력할 수 있어요") }
        )
    }

    private fun validatePostBody(){
        binding.etWriteBody.validateMaxLength(2000,
            onLengthExceeded =  {showCustomSnackBar("내용은 최대 2,000자까지 입력할 수 있어요")}
        )
    }

    private fun initMediaRVAdapter() {
        _writeMediaRVA = WriteMediaRVA {
            checkPermissionAndOpenMediaPicker()
        }
        binding.rvWriteMedia.adapter = writeMediaRVA
        binding.rvWriteMedia.addItemDecoration(GridSpaceItemDecoration(4, 8))
        writeMediaRVA?.submitList(listOf(Uri.EMPTY))
    }

    private fun checkPermissionAndOpenMediaPicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermission(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun requestPermission(permission: String) {
        when {
            ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED -> {
                openMediaPicker()
            }
            shouldShowRequestPermissionRationale(permission) -> {
                Toast.makeText(requireContext(), "Permission needed to access media", Toast.LENGTH_SHORT).show()
                requestPermissionLauncher.launch(permission)
            }
            else -> {
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    private fun openMediaPicker() {
        // 사진과 비디오를 모두 선택할 수 있도록 MIME 타입을 설정
        selectMediaLauncher.launch("image/* video/*")
    }

    private fun handleMediaUris(uris: List<Uri>) {
        val list = uris.toMutableList().apply { add(0, Uri.EMPTY)}
        writeMediaRVA?.submitList(list)
        for (uri in uris) {
            Log.d("uri", uri.toString())
        }
    }

    private fun initSpinnerAdapter() {
        binding.spinnerWriteCategory.adapter = SpinnerAdapter(requireContext(), spinnerList)
    }

    private fun writeDone() {
        binding.ivWriteDone.setOnClickListener {
            writePostViewModel.setSelectedCategory(binding.spinnerWriteCategory.selectedItem.toString())
            navigator.navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _writeMediaRVA = null
    }

    companion object {
        private val spinnerList = listOf("잡담", "응원", "FA", "거래", "질문", "후기")
    }
}