package pl.fairit.somedayiwill.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.fairit.somedayiwill.user.AppUser;
import pl.fairit.somedayiwill.payload.ApiResponse;
import pl.fairit.somedayiwill.payload.AuthResponse;
import pl.fairit.somedayiwill.payload.LoginRequest;
import pl.fairit.somedayiwill.payload.SignUpRequest;
import pl.fairit.somedayiwill.security.AuthService;

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
    public AuthResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return new AuthResponse(authService.authenticateUser(loginRequest));
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        AppUser result = authService.registerUser(signUpRequest);
        //        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("api/users/me")
//                .buildAndExpand(result.getId()).toUri();
//        return ResponseEntity.created(location)
//                .body(new ApiResponse(true, "User registered successfully"));
        return new ApiResponse(true, "User registered successfully");
    }
}
