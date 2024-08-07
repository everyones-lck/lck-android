package umc.everyones.lck.presentation.mypage.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.databinding.ItemMypageCommunityBinding
import umc.everyones.lck.domain.model.community.Comment
import umc.everyones.lck.domain.model.community.Post

sealed class CommunityItem {
    data class PostItem(val post: Post) : CommunityItem()
    data class CommentItem(val comment: Comment) : CommunityItem()
}

class MyPageCommunityItemAdapter : ListAdapter<CommunityItem, RecyclerView.ViewHolder>(
    CommunityItemDiffCallback()
) {

    companion object {
        private const val TYPE_POST = 0
        private const val TYPE_COMMENT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CommunityItem.PostItem -> TYPE_POST
            is CommunityItem.CommentItem -> TYPE_COMMENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMypageCommunityBinding.inflate(inflater, parent, false)
        return when (viewType) {
            TYPE_POST -> PostViewHolder(binding)
            TYPE_COMMENT -> CommentViewHolder(binding)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PostViewHolder -> holder.bind(getItem(position) as CommunityItem.PostItem)
            is CommentViewHolder -> holder.bind(getItem(position) as CommunityItem.CommentItem)
        }
    }

    class PostViewHolder(private val binding: ItemMypageCommunityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CommunityItem.PostItem) {
            binding.tvMypageCommunityTitle.text = item.post.title
            binding.tvMypageCommunityCategory.text = item.post.category
            binding.tvMypageCommunityShortcuts.text = "해당 게시글 바로가기   "
            binding.executePendingBindings()
        }
    }

    class CommentViewHolder(private val binding: ItemMypageCommunityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CommunityItem.CommentItem) {
            val commentBody = item.comment.body
            val truncatedBody = if (commentBody.length > 20) {
                "${commentBody.take(20)}..."
            } else {
                commentBody
            }

            binding.tvMypageCommunityTitle.text = truncatedBody
            binding.tvMypageCommunityCategory.text = item.comment.category
            binding.tvMypageCommunityShortcuts.text = "해당 댓글 바로가기   "
            binding.executePendingBindings()
        }
    }
}

class CommunityItemDiffCallback : DiffUtil.ItemCallback<CommunityItem>() {
    override fun areItemsTheSame(oldItem: CommunityItem, newItem: CommunityItem): Boolean {
        return when {
            oldItem is CommunityItem.PostItem && newItem is CommunityItem.PostItem -> oldItem.post.postId == newItem.post.postId
            oldItem is CommunityItem.CommentItem && newItem is CommunityItem.CommentItem -> oldItem.comment.commentId == newItem.comment.commentId
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: CommunityItem, newItem: CommunityItem): Boolean {
        return oldItem == newItem
    }
}
