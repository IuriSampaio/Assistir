package com.dev.assistir.models

data class Filme(
   val id: Long,
   val backdrop_path:String,
   val title:String,
   val genre_ids: Array<Int>,
   val overview:String,
   val poster_path:String,
   val release_date:String,
   val vote_average:Float
) {

    companion object {
        val BASEIMGURL = "https://image.tmdb.org/t/p/w300/"
    }

    override fun toString(): String {
        return "FILME(id=$id,ft1=$backdrop_path,ft2=$poster_path,desc=$overview)"
    }

}