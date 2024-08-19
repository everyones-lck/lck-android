package umc.everyones.lck.presentation.party.chat

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import okhttp3.Request
import umc.everyones.lck.R
import umc.everyones.lck.databinding.ActivityViewingPartyChatBinding
import umc.everyones.lck.domain.model.party.ChatItem
import umc.everyones.lck.presentation.party.adapter.ChatRVA
import umc.everyones.lck.presentation.party.adapter.ChatRVA.Companion.RECEIVER
import umc.everyones.lck.presentation.party.adapter.ChatRVA.Companion.SENDER
import umc.everyones.lck.util.extension.drawableOf
import umc.everyones.lck.util.extension.repeatOnStarted
import umc.everyones.lck.util.extension.setOnSingleClickListener
import umc.everyones.lck.util.extension.showCustomSnackBar
import umc.everyones.lck.util.extension.textToString
import javax.inject.Inject

@AndroidEntryPoint
class ViewingPartyChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewingPartyChatBinding

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var request: Request

    @Inject
    lateinit var spf: SharedPreferences

    private val chatRVA by lazy {
        ChatRVA()
    }

    private val postId by lazy {
        intent.getLongExtra("postId", 0L)
    }

    private val isParticipant by lazy {
        intent.getBooleanExtra("isParticipant", false)
    }

    private val viewModel: ViewingPartyChatViewModel by viewModels()

    private lateinit var wsClient: WsClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewingPartyChatBinding.inflate(layoutInflater).apply {
            setContentView(this.root)
        }
        initObserver()
        initView()
    }
    private fun initView() {
        viewModel.setPostId(postId)
        if(isParticipant){
            viewModel.createViewingPartyChatRoomAsParticipant()
        } else {
            viewModel.createViewingPartyChatRoom()
        }
        validateMessageSend()
        initChatRVAdapter()
        sendMessage()
        binding.ivChatBackBtn.setOnSingleClickListener {
            finish()
        }
    }

    private fun initChatRVAdapter(){
        binding.rvChat.adapter = chatRVA
        val list = listOf(
            ChatItem("ds", "현재 뷰파 참여 인원이 어떻게 되나요??", RECEIVER, 1),
            ChatItem("ds", "11명 입니다!", SENDER, 2),
            ChatItem("ds", "감사합니당", RECEIVER, 3),
            ChatItem("ds", "네에 ☺\uFE0F", SENDER, 4),
        )
        chatRVA.submitList(list)
    }

    private fun validateMessageSend() {
        binding.etChatInput.addTextChangedListener(
            onTextChanged = { text: CharSequence?, _: Int, _: Int, _: Int ->
                if (text != null) {

                    // 댓글 작성 여부에 따른 전송 버튼 활성화 제어
                    if(text.isEmpty()){
                        binding.ivChatSendBtn.setImageDrawable(drawableOf(R.drawable.ic_send))
                    } else {
                        binding.ivChatSendBtn.setImageDrawable(drawableOf(R.drawable.ic_send_enabled))
                    }

                    if (text.length >= 1000) {
                        showCustomSnackBar(
                            binding.ivChatSendBtn,
                            "채팅은 최대 1,000자까지 입력할 수 있어요"
                        )
                    }
                }
            }
        )
    }

    private fun sendMessage(){
        with(binding){
            ivChatSendBtn.setOnClickListener {
                /*chatRVA.submitList(chatRVA.currentList.toMutableList().apply {
                    add(ChatItem("ds", etChatInput.text.toString(), SENDER, 5))
                })
                etChatInput.setText("")*/
                wsClient.sendMessage(etChatInput.textToString())
                etChatInput.setText("")
            }
        }
    }

    private fun initObserver() {
        repeatOnStarted {
            viewModel.roomId.collect{roomId ->
                if(roomId != 0L){
                    viewModel.fetchViewingPartyChatLog(roomId)
                    initWsClient()
                }
            }
        }
    }

    private fun initWsClient(){
        wsClient = WsClient(viewModel, okHttpClient, request, spf)
        wsClient.apply {
            connectWebSocket()
            enterChatRoom("되나")
        }
    }

    companion object {
        fun newIntent(context: Context, postId: Long, isParticipant: Boolean): Intent =
            Intent(context, ViewingPartyChatActivity::class.java).apply {
                putExtra("postId", postId)
                putExtra("isParticipant", isParticipant)
            }
    }

}