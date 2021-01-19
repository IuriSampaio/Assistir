package com.dev.assistir.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.leanback.app.OnboardingFragment
import androidx.leanback.app.OnboardingSupportFragment
import com.dev.assistir.R
import kotlinx.android.synthetic.main.web_view.*
import kotlinx.android.synthetic.main.web_view.view.*


class WebViewFragment : OnboardingFragment(){

    private lateinit var wview: WebView
    override fun getPageCount(): Int {
        return 1
    }

    override fun getPageTitle(pageIndex: Int): CharSequence {
        return String()
    }

    override fun getPageDescription(pageIndex: Int): CharSequence {
        return String()
    }

    override fun onCreateBackgroundView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        wview = webview as WebView
        wview.loadUrl("https://www.google.co.in")
        return wview
    }

    override fun onCreateContentView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        wview = webview as WebView
        wview.loadUrl("https://www.google.co.in")
        return wview
    }

    override fun onCreateForegroundView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        wview = webview as WebView
        wview.loadUrl("https://www.google.co.in")
        return wview
    }
}