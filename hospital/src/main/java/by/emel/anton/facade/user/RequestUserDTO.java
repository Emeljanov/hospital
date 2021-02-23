package by.emel.anton.facade.user;

import javax.validation.constraints.NotNull;

public class RequestUserDTO {
    @NotNull(message = "field login = null")
    private String login;
    @NotNull(message = "field password = null")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
