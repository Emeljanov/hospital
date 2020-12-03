package by.emel.anton.model.beans.users;

import java.time.LocalDate;

public abstract class User {
    private String login;
    private String password;
    private UserType userType;
    private String name;
    private LocalDate birthday;

    public User() {
    }

    public User(String login, String password, UserType userType) {
        this.login = login;
        this.password = password;
        this.userType = userType;
    }

    public User(String login, String password, UserType userType, String name, LocalDate birthday) {
        this.login = login;
        this.password = password;
        this.userType = userType;
        this.name = name;
        this.birthday = birthday;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

}
