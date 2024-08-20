package umc.everyones.lck.presentation.community.write

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import umc.everyones.lck.R
import umc.everyones.lck.databinding.ActivityWritePostBinding
import umc.everyones.lck.domain.model.community.Post
import umc.everyones.lck.presentation.base.BaseActivity
import umc.everyones.lck.presentation.community.adapter.SpinnerAdapter
import umc.everyones.lck.presentation.community.adapter.WriteMediaRVA
import umc.everyones.lck.util.GridSpaceItemDecoration
import umc.everyones.lck.util.extension.showCustomSnackBar
import umc.everyones.lck.util.extension.toCategoryPosition
import umc.everyones.lck.util.extension.uriToFile
import umc.everyones.lck.util.extension.uriToFileCopy
import umc.everyones.lck.util.extension.validateMaxLength
import java.io.File
import java.util.UUID

@AndroidEntryPoint
class WritePostActivity : BaseActivity<ActivityWritePostBinding>(R.layout.activity_write_post) {
    private val writePostViewModel: WritePostViewModel by viewModels()

    private val writeMediaRVA by lazy {
        WriteMediaRVA {
            // 미디어 추가 버튼 클릭 시 미디어 선택
            photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }
    }

    // 권한 요청
    private val photoPickerLauncher =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(12)) { uris ->
            if (uris.isNotEmpty()) {
                val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                uris.forEach { uri ->
                    contentResolver.takePersistableUriPermission(uri, flag)
                }
                handleMediaUris(uris)
            }
        }

    override fun initObserver() {

    }

    override fun initView() {
        initMediaRVAdapter()
        initSpinnerAdapter()
        initEditView()
        validatePostTitle()
        validatePostBody()
        writeDone()

        binding.ivWriteClose.setOnClickListener {
            finish()
        }
    }


    // 글 수정 시 기존 게시글 Data View에 반영
    private fun initEditView() {
        val post: Post? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("edit", Post::class.java)
        } else {
            intent.getSerializableExtra("edit") as? Post
        }

        if (post != null) {
            Log.d("post", post.toString())
            with(binding) {
                spinnerWriteCategory.setSelection(post.category.toCategoryPosition())
                etWriteTitle.setText(post.title)
                etWriteBody.setText(post.body)
            }
        }
    }

    // 제목 유효성 검사
    private fun validatePostTitle() {
        binding.etWriteTitle.validateMaxLength(this, 20,
            onLengthExceeded = {
                showCustomSnackBar(
                    binding.etWriteTitle,
                    "제목은 최대 20자까지 입력할 수 있어요"
                )
            }
        )
    }

    // 본문 유효성 검사
    private fun validatePostBody() {
        binding.etWriteBody.validateMaxLength(this, 2000,
            onLengthExceeded = {
                showCustomSnackBar(
                    binding.etWriteBody,
                    "내용은 최대 2,000자까지 입력할 수 있어요"
                )
            }
        )
    }

    private fun initMediaRVAdapter() {
        binding.rvWriteMedia.apply {
            adapter = writeMediaRVA
            addItemDecoration(GridSpaceItemDecoration(4, 8))
            itemAnimator = null
        }
        writeMediaRVA.submitList(listOf(Uri.EMPTY))
    }

    // 선택한 미디어 Uri를 통해 RecyclerView에 반영
    private fun handleMediaUris(uris: List<Uri>) {
        var updateList = writeMediaRVA.currentList.toMutableList().apply {
            // 미디어 12개 선택 초과 시 앞에서 부터 12개만 반영
            val addUris = if (uris.size > 12) {
                uris.take(12)
            } else {
                uris
            }
            addAll(addUris)
        }

        // 미디어 추가 버튼 1개 + 미디어 개수 12개일 때
        // 미디어 추가 버튼 삭제
        // Uri 리스트 앞에서 부터 12개만 반영
        if (updateList.size > 12) {
            updateList.apply { removeAt(0) }
            updateList = updateList.take(12).toMutableList()
        }
        writeMediaRVA.submitList(updateList)
    }

    private fun initSpinnerAdapter() {
        binding.spinnerWriteCategory.adapter = SpinnerAdapter(this, spinnerList)
    }

    private fun writeDone() {
        with(binding) {
            ivWriteDone.setOnClickListener {
                // 제목이나 본문 입력하지 않을 시 예외처리
                if (etWriteTitle.text.isEmpty() || etWriteBody.text.isEmpty()) {
                    showCustomSnackBar(binding.tvWriteGuide, "필수 항목을 입력하지 않았습니다")
                    return@setOnClickListener
                }

                val imageParts = mutableListOf<MultipartBody.Part?>()
                val uris = writeMediaRVA.currentList
                if (uris.isNotEmpty()) {
                    uris.filter { it != Uri.EMPTY }.forEach { uri ->
                        val file = uriToFile(uri)
                        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                        val body =
                            MultipartBody.Part.createFormData("files", file.name, requestFile)
                        imageParts.add(body)
                    }


                    writePostViewModel.writeCommunityPost(imageParts, "잡담", "ㅇㅇ", "ㅇㅇ")

                    Intent(this@WritePostActivity, WritePostActivity::class.java).apply {
                        putExtra("category", spinnerWriteCategory.selectedItem.toString())
                        setResult(RESULT_OK, this)
                    }
                    finish()
                }
            }
        }
    }

    companion object {
        private val spinnerList = listOf("잡담", "응원", "FA", "거래", "질문", "후기")
        fun newIntent(context: Context) =
            Intent(context, WritePostActivity::class.java).apply {
            }

        fun editIntent(context: Context, post: Post) =
            Intent(context, WritePostActivity::class.java).apply {
                putExtra("edit", post)
            }
    }
}