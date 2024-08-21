package umc.everyones.lck.presentation.community.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.everyones.lck.databinding.ItemCommentBinding
import umc.everyones.lck.domain.model.community.Comment
import umc.everyones.lck.domain.model.response.community.ReadCommunityResponseModel
import umc.everyones.lck.util.extension.setOnSingleClickListener

class CommentRVA(
    val reportComment: (Long) -> Unit,
    val deleteComment: (Long) -> Unit
) : ListAdapter<ReadCommunityResponseModel.CommentListElementModel, CommentRVA.CommentViewHolder>(DiffCallback()) {

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
        fun bind(comment: ReadCommunityResponseModel.CommentListElementModel) {
            with(binding) {
                tvCommentNickname.text = comment.writerInfo
                tvCommentBody.text = comment.content
                tvCommnetDate.text = comment.createdAt
                Glide.with(ivCommentProfile.context)
                    .load(comment.profileUrl)
                    .into(ivCommentProfile)

                if (comment.isWriter){
                    ivCommentReportBtn.visibility = View.GONE
                    ivCommentDeleteBtn.visibility = View.VISIBLE
                } else {
                    ivCommentReportBtn.visibility = View.VISIBLE
                    ivCommentDeleteBtn.visibility = View.GONE
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

    class DiffCallback : DiffUtil.ItemCallback<ReadCommunityResponseModel.CommentListElementModel>() {
        override fun areItemsTheSame(oldItem: ReadCommunityResponseModel.CommentListElementModel, newItem: ReadCommunityResponseModel.CommentListElementModel) =
            oldItem.commentId == newItem.commentId

        override fun areContentsTheSame(oldItem: ReadCommunityResponseModel.CommentListElementModel, newItem: ReadCommunityResponseModel.CommentListElementModel) =
            oldItem == newItem
    }
}