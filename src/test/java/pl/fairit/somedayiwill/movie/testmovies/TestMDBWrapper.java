package pl.fairit.somedayiwill.movie.testmovies;

import pl.fairit.somedayiwill.movie.moviesearch.MDBMovie;
import pl.fairit.somedayiwill.movie.moviesearch.MDBWrapper;

public class TestMDBWrapper {
    public static MDBWrapper aWrapperWithMultipleMDBMovies(final int numberOfMDBMovies) {
        var array = new MDBMovie[numberOfMDBMovies];
        for (int i = 0; i < numberOfMDBMovies; i++) {
            array[i] = TestMDBMovie.aRandomMDBMovie();
        }
        return new MDBWrapper(numberOfMDBMovies, array);

    }
}
