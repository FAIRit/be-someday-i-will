package pl.fairit.somedayiwill.user;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fairit.somedayiwill.exceptions.ResourceNotFoundException;
import pl.fairit.somedayiwill.exceptions.UserAlreadyExistsException;

import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    final
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String email)
            throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
                );
        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(final Long id) {
        AppUser user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User " + id)
        );
        return UserPrincipal.create(user);
    }

    public AppUser createUser(final AppUser user) {
        throwIfUserWithGivenEmailExists(user.getEmail());
        return userRepository.save(user);
    }

    private UserDto mapAppUserToUserDto(final AppUser user) {
        return UserDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .books(user.getBooks())
                .movies(user.getMovies()).build();
    }

    public UserDto getUserDtoByAppUserId(final Long userId) {
        final AppUser user = findExistingUser(userId);
        return mapAppUserToUserDto(user);
    }


    public void deleteByUserId(Long id) {
        userRepository.deleteById(id);
    }

    private Optional<AppUser> findUserById(final Long id) {
        return userRepository.findById(id);
    }

    public AppUser findExistingUser(final Long id) {
        return findUserById(id).orElseThrow(() ->
                new ResourceNotFoundException("User with given id does not exist"));
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
