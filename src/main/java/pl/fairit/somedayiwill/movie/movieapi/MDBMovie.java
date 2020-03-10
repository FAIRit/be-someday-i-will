package pl.fairit.somedayiwill.movie.movieapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MDBMovie {
    private String title;
    private String overview;
    private LocalDate release_date;
    private String poster_path;
    private Integer[] genre_ids;
}
