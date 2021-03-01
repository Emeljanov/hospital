package by.emel.anton.facade.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseExceptionDTO {
    private String name;
    private String message;
}
