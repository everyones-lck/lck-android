package umc.everyones.lck.presentation.community.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.everyones.lck.databinding.ItemCommentBinding
import umc.everyones.lck.domain.model.community.Comment
import umc.everyones.lck.util.extension.setOnSingleClickListener

class CommentRVA(
    val editComment: (Int, String) -> Unit,
    val reportComment: (Int) -> Unit,
    val deleteComment: (Int) -> Unit
) : ListAdapter<Comment, CommentRVA.CommentViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            ItemCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class CommentViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(comment: Comment) {
            with(binding) {
                /*
                tvCommentNickname.text = "${comment.nickname} | ${comment.favoriteTeam}"
                tvCommentBody.text = comment.body
                tvCommnetDate.text = comment.date
                Glide.with(ivCommentProfile.context)
                    .load(comment.profileImageUrl)
                    .into(ivCommentProfile)*/
                // 댓글 수정
                ivCommentEditBtn.setOnSingleClickListener {
                    editComment(comment.commentId, comment.body)
                }

                // 댓글 신고
                ivCommentReportBtn.setOnSingleClickListener {
                    reportComment(comment.commentId)
                }

                // 댓글 삭제
                ivCommentDeleteBtn.setOnSingleClickListener {
                    deleteComment(comment.commentId)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment) =
            oldItem.commentId == newItem.commentId

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment) =
            oldItem == newItem
    }
}