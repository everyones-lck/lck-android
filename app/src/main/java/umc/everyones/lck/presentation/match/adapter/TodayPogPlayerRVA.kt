package umc.everyones.lck.presentation.match.adapter

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.databinding.ItemTodayPogPlayerBinding

class TodayPogPlayerRVA(private var todayPog: List<Int>, private val onItemClick: (Int) -> Unit):
    RecyclerView.Adapter<TodayPogPlayerRVA.ViewHolder>() {

    private var selectedPlayerIndex: Int? = null
    private var isSingleItemShowing = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTodayPogPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = todayPog[position]
        val isSelected = position == selectedPlayerIndex
        holder.bind(item, isSelected)
        holder.itemView.setOnClickListener {
            if (isSingleItemShowing) {
                // 모든 아이템을 다시 보여주기
                isSingleItemShowing = false
                selectedPlayerIndex = null
                onItemClick(-1)
            } else {
                // 선택된 아이템만 보여주기
                selectedPlayerIndex = position
                isSingleItemShowing = true
                notifyDataSetChanged()
                onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int = todayPog.size

    fun getSelectedPlayerIndex(): Int? = selectedPlayerIndex

    fun updateData(newTodayPog: List<Int>) {
        todayPog = newTodayPog
        selectedPlayerIndex = if (newTodayPog.size == 1) 0 else null
        notifyDataSetChanged()
    }
//    fun isShowingSingleItem(): Boolean = isSingleItemShowing

    fun resetSelection() {
        isSingleItemShowing = false
        selectedPlayerIndex = null
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemTodayPogPlayerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todayPog: Int, isSelected: Boolean) {
            binding.ivTodayPogPlayer.setImageResource(todayPog)
            if (isSelected) {
                binding.ivTodayPogPlayer.clearColorFilter()
                binding.root.visibility = View.VISIBLE
            } else {
                val matrix = ColorMatrix().apply { setSaturation(0f) }
                binding.ivTodayPogPlayer.colorFilter = ColorMatrixColorFilter(matrix)
                binding.root.visibility = View.VISIBLE
            }
        }
    }

}