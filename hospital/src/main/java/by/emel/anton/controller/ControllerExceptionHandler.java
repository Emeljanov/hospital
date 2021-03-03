package by.emel.anton.controller;

import by.emel.anton.config.security.JwtAuthenticationException;
import by.emel.anton.facade.converter.Converter;
import by.emel.anton.facade.exception.ResponseExceptionDTO;
import by.emel.anton.service.exception.UserServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class ControllerExceptionHandler {

    private Converter<Exception, ResponseExceptionDTO> converter;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserServiceException.class)
    public ResponseExceptionDTO userServiceExceptionHandler(UserServiceException userServiceException) {
        log.error(userServiceException.getMessage());
        return converter.convert(userServiceException);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseExceptionDTO jwtAuthenticationExceptionHandler(JwtAuthenticationException jwtAuthenticationException) {
        log.error(jwtAuthenticationException.getMessage());
        return converter.convert(jwtAuthenticationException);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseExceptionDTO badCredentialsAuthenticationExceptionHandler(BadCredentialsException badCredentialsException) {
        log.error(badCredentialsException.getMessage());
        return converter.convert(badCredentialsException);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResponseExceptionDTO exceptionHandler(Exception exception) {
        log.error("Controller exception :" + exception.getMessage());
        return converter.convert(exception);
    }


}
