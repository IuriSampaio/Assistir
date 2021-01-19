package com.dev.assistir.viewHolders

import androidx.leanback.widget.AbstractDetailsDescriptionPresenter
import com.dev.assistir.models.Filme
import com.dev.assistir.models.MovieToWatch

class DetailsDescriptionPresenter : AbstractDetailsDescriptionPresenter() {

    private lateinit var movie :MovieToWatch

    override fun onBindDescription(
            viewHolder: AbstractDetailsDescriptionPresenter.ViewHolder,
            item: Any) {
        movie= item as MovieToWatch
        viewHolder.title.text = movie.title
        viewHolder.subtitle.text = "Nota no IMDB: ${movie.vote_average}"
        viewHolder.body.text = movie.overview
    }
}