package pl.fairit.somedayiwill.movie.testmovies;

import pl.fairit.somedayiwill.movie.moviesearch.Genre;
import pl.fairit.somedayiwill.movie.moviesearch.Genres;

import java.util.HashMap;
import java.util.Map;

public class TestGenres {
    public static Genres genres() {
        return new Genres(new Genre[]{new Genre(28, "Action"), new Genre(12, "Adventure"), new Genre(16, "Animation"), new Genre(35, "Comedy")});
    }

    public static Map<Integer, String> asMap() {
        var genresMap = new HashMap<Integer, String>();
        genresMap.put(28, "Action");
        genresMap.put(12, "Adventure");
        genresMap.put(16, "Animation");
        genresMap.put(35, "Comedy");
        return genresMap;
    }
}
