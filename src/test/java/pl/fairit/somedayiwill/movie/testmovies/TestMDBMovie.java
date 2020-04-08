package pl.fairit.somedayiwill.movie.testmovies;

import com.github.javafaker.Faker;
import pl.fairit.somedayiwill.movie.moviesearch.MDBMovie;

public class TestMDBMovie {
    public static MDBMovie aRandomMDBMovie() {
        var faker = new Faker();
        return MDBMovie.builder()
                .genre_ids(new Integer[]{1, 6, 13})
                .poster_path(faker.internet()
                        .url())
                .overview(faker.gameOfThrones()
                        .quote())
                .title(faker.harryPotter()
                        .house())
                .build();
    }
}
