package umc.everyones.lck.presentation.party

import android.content.Context
import android.content.Intent
import androidx.core.widget.addTextChangedListener
import umc.everyones.lck.R
import umc.everyones.lck.databinding.ActivityViewingPartyChatBinding
import umc.everyones.lck.domain.model.party.ChatItem
import umc.everyones.lck.presentation.base.BaseActivity
import umc.everyones.lck.presentation.party.adapter.ChatRVA
import umc.everyones.lck.util.extension.drawableOf
import umc.everyones.lck.util.extension.showCustomSnackBar

class ViewingPartyChatActivity : BaseActivity<ActivityViewingPartyChatBinding>(R.layout.activity_viewing_party_chat) {
    private val chatRVA by lazy {
        ChatRVA()
    }
    override fun initView() {
        validateMessageSend()
        binding.rvChat.adapter = chatRVA
        val list = listOf(
            ChatItem("ds", "dsds", 1, 1),
            ChatItem("ds", "dsds", 0, 2),
            ChatItem("ds", "dsds", 1, 3),
            ChatItem("ds", "dsds", 0, 4),
            ChatItem("ds", "dsds", 1, 5),
            ChatItem("ds", "dsds", 0, 6),
            ChatItem("ds", "dsds", 1, 7),
            ChatItem("ds", "dsds", 0, 8),
            ChatItem("ds", "dsds", 1, 9),
            ChatItem("ds", "dsds", 0, 10),
            ChatItem("ds", "dsds", 0, 11),
            ChatItem("ds", "dsds", 0, 12),
            ChatItem("ds", "dsds", 0, 13),
            ChatItem("ds", "dsds", 0, 14),
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

    override fun initObserver() {

    }

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, ViewingPartyChatActivity::class.java)
    }

}