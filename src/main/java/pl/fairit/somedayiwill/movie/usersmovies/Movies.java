package pl.fairit.somedayiwill.movie.usersmovies;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel
public class Movies {
    @ApiModelProperty(notes = "The list of movies")
    private List<MovieDto> movies;
}
