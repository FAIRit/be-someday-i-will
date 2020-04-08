package pl.fairit.somedayiwill.exceptions;


import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.fairit.somedayiwill.avatar.AvatarStorageException;
import pl.fairit.somedayiwill.security.user.UserAlreadyExistsException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleGlobalException(final Exception exp) {
        return new ErrorResponse(LocalDateTime.now(), List.of(exp.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException exp) {
        var validationMessage = exp.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return new ErrorResponse(LocalDateTime.now(), validationMessage);
    }

    @ExceptionHandler(AvatarStorageException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ResponseBody
    ErrorResponse handleAvatarStorageException(final AvatarStorageException exp) {
        return new ErrorResponse(LocalDateTime.now(), List.of(exp.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    ErrorResponse handleUserAlreadyExistsException(final UserAlreadyExistsException exp) {
        return new ErrorResponse(List.of(exp.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ErrorResponse handleUsernameNotFoundException(final UsernameNotFoundException exp) {
        return new ErrorResponse(LocalDateTime.now(), List.of(exp.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ErrorResponse handleResourceNotFoundException(final ResourceNotFoundException exp) {
        return new ErrorResponse(LocalDateTime.now(), List.of(exp.getMessage()));
    }
}
