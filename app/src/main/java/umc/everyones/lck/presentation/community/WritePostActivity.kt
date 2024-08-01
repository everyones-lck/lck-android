package umc.everyones.lck.presentation.community

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.ActivityWritePostBinding
import umc.everyones.lck.domain.model.community.Post
import umc.everyones.lck.presentation.base.BaseActivity
import umc.everyones.lck.presentation.community.adapter.SpinnerAdapter
import umc.everyones.lck.presentation.community.adapter.WriteMediaRVA
import umc.everyones.lck.util.GridSpaceItemDecoration
import umc.everyones.lck.util.extension.showCustomSnackBar
import umc.everyones.lck.util.extension.toCategoryPosition
import umc.everyones.lck.util.extension.validateMaxLength

@AndroidEntryPoint
class WritePostActivity : BaseActivity<ActivityWritePostBinding>(R.layout.activity_write_post) {
    private val writePostViewModel: WritePostViewModel by viewModels()

    private val writeMediaRVA by lazy {
        WriteMediaRVA {
            // 미디어 추가 버튼 클릭 시
            // 권한 검사 및 요청 -> 미디어 선택
            checkPermissionAndOpenMediaPicker()
        }
    }

    // 권한 요청
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                openMediaPicker()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    // 미디어 선택
    private val selectMediaLauncher =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri> ->
            handleMediaUris(uris)
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
    private fun initEditView(){
        val post: Post? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("edit", Post::class.java)
        } else {
            intent.getSerializableExtra("edit") as? Post
        }

        if(post != null){
            Log.d("post", post.toString())
            with(binding){
                spinnerWriteCategory.setSelection(post.category.toCategoryPosition())
                etWriteTitle.setText(post.title)
                etWriteBody.setText(post.body)
            }
        }
    }

    // 제목 유효성 검사
    private fun validatePostTitle() {
        binding.etWriteTitle.validateMaxLength(20,
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
        binding.etWriteBody.validateMaxLength(2000,
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

    // 권한 검사 및 요청
    private fun checkPermissionAndOpenMediaPicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermission(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun requestPermission(permission: String) {
        when {
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                openMediaPicker()
            }

            shouldShowRequestPermissionRationale(permission) -> {
                Toast.makeText(this, "Permission needed to access media", Toast.LENGTH_SHORT).show()
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

    // 선택한 미디어 Uri를 통해 RecyclerView에 반영
    private fun handleMediaUris(uris: List<Uri>) {
        var updateList = writeMediaRVA.currentList.toMutableList().apply {
            // 미디어 12개 선택 초과 시 앞에서 부터 12개만 반영
            val addUris = if(uris.size > 12){
                uris.take(12)
            } else {
                uris
            }
            addAll(addUris)
        }

        // 미디어 추가 버튼 1개 + 미디어 개수 12개일 때
        // 미디어 추가 버튼 삭제
        // Uri 리스트 앞에서 부터 12개만 반영
        if(updateList.size > 12){
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
                if(etWriteTitle.text.isEmpty() || etWriteBody.text.isEmpty()){
                    showCustomSnackBar(binding.tvWriteGuide, "필수 항목을 입력하지 않았습니다")
                    return@setOnClickListener
                }

                Intent(this@WritePostActivity, WritePostActivity::class.java).apply {
                    putExtra("category", spinnerWriteCategory.selectedItem.toString())
                    setResult(RESULT_OK, this)
                }
                finish()
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