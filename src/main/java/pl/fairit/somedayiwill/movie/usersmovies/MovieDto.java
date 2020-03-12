package pl.fairit.somedayiwill.movie.usersmovies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private Long id;
    private String genres;
    @NotNull
    private String title;
    private LocalDate releaseDate;
    private String posterLink;
    private String description;
}
