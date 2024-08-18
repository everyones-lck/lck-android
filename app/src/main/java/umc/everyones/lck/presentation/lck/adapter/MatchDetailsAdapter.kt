package umc.everyones.lck.presentation.lck.adapter

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import umc.everyones.lck.R
import umc.everyones.lck.presentation.lck.data.MatchData
import javax.annotation.Nullable
import javax.sql.DataSource

class MatchDetailsAdapter(private val matchDetails: List<MatchData>) :
    RecyclerView.Adapter<MatchDetailsAdapter.MatchDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchDetailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_about_lck_matches_detail, parent, false)
        return MatchDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchDetailViewHolder, position: Int) {
        val detail = matchDetails[position]
        holder.bind(detail)
    }

    override fun getItemCount(): Int = matchDetails.size

    inner class MatchDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivTeam1: ImageView = itemView.findViewById(R.id.iv_about_lck_team1)
        private val ivTeam2: ImageView = itemView.findViewById(R.id.iv_about_lck_team2)
        private val tvMatchTitle: TextView = itemView.findViewById(R.id.tv_match_title)
        private val tvMatchTime: TextView = itemView.findViewById(R.id.tv_match_time)

        fun bind(detail: MatchData) {
            tvMatchTitle.text = detail.matchTitle
            tvMatchTime.text = detail.matchTime

            loadTeamLogo(detail.teamLogoUrl1, ivTeam1, detail.isTeam1Winner)
            loadTeamLogo(detail.teamLogoUrl2, ivTeam2, detail.isTeam2Winner)
        }

        private fun loadTeamLogo(url: String?, imageView: ImageView, isWinner: Boolean) {
            if (url.isNullOrEmpty()) {
                imageView.visibility = View.GONE
                return
            }

            Glide.with(imageView.context)
                .asBitmap()
                .load(url)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        if (!isWinner) {
                            val grayscaleBitmap = convertToGrayscale(resource)
                            imageView.setImageBitmap(grayscaleBitmap)
                        } else {
                            imageView.setImageBitmap(resource)
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        Log.e("MatchDetailsAdapter", "Failed to load image: $url")
                        imageView.visibility = View.GONE
                    }
                })
        }

        //흑백변환 코드
        private fun convertToGrayscale(bitmap: Bitmap): Bitmap {
            val width = bitmap.width
            val height = bitmap.height

            val grayscaleBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(grayscaleBitmap)
            val paint = Paint()

            val colorMatrix = ColorMatrix()
            colorMatrix.setSaturation(0f) // 흑백으로 변환

            val filter = ColorMatrixColorFilter(colorMatrix)
            paint.colorFilter = filter
            canvas.drawBitmap(bitmap, 0f, 0f, paint)

            return grayscaleBitmap
        }
    }
}
