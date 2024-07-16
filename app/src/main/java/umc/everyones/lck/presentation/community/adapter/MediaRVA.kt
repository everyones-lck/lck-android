package umc.everyones.lck.presentation.community.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.everyones.lck.databinding.ItemMediaAddBinding
import umc.everyones.lck.databinding.ItemMediaBinding

class MediaRVA(val addMedia: () -> Unit) : ListAdapter<Uri, RecyclerView.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            MEDIA_ADD -> {
                MediaAddViewHolder(
                    ItemMediaAddBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false)
                )
            }

            else -> {
                MediaViewHolder(
                    ItemMediaBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType) {
            MEDIA_ADD -> {
                (holder as MediaAddViewHolder).bind()
            }

            else -> {
                (holder as MediaViewHolder).bind(currentList[position])
            }
        }
    }

    inner class MediaAddViewHolder(private val binding: ItemMediaAddBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(){
                binding.layoutMediaAddBtn.setOnClickListener {
                    Log.d("add btn", "click")
                    addMedia()
                }
            }
        }

    inner class MediaViewHolder(private val binding: ItemMediaBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(uri: Uri){
            Glide.with(binding.ivMediaImage.context)
                .load(uri)
                .into(binding.ivMediaImage)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> MEDIA_ADD
            else -> MEDIA
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Uri>() {
        override fun areItemsTheSame(oldItem: Uri, newItem: Uri) =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: Uri, newItem: Uri) =
            oldItem == newItem
    }

    companion object {
        const val MEDIA_ADD = 0
        const val MEDIA = 1
    }
}