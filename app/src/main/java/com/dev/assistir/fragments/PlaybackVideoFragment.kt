package com.dev.assistir.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost
import androidx.leanback.media.MediaPlayerAdapter
import androidx.leanback.media.PlaybackTransportControlGlue
import androidx.leanback.widget.PlaybackControlsRow
import com.dev.assistir.activitys.DetailsActivity
import com.dev.assistir.models.Movie
import com.dev.assistir.models.MovieToWatch

/** Handles video playback with media controls. */
class PlaybackVideoFragment() : VideoSupportFragment() {

    private lateinit var mTransportControlGlue: PlaybackTransportControlGlue<MediaPlayerAdapter>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = activity?.intent?.getStringExtra(DetailsActivity.MOVIE)

        println("************************* $id")

        val glueHost = VideoSupportFragmentGlueHost(this@PlaybackVideoFragment)
        val playerAdapter = MediaPlayerAdapter(context)
        playerAdapter.setRepeatAction(PlaybackControlsRow.RepeatAction.INDEX_NONE)

        mTransportControlGlue = PlaybackTransportControlGlue(getActivity(), playerAdapter)
        mTransportControlGlue.host = glueHost
        mTransportControlGlue.playWhenPrepared()

       mTransportControlGlue.playerAdapter.setDataSource(Uri.parse( "https://streamvideo.link/getvideo?key=D8fKghz9o87wrJYX&video_id=$id"))
    }

    override fun onPause() {
        super.onPause()
        mTransportControlGlue.pause()
    }
}