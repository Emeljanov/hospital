package by.emel.anton.facade.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RequestUserDTO {
    @NotNull(message = "field login = null")
    private String login;
    @NotNull(message = "field password = null")
    private String password;
}
