package umc.everyones.lck.presentation.lck.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.R

class HistoryDetailAdapter(private val details: List<String>) :
    RecyclerView.Adapter<HistoryDetailAdapter.DetailsViewHolder>() {

    inner class DetailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val detailTextView: TextView = view.findViewById(R.id.tv_about_lck_history_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_about_lck_history_detail, parent, false)
        return DetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        holder.detailTextView.text = details[position]
    }

    override fun getItemCount(): Int {
        return details.size
    }

}