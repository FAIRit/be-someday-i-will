package pl.fairit.somedayiwill.movie.testdatabuilder;

import pl.fairit.somedayiwill.movie.usersmovies.Movie;
import pl.fairit.somedayiwill.user.AppUser;

import java.time.LocalDate;

public class MovieBuilder {
    public static final AppUser DEFAULT_USER = new AppUser();

    private Long id;
    private AppUser user = DEFAULT_USER;
    private LocalDate releaseDate;
    private String genres;
    private String description;
    private String title;
    private String posterLink;

    private MovieBuilder() {
    }

    public static MovieBuilder aMovie() {
        return new MovieBuilder();
    }

    public MovieBuilder withUser(AppUser user) {
        this.user = user;
        return this;
    }

    public MovieBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public MovieBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public MovieBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public MovieBuilder withPosterLink(String posterLink) {
        this.posterLink = posterLink;
        return this;
    }

    public MovieBuilder withGenres(String genres) {
        this.genres = genres;
        return this;
    }

    public MovieBuilder withReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public Movie build() {
        return new Movie(id, user, genres, title, releaseDate, posterLink, description);
    }
}
