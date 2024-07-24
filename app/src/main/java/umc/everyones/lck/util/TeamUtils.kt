package umc.everyones.lck.util

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import umc.everyones.lck.R

fun setupTeamSelection(view: View, onTeamSelected: (String) -> Unit) {
    val teamViews = TeamData.teamLogos

    teamViews.forEach { (viewId, teamName) ->
        val teamView = view.findViewById<ImageView>(viewId)
        teamView.setOnClickListener {
            toggleTeamSelection(teamView, teamName)
            onTeamSelected(teamName)
        }
    }
}

private fun toggleTeamSelection(teamView: ImageView, teamName: String) {
    // 이미 선택된 상태를 체크하는 로직이 필요할 수 있음
    teamView.setColorFilter(
        ContextCompat.getColor(teamView.context, R.color.selected),
        android.graphics.PorterDuff.Mode.SRC_IN
    )
}
