package com.dev.assistir.models

import com.dev.assistir.utils.MoviesRepository

object GenreList {
    val list :Array<String> by lazy { 
        getGenres()
    }

    private val genresList:List<Genre> = MoviesRepository().getGenres()
    
    private fun getGenres():Array<String>{
        val genre:Array<String> = genresList.map { genre ->
            genre.name
        }.toTypedArray()

        return genre
    }
    
    fun getIdByGenre(genreToFind:String) : Int{
        val genderFound = genresList.find { genre ->  genre.name == genreToFind }
    
        return genderFound!!.id
    }
}