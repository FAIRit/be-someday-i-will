package pl.fairit.somedayiwill.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private Long id;
    private String genres;
    private String title;
    private LocalDate releaseDate;
    private String posterLink;
    private String description;
    private int runtime;
}
