 package com.dev.assistir.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.leanback.app.DetailsFragment
import androidx.leanback.app.DetailsFragmentBackgroundController
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.leanback.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.dev.assistir.R

import com.dev.assistir.activitys.DetailsActivity
import com.dev.assistir.activitys.MainActivity
import com.dev.assistir.activitys.PlaybackActivity
import com.dev.assistir.activitys.VideoActivity
import com.dev.assistir.models.FilmeList
import com.dev.assistir.models.Filme
import com.dev.assistir.models.MovieToWatch
import com.dev.assistir.utils.MoviesRepository
import com.dev.assistir.viewHolders.CardPresenter
import com.dev.assistir.viewHolders.DetailsDescriptionPresenter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

class VideoDetailsFragment : DetailsFragment() {

    private lateinit var movieToWatch: MovieToWatch
    private lateinit var mDetailsBackground: DetailsFragmentBackgroundController
    private lateinit var mPresenterSelector: ClassPresenterSelector
    private lateinit var mAdapter: ArrayObjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mDetailsBackground = DetailsFragmentBackgroundController(this)

        val id = activity.intent.getSerializableExtra(DetailsActivity.MOVIE) as Long

        if (id != null){
            mPresenterSelector = ClassPresenterSelector()
            mAdapter = ArrayObjectAdapter(mPresenterSelector)

            mDetailsBackground.enableParallax()

            doAsync {
                movieToWatch = MoviesRepository().getImdbId(id)
                uiThread {
                    setupDetailsOverviewRow()
                    setupDetailsOverviewRowPresenter()
                    setupRelatedMovieListRow()
                    initializeBackground()
                }
            }

            adapter = mAdapter
            onItemViewClickedListener = ItemViewClickedListener()
        } else {
            val intent = Intent(context , MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initializeBackground() {

        Glide.with(context)
                .load( Filme.BASEIMGURL+movieToWatch.backdrop_path)
                .asBitmap()
                .centerCrop()
                .error(R.drawable.default_background)
                .into<SimpleTarget<Bitmap>>(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(bitmap: Bitmap,
                                                 glideAnimation: GlideAnimation<in Bitmap>
                    ) {
                        mDetailsBackground.coverBitmap = bitmap
                        println(bitmap.toString())
                        mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size())
                    }
                })
    }

    private fun setupDetailsOverviewRow() {
        val row = DetailsOverviewRow(movieToWatch)

        row.imageDrawable = ContextCompat.getDrawable(context, R.color.fastlane_background)

        val width = convertDpToPixel(context, DETAIL_THUMB_WIDTH)
        val height = convertDpToPixel(context, DETAIL_THUMB_HEIGHT)

        Glide.with(context)
                .load(Filme.BASEIMGURL+movieToWatch.poster_path)
                .centerCrop()
                .error(R.drawable.default_background)
                .into<SimpleTarget<GlideDrawable>>(object : SimpleTarget<GlideDrawable>(width, height) {
                    override fun onResourceReady(resource: GlideDrawable,
                                                 glideAnimation: GlideAnimation<in GlideDrawable>) {
                        Log.d(TAG, "FOTO PRONTA : " + resource)
                        row.imageDrawable = resource
                        mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size())
                    }
                })

        val actionAdapter = ArrayObjectAdapter()

        val icon : Drawable? = Drawable.createFromPath(R.drawable.ic_play_circle_outline_black_24dp.toString())

        actionAdapter.add(
            Action(
                ACTION_WATCH_MOVIE,
                "Assistir"," Agora", icon
            )
        )

        row.actionsAdapter = actionAdapter

        mAdapter.add(row)
    }

    private fun setupDetailsOverviewRowPresenter() {
        // Set detail background.
        val detailsPresenter = FullWidthDetailsOverviewRowPresenter(DetailsDescriptionPresenter())
        detailsPresenter.backgroundColor = ContextCompat.getColor(context, R.color.selected_background)

        // Hook up transition element.
        val sharedElementHelper = FullWidthDetailsOverviewSharedElementHelper()
        sharedElementHelper.setSharedElementEnterTransition(activity, DetailsActivity.SHARED_ELEMENT_NAME)

        detailsPresenter.setListener(sharedElementHelper)
        detailsPresenter.isParticipatingEntranceTransition = true

        detailsPresenter.onActionClickedListener = OnActionClickedListener { action ->
            println(action.id)
            if (action.id == ACTION_WATCH_MOVIE) {

                val intent = Intent(context, VideoActivity::class.java)

                intent.putExtra(DetailsActivity.MOVIE, movieToWatch.imdb_id)

                startActivity(intent)
            } else {
                Toast.makeText(context, action.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        println(detailsPresenter.toString())
        mPresenterSelector.addClassPresenter(DetailsOverviewRow::class.java, detailsPresenter)
    }

    private fun setupRelatedMovieListRow() {
        val subcategories = arrayOf(getString(R.string.related_movies))
        val list: List<List<Filme>> = FilmeList.list
        println(list)
        Collections.shuffle(list[0])
        val listRowAdapter = ArrayObjectAdapter(CardPresenter())

        list[1].map { movie ->
            listRowAdapter.add(movie)
        }

        val header = HeaderItem(0, subcategories[0])
        mAdapter.add(ListRow(header, listRowAdapter))
        mPresenterSelector.addClassPresenter(ListRow::class.java, ListRowPresenter())
    }

    private fun convertDpToPixel(context: Context, dp: Int): Int {
        val density = context.applicationContext.resources.displayMetrics.density
        return Math.round(dp.toFloat() * density)
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(
                itemViewHolder: Presenter.ViewHolder?,
                item: Any?,
                rowViewHolder: RowPresenter.ViewHolder,
                row: Row) {
            if (item is Filme) {

                val intent = Intent(context, DetailsActivity::class.java)

                intent.putExtra(resources.getString(R.string.movie), movieToWatch.imdb_id)

                val bundle = ActivityOptionsCompat
                    .makeSceneTransitionAnimation( activity,
                        (itemViewHolder?.view as ImageCardView).mainImageView,
                        DetailsActivity.SHARED_ELEMENT_NAME).toBundle()

                activity.startActivity(intent, bundle)
            }
        }
    }

    companion object {
        private val TAG = "VideoDetailsFragment"

        private val ACTION_WATCH_MOVIE = 1L

        private val DETAIL_THUMB_WIDTH = 404
        private val DETAIL_THUMB_HEIGHT = 574
    }

}