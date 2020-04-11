package pl.fairit.somedayiwill.security.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fairit.somedayiwill.exceptions.ResourceNotFoundException;
import pl.fairit.somedayiwill.user.AppUserRepository;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    public CustomUserDetailsService(final AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) {
        var user = appUserRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found with email : " + email)
        );
        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(final Long id) {
        var user = appUserRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User " + id)
        );
        return UserPrincipal.create(user);
    }
}
