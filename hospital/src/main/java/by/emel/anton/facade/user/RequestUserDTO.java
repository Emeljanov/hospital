package by.emel.anton.facade.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class RequestUserDTO {
    @NotNull(message = "field login = null")
    private String login;
    @NotNull(message = "field password = null")
    private String password;
}
