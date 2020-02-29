package pl.fairit.somedayiwill.movie;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.fairit.somedayiwill.user.AppUser;
import pl.fairit.somedayiwill.user.UserDto;

@Mapper
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    MovieDto movieToMovieDto(Movie movie);
}
