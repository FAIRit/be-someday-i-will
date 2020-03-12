package pl.fairit.somedayiwill.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.fairit.somedayiwill.avatar.AvatarStorageException;
import pl.fairit.somedayiwill.security.user.UserAlreadyExistsException;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException exp) {
        return new ErrorResponse("Failed to validate object");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(final Exception exp) {
        return new ErrorResponse(exp.getMessage());
    }

    @ExceptionHandler(AvatarStorageException.class)
    public ErrorResponse handleAvatarStorageException(final AvatarStorageException exp) {
        return new ErrorResponse(exp.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ErrorResponse handleUserAlreadyExistsException(final UserAlreadyExistsException exp) {
        return new ErrorResponse(exp.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ErrorResponse handleUsernameNotFoundException(final UsernameNotFoundException exp) {
        return new ErrorResponse(exp.getMessage());
    }
}
