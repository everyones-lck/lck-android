package umc.everyones.lck.presentation.lck

import ItemAboutLckViewHolder
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MatchVPAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val matchDataList: ArrayList<MatchData> = ArrayList()

    companion object {
        private const val TYPE_MATCH = 0
        private const val TYPE_NO_MATCHES = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_MATCH -> ItemAboutLckViewHolder.from(parent)
            TYPE_NO_MATCHES -> ItemAboutLckNoMatchesViewHolder.from(parent)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_MATCH -> (holder as ItemAboutLckViewHolder).bind(matchDataList[position])
            TYPE_NO_MATCHES -> (holder as ItemAboutLckNoMatchesViewHolder).bind()
        }
    }
    override fun getItemViewType(position: Int): Int {
        return if (position < matchDataList.size) {
            TYPE_MATCH
        } else {
            TYPE_NO_MATCHES
        }
    }

    override fun getItemCount(): Int {
        return matchDataList.size + 1
    }

    fun addMatchData(matchData: MatchData) {
        matchDataList.add(matchData)
        notifyItemInserted(matchDataList.size - 1)
    }
}