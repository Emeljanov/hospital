package by.emel.anton.controller;

import by.emel.anton.facade.exception.ResponseExceptionDTO;
import by.emel.anton.facade.converter.Converter;
import by.emel.anton.model.dao.exceptions.TherapyDaoException;
import by.emel.anton.model.dao.exceptions.UserDaoException;
import by.emel.anton.service.exception.UserServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    private Converter<Exception, ResponseExceptionDTO> converter;

    @Autowired
    public ControllerExceptionHandler(Converter<Exception, ResponseExceptionDTO> converter) {
        this.converter = converter;
    }

    public ResponseExceptionDTO userServiceExceptionHandler(UserServiceException userServiceException) {
        logger.error(userServiceException.getMessage());
        return converter.convert(userServiceException);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserDaoException.class)
    public void userDaoExceptionHandler(UserDaoException userDaoException) {
        logger.error(userDaoException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TherapyDaoException.class)
    public void therapyDaoExceptionHandler(TherapyDaoException therapyDaoException) {
        logger.error(therapyDaoException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public void exceptionHandler(Exception exception) {
        logger.error("Controller exception :" + exception.getMessage());
    }

}
