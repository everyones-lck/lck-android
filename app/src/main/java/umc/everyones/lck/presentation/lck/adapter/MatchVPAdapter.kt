package umc.everyones.lck.presentation.lck.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.R
import umc.everyones.lck.presentation.lck.data.MatchData


class MatchVPAdapter : RecyclerView.Adapter<MatchVPAdapter.MatchViewHolder>() {

    private val matchDetailsList: MutableList<List<MatchData>> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_about_lck_matches, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val matchDetails = matchDetailsList[position]
        holder.bind(matchDetails)
    }

    override fun getItemCount(): Int = matchDetailsList.size

    fun addMatchDetails(details: List<MatchData>) {
        matchDetailsList.add(details)
        notifyItemInserted(matchDetailsList.size - 1)
    }

    inner class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recyclerView: RecyclerView = itemView.findViewById(R.id.rv_about_lck_match_details)

        fun bind(matchDetails: List<MatchData>) {
            recyclerView.layoutManager = LinearLayoutManager(itemView.context)
            recyclerView.adapter = MatchDetailsAdapter(matchDetails)
            //recyclerView.setNestedScrollingEnabled(false) // 스크롤 비활성화
        }
    }
}
