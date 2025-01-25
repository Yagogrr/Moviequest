package com.example.moviequest

class MovieProvider {
    companion object {
        var movieList = listOf<Movie>(
            Movie("Cadena Perpetua", "Action", R.drawable.shawshank_redemption),
            Movie("From Dusk Until Dawn", "Action", R.drawable.abierto_hasta_el_amanecer),
            Movie("Fight Club", "Mistery", R.drawable.fight_club),
            Movie("Shutter Island", "Thriller", R.drawable.shutter_island),
            Movie("Bilchus", "Comedy", R.drawable.bitelchus),
            Movie("Influencia", "Terror", R.drawable.influencia),
            Movie("Rampage", "Action", R.drawable.rampage),
            Movie("Moana", "Cartoon", R.drawable.moana),
            Movie("Smile", "Terror", R.drawable.smile)
        )
    }
}