package umc.everyones.lck.presentation.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.databinding.ItemCommunityPostBinding
import umc.everyones.lck.domain.model.community.Post

class PostListRVA(val readPost: (Int) -> Unit) : ListAdapter<Post, PostListRVA.PostViewHolder>(DiffCallback()) {

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
            fun bind(post: Post){
                with(binding){
                    tvPostTitle.text = post.title
                    tvPostDate.text = post.date
                    tvPostNickname.text = post.nickname
                    tvPostFavoriteTeam.text = post.favoriteTeam
                    tvPostComment.text = post.commentCnt.toString()

                    root.setOnClickListener {
                        readPost(post.postId)
                    }
                }
            }
        }

    class DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.postId == newItem.postId

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}