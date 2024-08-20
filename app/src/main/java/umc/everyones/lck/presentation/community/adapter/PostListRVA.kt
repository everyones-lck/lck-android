package umc.everyones.lck.presentation.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.databinding.ItemCommunityPostBinding
import umc.everyones.lck.domain.model.community.CommunityListModel
import umc.everyones.lck.domain.model.community.PostListItem

class PostListRVA(val readPost: (Long) -> Unit) : ListAdapter<CommunityListModel.CommunityListElementModel, PostListRVA.PostViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemCommunityPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class PostViewHolder(private val binding: ItemCommunityPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(postListItem: CommunityListModel.CommunityListElementModel){
                with(binding){
                    tvPostTitle.text = postListItem.postTitle
                    tvPostDate.text = postListItem.postCreatedAt
                    tvPostNickname.text = postListItem.userNickname
                    tvPostFavoriteTeam.text = postListItem.supportTeamName
                    tvPostComment.text = postListItem.commentCounts.toString()

                    // 게시글 postId 전달
                    root.setOnClickListener {
                        readPost(postListItem.postId)
                    }
                }
            }
        }

    class DiffCallback : DiffUtil.ItemCallback<CommunityListModel.CommunityListElementModel>() {
        override fun areItemsTheSame(oldItem: CommunityListModel.CommunityListElementModel, newItem: CommunityListModel.CommunityListElementModel) =
            oldItem.postId == newItem.postId

        override fun areContentsTheSame(oldItem: CommunityListModel.CommunityListElementModel, newItem: CommunityListModel.CommunityListElementModel) =
            oldItem == newItem
    }
}