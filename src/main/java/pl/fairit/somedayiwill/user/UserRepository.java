package pl.fairit.somedayiwill.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findAppUserById(Long id);

    @Override
    Optional<AppUser> findById(Long Long);

    Boolean existsByEmail(String email);
}
