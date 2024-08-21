package umc.everyones.lck.presentation.community.read

import android.view.View
import androidx.fragment.app.activityViewModels
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.util.Util
import umc.everyones.lck.R
import umc.everyones.lck.databinding.DialogVideoBinding
import umc.everyones.lck.presentation.base.BaseDialogFragment
import umc.everyones.lck.util.extension.repeatOnStarted
import umc.everyones.lck.util.extension.setOnSingleClickListener


class ReadVideoDialogFragment : BaseDialogFragment<DialogVideoBinding>(R.layout.dialog_video) {
    private val viewModel: ReadPostViewModel by activityViewModels()
    private var player: ExoPlayer? = null
    private val videoView: StyledPlayerView? = null
    private var playWhenReady = true // 재생,일시정지 정보

    private var currentWindow = 0 // 현재 윈도우 지수

    private var playbackPosition = 0L // 현재 재생 위치

    private fun initializePlayer(uri: String) {
        if (player == null) {
            player = ExoPlayer.Builder(requireContext()).build()
            binding.ivImage.player = player
            val mediaItem: MediaItem = MediaItem.fromUri(uri)
            player!!.setMediaItem(mediaItem)
        }
        // releasePlayer에 저장한 상태 정보를 초기화 중에 플레이어에게 제공하는 부분
        player!!.playWhenReady = playWhenReady
        player!!.seekTo(currentWindow, playbackPosition)
        player!!.prepare()
    }

    private fun hideSystemUi() {
        videoView!!.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }
    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            viewModel.imageUrl.collect{
                if (it.isEmpty()){
                    return@collect
                }
                initializePlayer(it)
            }
        }
    }

    override fun initView() {
        requireContext().dialogFragmentResize(this, 0.8f, 0.8f)
        binding.ivClose.setOnSingleClickListener {
            dismiss()
        }
    }

    private fun releasePlayer() {
        playbackPosition = player!!.currentPosition
        currentWindow = player!!.currentMediaItemIndex
        playWhenReady = player!!.playWhenReady
        player!!.release()
        player = null
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}