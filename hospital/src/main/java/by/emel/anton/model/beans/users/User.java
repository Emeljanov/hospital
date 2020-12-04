package by.emel.anton.model.beans.users;

import by.emel.anton.constants.Constans;

import java.time.LocalDate;

public abstract class User {
    private int id;
    private String login;
    private String password;
    private UserType userType;
    private String name;
    private LocalDate birthday;

    public User(int id,String login, String password, UserType userType, String name, LocalDate birthday) {
        this.id =id;
        this.login = login;
        this.password = password;
        this.userType = userType;
        this.name = name;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
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

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        String result =
                        id + Constans.SEPARATOR +
                        login + Constans.SEPARATOR +
                        password + Constans.SEPARATOR +
                        userType.toString() + Constans.SEPARATOR +
                        name + Constans.SEPARATOR +
                        birthday;
        return result;
    }
}
