package pl.fairit.somedayiwill.user;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fairit.somedayiwill.exceptions.ResourceNotFoundException;
import pl.fairit.somedayiwill.mailsender.NewsletterFrequency;
import pl.fairit.somedayiwill.mailsender.SendGridEmailService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final SendGridEmailService sendGridEmailService;

    public UserService(UserRepository userRepository, SendGridEmailService sendGridEmailService) {
        this.userRepository = userRepository;
        this.sendGridEmailService = sendGridEmailService;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found with email : " + email)
        );
        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(final Long id) {
        AppUser user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User " + id)
        );
        return UserPrincipal.create(user);
    }

    public AppUser saveUser(final AppUser user) {
        sendGridEmailService.sendRegisterConfirmationEmail(user);
        return userRepository.save(user);
    }

    public UserDto getUserDtoByAppUserId(final Long userId) {
        final AppUser user = findExistingUser(userId);
        return UserMapper.INSTANCE.userToUserDto(user);
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

    public List<AppUser> findAllUsersForMonthlyNewsletter() {
        return userRepository.findAllByNewsletterFrequency(NewsletterFrequency.MONTHLY);
    }

    public List<AppUser> findAllUsersForWeeklyNewsletter() {
        return userRepository.findAllByNewsletterFrequency(NewsletterFrequency.WEEKLY);
    }

    public boolean existsByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }
}
