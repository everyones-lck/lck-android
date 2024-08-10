package umc.everyones.lck.presentation.party

import android.content.Context
import android.content.Intent
import umc.everyones.lck.R
import umc.everyones.lck.databinding.ActivityViewingPartyChatBinding
import umc.everyones.lck.domain.model.party.ChatItem
import umc.everyones.lck.presentation.base.BaseActivity
import umc.everyones.lck.presentation.party.adapter.ChatRVA

class ViewingPartyChatActivity : BaseActivity<ActivityViewingPartyChatBinding>(R.layout.activity_viewing_party_chat) {
    private val chatRVA by lazy {
        ChatRVA()
    }
    override fun initView() {
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

    override fun initObserver() {

    }

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, ViewingPartyChatActivity::class.java)
    }

}