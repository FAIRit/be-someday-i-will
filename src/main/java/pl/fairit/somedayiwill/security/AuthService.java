package pl.fairit.somedayiwill.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fairit.somedayiwill.exceptions.UserAlreadyExistsException;
import pl.fairit.somedayiwill.user.AppUser;
import pl.fairit.somedayiwill.payload.LoginRequest;
import pl.fairit.somedayiwill.payload.SignUpRequest;
import pl.fairit.somedayiwill.user.UserService;

import javax.validation.Valid;

@Service
@Transactional
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public AuthService(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public String authenticateUser(@Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.createToken(authentication);
    }

    public AppUser registerUser(@Valid SignUpRequest signUpRequest) {
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            throw new UserAlreadyExistsException("Email address already in use.");
        }
        AppUser user = createUserFromSignupRequest(signUpRequest);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userService.saveUser(user);
    }

    private AppUser createUserFromSignupRequest(final SignUpRequest signUpRequest) {
        return AppUser.builder()
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .build();
    }
}
