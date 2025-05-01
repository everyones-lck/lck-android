package umc.everyones.lck.presentation.lck.adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import umc.everyones.lck.R
import umc.everyones.lck.databinding.ItemAboutLckMatchesDetailBinding
import umc.everyones.lck.domain.model.about_lck.AboutLckMatchDetailsModel
import umc.everyones.lck.presentation.lck.data.MatchData
import umc.everyones.lck.util.extension.formatMatchTitle
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.annotation.Nullable
import javax.sql.DataSource

class MatchDetailsAdapter() :
    ListAdapter<AboutLckMatchDetailsModel.AboutLckMatchDetailsElementModel, MatchDetailsAdapter.MatchDetailViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchDetailViewHolder {
        return MatchDetailViewHolder(
            ItemAboutLckMatchesDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MatchDetailViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private fun getTeamColorByName(teamName: String): Int {
        return when (teamName.uppercase()) {
            "GEN.G", "GEN" -> Color.parseColor("#AA8B30")
            "HLE", "HANWHA" -> Color.parseColor("#F3741B")
            "DK" -> Color.parseColor("#FFFFFF")
            "T1", "KT", "KDF", "NS", "DNF" -> Color.parseColor("#E91B3B")
            "DRX" -> Color.parseColor("#0017E7")
            "BNK" -> Color.parseColor("#F8E52F")
            "OK BRION", "BRO" -> Color.parseColor("#003202")
            else -> Color.WHITE
        }
    }

    private fun applyTeamCircleColorByName(view: ImageView, teamName: String?) {
        val color = teamName?.let { getTeamColorByName(it) } ?: Color.WHITE
        val drawable = view.background.mutate()
        drawable.setTint(color)
        view.background = drawable
    }

    private fun formatMatchDateTime(matchDate: String, matchTime: String): String {
        return try {
            val input = "$matchDate $matchTime" // "2024-12-27 15:00:00"
            val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val outputFormat = DateTimeFormatter.ofPattern("MM.dd HH:mm")

            val dateTime = LocalDateTime.parse(input, inputFormat)
            dateTime.format(outputFormat)
        } catch (e: Exception) {
            "Invalid Date"
        }
    }

    inner class MatchDetailViewHolder(private val binding: ItemAboutLckMatchesDetailBinding) : RecyclerView.ViewHolder(binding.root) {


        @SuppressLint("SetTextI18n")
        fun bind(detail: AboutLckMatchDetailsModel.AboutLckMatchDetailsElementModel?) {
            with(binding) {
                if (detail == null) {
                    tvMatchTime.text = "00.00 00:00"
                    tvMatchTitle.text = "No Match"
                    tvMatchTeam1Name.text = "-"

                    tvMatchVs.visibility = View.INVISIBLE
                    tvMatchTeam2Name.visibility = View.INVISIBLE
                    applyTeamCircleColorByName(ivMatchTeam1, null)
                    applyTeamCircleColorByName(ivMatchTeam2, null)
                    return
                }

                tvMatchTitle.text = formatMatchTitle(detail.season, detail.matchNumber)
                tvMatchTime.text = formatMatchDateTime(detail.matchDate, detail.matchTime)

//                if (detail.matchFinished) {
//                    val winningTeamName = if (detail.team1.winner) {
//                        detail.team1.teamName
//                    } else {
//                        detail.team2.teamName
//                    }
//                    tvMatchTime.text = "WIN | $winningTeamName"
//                } else {
//                    tvMatchTime.text = detail.matchTime.dropLast(3)
//                }

                applyTeamCircleColorByName(ivMatchTeam1, detail.team1.teamName)
                applyTeamCircleColorByName(ivMatchTeam2, detail.team2.teamName)

                tvMatchTeam1Name.text = detail.team1.teamName
                tvMatchTeam2Name.text = detail.team2.teamName
            }
        }

        private fun loadTeamLogo(url: String?, imageView: ImageView, isWinner: Boolean, isMatchFinished: Boolean) {
            if (url.isNullOrEmpty()) {
                imageView.visibility = View.INVISIBLE
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
                        if (isMatchFinished && !isWinner) {
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
                        imageView.visibility = View.INVISIBLE
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

    class DiffCallback : DiffUtil.ItemCallback<AboutLckMatchDetailsModel.AboutLckMatchDetailsElementModel>() {
        override fun areItemsTheSame(oldItem: AboutLckMatchDetailsModel.AboutLckMatchDetailsElementModel, newItem: AboutLckMatchDetailsModel.AboutLckMatchDetailsElementModel) =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: AboutLckMatchDetailsModel.AboutLckMatchDetailsElementModel, newItem: AboutLckMatchDetailsModel.AboutLckMatchDetailsElementModel) =
            oldItem == newItem
    }
}
