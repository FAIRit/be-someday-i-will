package pl.fairit.somedayiwill.movie.usersmovies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Movies {
    private List<MovieDto> movies;
}
