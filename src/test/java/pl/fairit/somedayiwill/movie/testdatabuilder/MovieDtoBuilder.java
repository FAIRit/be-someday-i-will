package pl.fairit.somedayiwill.movie.testdatabuilder;

import pl.fairit.somedayiwill.movie.usersmovies.MovieDto;

import java.time.LocalDate;

public class MovieDtoBuilder {

    private Long id;
    private LocalDate releaseDate;
    private String genres;
    private String description;
    private String title;
    private String posterLink;

    private MovieDtoBuilder() {
    }

    public static MovieDtoBuilder aMovieDto() {
        return new MovieDtoBuilder();
    }

    public MovieDtoBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public MovieDtoBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public MovieDtoBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public MovieDtoBuilder withPosterLink(String posterLink) {
        this.posterLink = posterLink;
        return this;
    }

    public MovieDtoBuilder withGenres(String genres) {
        this.genres = genres;
        return this;
    }

    public MovieDtoBuilder withReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public MovieDto build() {
        return new MovieDto(id, genres, title, releaseDate, posterLink, description);
    }
}
