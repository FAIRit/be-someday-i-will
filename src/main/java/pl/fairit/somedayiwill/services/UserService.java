package pl.fairit.somedayiwill.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fairit.somedayiwill.exceptions.ResourceNotFoundException;
import pl.fairit.somedayiwill.exceptions.UserAlreadyExistsException;
import pl.fairit.somedayiwill.models.AppUser;
import pl.fairit.somedayiwill.repositories.UserRepository;
import pl.fairit.somedayiwill.security.UserPrincipal;

import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser createUser(final AppUser user) {
        throwIfUserWithGivenEmailExists(user.getEmail());
        return userRepository.save(user);
    }

    public void deleteByUserId(Long id) {
        userRepository.deleteById(id);
    }

    public AppUser getUserByPrincipal(final UserPrincipal principal) {
        final Long id = getUserId(principal);
        return findExistingUser(id);
    }

    public AppUser createOrUpdateUser(final UserPrincipal principal) {
        final AppUser user = principalToAppUser(principal);
        final Optional<AppUser> optionalUser = userRepository.findByEmail(user.getEmail());
        return optionalUser.orElseGet(() -> userRepository.save(user));
    }

    private AppUser principalToAppUser(final UserPrincipal principal) {
        final Map<String, Object> attributes = principal.getAttributes();
        return AppUser.builder()
                .name((String) attributes.get("family_name"))
                .email((String) attributes.get("email"))
                .build();
    }

    public Long getUserId(final UserPrincipal principal) {
        final Map<String, Object> attributes = principal.getAttributes();
        final String email = (String) attributes.get("email");
        final Optional<AppUser> optionalAppUser = userRepository.findByEmail(email);
        if (optionalAppUser.isPresent()) {
            return optionalAppUser.get().getId();
        }
        throw new ResourceNotFoundException("");
    }


    private Optional<AppUser> findUserById(final Long id) {
        return userRepository.findById(id);
    }

    public AppUser findExistingUser(final Long id) {
        return findUserById(id).orElseThrow(() ->
                new ResourceNotFoundException("User with given id does not exist"));
    }

    public void deleteByPrincipal(final UserPrincipal principal) {
        final Long id = getUserId(principal);
        userRepository.deleteById(id);
    }

    private void throwIfUserWithGivenEmailExists(final String email) {
        userRepository.findByEmail(email)
                .ifPresent(pl -> {
                    throw new UserAlreadyExistsException("User with given email already exists");
                });
    }

    public boolean existsByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<AppUser> findById(final Long id) {
        return userRepository.findById(id);
    }

}
