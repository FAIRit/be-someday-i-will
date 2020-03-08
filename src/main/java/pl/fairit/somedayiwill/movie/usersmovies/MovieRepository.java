package pl.fairit.somedayiwill.movie.usersmovies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findAllByUserId(Long userId);

    void deleteAllByUserId(Long userId);

}
