package pl.fairit.somedayiwill.movie;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    MovieDto mapToDto(Movie movie);

    Movie mapToMovie(MovieDto movieDto);
}
