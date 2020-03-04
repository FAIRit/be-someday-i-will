package pl.fairit.somedayiwill.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.fairit.somedayiwill.user.AppUser;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByUserId(Long userId);

    void deleteAllByUserId(Long userId);
}
