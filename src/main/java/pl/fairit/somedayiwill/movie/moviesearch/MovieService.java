package pl.fairit.somedayiwill.movie.moviesearch;

import pl.fairit.somedayiwill.movie.usersmovies.Movies;

public interface MovieService {

    Movies searchMovies(final String query);
}
