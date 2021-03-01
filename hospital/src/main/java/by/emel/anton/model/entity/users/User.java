package by.emel.anton.model.entity.users;

import by.emel.anton.constants.Constants;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
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

    @Override
    public String toString() {
        return String.join(Constants.SEPARATOR, String.valueOf(id), login, password, userType.toString(), name, birthday.toString());
    }
}
