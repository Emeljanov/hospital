package by.emel.anton.controller;

import by.emel.anton.facade.converter.Converter;
import by.emel.anton.facade.exception.ResponseExceptionDTO;
import by.emel.anton.service.exception.UserServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    private Converter<Exception, ResponseExceptionDTO> converter;

    @Autowired
    public ControllerExceptionHandler(Converter<Exception, ResponseExceptionDTO> converter) {
        this.converter = converter;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserServiceException.class)
    public ResponseExceptionDTO userServiceExceptionHandler(UserServiceException userServiceException) {
        log.error(userServiceException.getMessage());
        return converter.convert(userServiceException);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResponseExceptionDTO exceptionHandler(Exception exception) {
        log.error("Controller exception :" + exception.getMessage());
        return converter.convert(exception);
    }

}
