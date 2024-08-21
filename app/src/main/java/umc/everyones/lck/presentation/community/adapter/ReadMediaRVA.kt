package umc.everyones.lck.presentation.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.everyones.lck.data.dto.response.community.ReadCommunityResponseDto
import umc.everyones.lck.databinding.ItemMediaReadBinding
import umc.everyones.lck.util.extension.setOnSingleClickListener

class ReadMediaRVA(val viewOriginalMedia: (String, Boolean) -> Unit // 미디어 원본 보기 위한 함수
 ) : ListAdapter<ReadCommunityResponseDto.File, ReadMediaRVA.ReadMediaViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadMediaViewHolder {
        return ReadMediaViewHolder(
            ItemMediaReadBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReadMediaViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ReadMediaViewHolder(private val binding: ItemMediaReadBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(file: ReadCommunityResponseDto.File){

                Glide.with(binding.ivMediaImage.context)
                    .load(file.fileUrl)
                    .into(binding.ivMediaImage)
                binding.ivMediaImage.setOnSingleClickListener {
                    viewOriginalMedia(file.fileUrl, file.isImage)
                }
            }
        }

    class DiffCallback : DiffUtil.ItemCallback<ReadCommunityResponseDto.File>() {
        override fun areItemsTheSame(oldItem: ReadCommunityResponseDto.File, newItem: ReadCommunityResponseDto.File) =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: ReadCommunityResponseDto.File, newItem: ReadCommunityResponseDto.File) =
            oldItem == newItem
    }
}