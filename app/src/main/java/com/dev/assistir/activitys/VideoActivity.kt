package com.dev.assistir.activitys

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.dev.assistir.R
import com.dev.assistir.fragments.PlaybackVideoFragment

class VideoActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, PlaybackVideoFragment())
                .commit()
        }
    }
}
