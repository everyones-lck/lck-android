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
import umc.everyones.lck.databinding.ItemMediaWriteBinding
import umc.everyones.lck.util.extension.setOnSingleClickListener

class WriteMediaRVA(val addMedia: () -> Unit, val deleteMedia: (Int) -> Unit) : ListAdapter<Uri, RecyclerView.ViewHolder>(DiffCallback()) {

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
                WriteMediaViewHolder(
                    ItemMediaWriteBinding.inflate(
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
                (holder as WriteMediaViewHolder).bind(currentList[position])
            }
        }
    }

    inner class MediaAddViewHolder(private val binding: ItemMediaAddBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(){
                binding.layoutMediaAddBtn.setOnClickListener {
                    addMedia()
                }
            }
        }

    inner class WriteMediaViewHolder(private val binding: ItemMediaWriteBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(uri: Uri){
            with(binding) {
                Glide.with(ivMediaImage.context)
                    .load(uri)
                    .into(ivMediaImage)

                // 미디어 삭제 기능
                ivMediaDeleteBtn.setOnSingleClickListener {
                    // 미디어 개수가 12개일 때 선택한 미디어 삭제 후
                    // RecyclerView에 미디어 추가 버튼 생성
                    deleteMedia(bindingAdapterPosition)

                    if(currentList.size == 12 && currentList[0] != Uri.EMPTY){
                        submitList(currentList.toMutableList().apply {
                            remove(uri)
                            add(0, Uri.EMPTY)
                        })
                    }

                    // 미디어 개수가 12개가 아니면 선택한 미디어 삭제
                    else {
                        submitList(currentList.toMutableList().apply { remove(uri) })
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(currentList[position]){
            // Uri.Empty일 때 MEDIA_ADD로 ViewType 지정해
            // RecyclerView에 미디어 추가 버튼 생성
            Uri.EMPTY -> MEDIA_ADD
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