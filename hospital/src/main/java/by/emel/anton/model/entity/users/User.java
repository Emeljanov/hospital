package by.emel.anton.model.entity.users;

import by.emel.anton.constants.Constants;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String login;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('DOCTOR','PATIENT')", nullable = false)
    private UserType userType;

    @Column
    private String name;

    @Column
    private LocalDate birthday;

    public User() {
    }

   /* public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setUserType(UserType userType) {
        this.userType = userType;
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
    }*/

    @Override
    public String toString() {
        return String.join(Constants.SEPARATOR, String.valueOf(id), login, password, userType.toString(), name, birthday.toString());
    }
}
