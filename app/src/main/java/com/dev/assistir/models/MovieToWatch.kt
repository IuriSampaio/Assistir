package com.dev.assistir.models

data class MovieToWatch(
    val imdb_id:String,
    val backdrop_path:String,
    val genres:Array<Genre>,
    val title:String,
    val overview:String,
    val poster_path:String,
    val release_date:String,
    val vote_average:Float
){
    override fun toString(): String {
        return "MOVIETOWATCH(imdb_id=$imdb_id, photo=$backdrop_path, genres: ${genres.map { genre -> genre.toString() }}, title= $title)"
    }
}

data class Genre(
    val id: Int,
    val name:String
) {

    override fun toString(): String {
        return this.name
    }
}

data class AllGenres(
    val genres : Array<Genre>
){

}