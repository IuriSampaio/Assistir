package com.dev.assistir.utils

import com.dev.assistir.models.*
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class MoviesRepository {

    private val BASEURL =  "https://api.themoviedb.org/3/"
    private lateinit var PATHURL: String
    private val APIKEY = "96988444ffe703373ca42f07efb5b6fc"

    private val OKHTTPCLIENT = OkHttpClient()

    fun getMoviesInfo( page:Int, genreId:Int ) : List<Filme> {
        PATHURL = "discover/movie?sort_by=popularity.desc&language=pt-Br&page=$page&with_genres=$genreId"

        val request = Request.Builder().url("$BASEURL$PATHURL&api_key=$APIKEY").get().build()

        val response = OKHTTPCLIENT.newCall(request).execute()

        val responseBody = response.body()

        val json = responseBody!!.string()

        val Movies = Gson().fromJson(json, MoviesConstructor::class.java).results.toList()

        return Movies
    }

    fun getGenres():List<Genre>{

        PATHURL = "genre/movie/list"

        val request = Request.Builder().url("$BASEURL$PATHURL?api_key=$APIKEY&language=pt-Br").get().build()

        val response = OKHTTPCLIENT.newCall(request).execute()

        val responseBody = response.body()

        val json = responseBody!!.string()

        val genres = Gson().fromJson( json , AllGenres::class.java).genres.toList()

        return genres
    }

    fun getImdbId ( tmdbId:Long ) :MovieToWatch {

        PATHURL = "movie/$tmdbId"

        val request = Request.Builder().url("$BASEURL$PATHURL?api_key=$APIKEY&language=pt-Br").get().build()

        val response = OKHTTPCLIENT.newCall(request).execute()

        val responseBody = response.body()

        val json = responseBody!!.string()

        val Movie = Gson().fromJson(json, MovieToWatch::class.java)

        return Movie
    }

}