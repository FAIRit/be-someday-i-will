package pl.fairit.somedayiwill.avatar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.fairit.somedayiwill.avatar.Avatar;

import java.util.Optional;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    void deleteAvatarByUserId(Long userId);

    Optional<Avatar> findAvatarByUserId(Long userId);

}
