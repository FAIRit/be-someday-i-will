package pl.fairit.somedayiwill.security;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fairit.somedayiwill.exceptions.ResourceNotFoundException;
import pl.fairit.somedayiwill.models.AppUser;
import pl.fairit.somedayiwill.repositories.UserRepository;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    final
    UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
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
}
