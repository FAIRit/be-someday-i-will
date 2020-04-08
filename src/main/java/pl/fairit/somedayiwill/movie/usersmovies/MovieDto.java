package pl.fairit.somedayiwill.movie.usersmovies;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(notes = "The database generated movie ID")
    private Long id;

    @ApiModelProperty(notes = "The genres of the movie")
    private String genres;

    @NotNull
    @ApiModelProperty(notes = "The title of the movie")
    private String title;

    @ApiModelProperty(notes = "The release date of the movie")
    private LocalDate releaseDate;

    @ApiModelProperty(notes = "The poster URL")
    private String posterLink;

    @ApiModelProperty(notes = "The movie description")
    private String description;
}
