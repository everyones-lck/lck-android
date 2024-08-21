package umc.everyones.lck.presentation.lck.adapter

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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

    inner class MatchDetailViewHolder(private val binding: ItemAboutLckMatchesDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        private val ivTeam1: ImageView = itemView.findViewById(R.id.iv_about_lck_team1)
        private val ivTeam2: ImageView = itemView.findViewById(R.id.iv_about_lck_team2)
        private val tvMatchTitle: TextView = itemView.findViewById(R.id.tv_match_title)
        private val tvMatchTime: TextView = itemView.findViewById(R.id.tv_match_time)

        @SuppressLint("SetTextI18n")
        fun bind(detail: AboutLckMatchDetailsModel.AboutLckMatchDetailsElementModel) {
            Log.d("detail", detail.toString())
            tvMatchTitle.text = formatMatchTitle(detail.season, detail.matchNumber)
            tvMatchTime.text = detail.matchTime.dropLast(3)

            loadTeamLogo(detail.team1.teamLogoUrl, ivTeam1, detail.team1.winner)
            loadTeamLogo(detail.team2.teamLogoUrl, ivTeam2, detail.team2.winner)
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

    class DiffCallback : DiffUtil.ItemCallback<AboutLckMatchDetailsModel.AboutLckMatchDetailsElementModel>() {
        override fun areItemsTheSame(oldItem: AboutLckMatchDetailsModel.AboutLckMatchDetailsElementModel, newItem: AboutLckMatchDetailsModel.AboutLckMatchDetailsElementModel) =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: AboutLckMatchDetailsModel.AboutLckMatchDetailsElementModel, newItem: AboutLckMatchDetailsModel.AboutLckMatchDetailsElementModel) =
            oldItem == newItem
    }
}
