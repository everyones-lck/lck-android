package umc.everyones.lck.presentation.lck

import ItemAboutLckViewHolder
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MatchVPAdapter : RecyclerView.Adapter<ItemAboutLckViewHolder>() {

    private val matchDataList: ArrayList<MatchData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAboutLckViewHolder {
        return ItemAboutLckViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemAboutLckViewHolder, position: Int) {
        val matchData = matchDataList[position]
        holder.bind(matchData)
    }

    override fun getItemCount(): Int {
        return matchDataList.size
    }

    fun addMatchData(matchData: MatchData) {
        matchDataList.add(matchData)
        notifyItemInserted(matchDataList.size - 1)
    }
}