package umc.everyones.lck.presentation.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.databinding.ItemCommunityPostBinding
import umc.everyones.lck.domain.model.community.PostListItem

class PostListRVA(val readPost: (Int) -> Unit) : ListAdapter<PostListItem, PostListRVA.PostViewHolder>(DiffCallback()) {

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
            fun bind(postListItem: PostListItem){
                with(binding){
                    tvPostTitle.text = postListItem.title
                    tvPostDate.text = postListItem.date
                    tvPostNickname.text = postListItem.nickname
                    tvPostFavoriteTeam.text = postListItem.favoriteTeam
                    tvPostComment.text = postListItem.commentCnt.toString()

                    root.setOnClickListener {
                        readPost(postListItem.postId)
                    }
                }
            }
        }

    class DiffCallback : DiffUtil.ItemCallback<PostListItem>() {
        override fun areItemsTheSame(oldItem: PostListItem, newItem: PostListItem) =
            oldItem.postId == newItem.postId

        override fun areContentsTheSame(oldItem: PostListItem, newItem: PostListItem) =
            oldItem == newItem
    }
}