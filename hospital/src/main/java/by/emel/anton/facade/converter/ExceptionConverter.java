package by.emel.anton.facade.converter;

import by.emel.anton.facade.exception.ResponseExceptionDTO;
import org.springframework.stereotype.Component;

@Component
public class ExceptionConverter implements Converter<Exception, ResponseExceptionDTO> {
    @Override
    public ResponseExceptionDTO convert(Exception from) {
        return ResponseExceptionDTO.builder()
                .name(from.getClass().getSimpleName())
                .message(from.getMessage())
                .build();
    }
}
