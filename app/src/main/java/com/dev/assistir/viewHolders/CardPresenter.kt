package com.dev.assistir.viewHolders

import android.graphics.drawable.Drawable
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import androidx.core.content.ContextCompat
import android.util.Log
import android.view.ViewGroup

import com.bumptech.glide.Glide
import com.dev.assistir.R
import com.dev.assistir.models.Filme
import com.dev.assistir.models.MovieToWatch
import kotlin.properties.Delegates

class CardPresenter : Presenter() {
    private lateinit var movie:Filme
    private var mDefaultCardImage: Drawable? = null
    private var sSelectedBackgroundColor: Int by Delegates.notNull()
    private var sDefaultBackgroundColor: Int by Delegates.notNull()

    override fun onCreateViewHolder(parent: ViewGroup): Presenter.ViewHolder {
        sDefaultBackgroundColor = ContextCompat.getColor(parent.context, R.color.default_background)
        sSelectedBackgroundColor = ContextCompat.getColor(parent.context, R.color.selected_background)
        mDefaultCardImage = ContextCompat.getDrawable(parent.context, R.drawable.movie)

        val cardView = object : ImageCardView(parent.context) {
            override fun setSelected(selected: Boolean) {
                updateCardBackgroundColor(this, selected)
                super.setSelected(selected)
            }
        }

        cardView.isFocusable = true
        cardView.isFocusableInTouchMode = true
        updateCardBackgroundColor(cardView, false)
        return Presenter.ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        movie = item as Filme

        val cardView = viewHolder.view as ImageCardView

        cardView.titleText = movie.title
        cardView.contentText = movie.release_date
        cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT)

        if (movie.backdrop_path != null) {
            Glide.with(viewHolder.view.context)
                    .load(Filme.BASEIMGURL+movie.backdrop_path)
                    .centerCrop()
                    .error(mDefaultCardImage)
                    .into(cardView.mainImageView)
        }
    }

    override fun onUnbindViewHolder(viewHolder: Presenter.ViewHolder) {
        val cardView = viewHolder.view as ImageCardView

        cardView.badgeImage = null
        cardView.mainImage = null
    }

    private fun updateCardBackgroundColor(view: ImageCardView, selected: Boolean) {
        val color = if (selected) sSelectedBackgroundColor else sDefaultBackgroundColor

        view.setBackgroundColor(color)
        view.setInfoAreaBackgroundColor(color)
    }

    companion object {
        private val CARD_WIDTH = 313
        private val CARD_HEIGHT = 176
    }
}
