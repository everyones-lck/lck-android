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
            checkPermissionAndOpenMediaPicker()
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                openMediaPicker()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

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
        }
        writeMediaRVA.submitList(listOf(Uri.EMPTY))
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

    private fun handleMediaUris(uris: List<Uri>) {
        val list = uris.toMutableList().apply { add(0, Uri.EMPTY) }
        writeMediaRVA.submitList(list)
        for (uri in uris) {
            Log.d("uri", uri.toString())
        }
    }

    private fun initSpinnerAdapter() {
        binding.spinnerWriteCategory.adapter = SpinnerAdapter(this, spinnerList)
    }

    private fun writeDone() {
        with(binding) {
            ivWriteDone.setOnClickListener {
                val intent = Intent(this@WritePostActivity, WritePostActivity::class.java)
                intent.putExtra("category", spinnerWriteCategory.selectedItem.toString())
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    companion object {
        private val spinnerList = listOf("잡담", "응원", "FA", "거래", "질문", "후기")
        fun newIntent(context: Context) =
            Intent(context, WritePostActivity::class.java).apply {
            }

        fun EditIntent(context: Context, post: Post) =
            Intent(context, WritePostActivity::class.java).apply {
                putExtra("edit", post)
            }
    }
}