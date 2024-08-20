package umc.everyones.lck.util

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import umc.everyones.lck.R

fun setupTeamSelection(view: View, onTeamSelected: (Int) -> Unit) {
    val teamViews = TeamData.teamLogos

    teamViews.forEach { (viewId, teamId) ->
        val teamView = view.findViewById<ImageView>(viewId)
        teamView.setOnClickListener {
            toggleTeamSelection(teamView, teamId)
            onTeamSelected(teamId)
        }
    }
}

private fun toggleTeamSelection(teamView: ImageView, teamId: Int) {
    // 이미 선택된 상태를 체크하는 로직이 필요할 수 있음
    teamView.setColorFilter(
        ContextCompat.getColor(teamView.context, R.color.selected),
        android.graphics.PorterDuff.Mode.SRC_IN
    )
}
