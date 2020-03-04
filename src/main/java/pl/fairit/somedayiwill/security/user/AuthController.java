package pl.fairit.somedayiwill.security.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.fairit.somedayiwill.login.AuthResponse;
import pl.fairit.somedayiwill.login.LoginRequest;
import pl.fairit.somedayiwill.signup.SignUpRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return new AuthResponse(authService.authenticateUser(loginRequest));
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public String signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.registerUser(signUpRequest);
        return "User registered successfully";
    }
}
