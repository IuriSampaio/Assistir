package com.dev.assistir.models

import com.dev.assistir.utils.MoviesRepository
import com.dev.assistir.models.GenreList
import org.jetbrains.anko.doAsync

object FilmeList{

    lateinit var MOVIE_CATEGORY : Array<String>

    val list: List<List<Filme>> by lazy {
        setupMovies()
    }

    private fun setupMovies(): List<List<Filme>> {

        val movies = MoviesRepository()

        MOVIE_CATEGORY = GenreList.list

        val list = MOVIE_CATEGORY.map { category ->
            movies.getMoviesInfo(1, GenreList.getIdByGenre(category))
        }

        return list
    }
}