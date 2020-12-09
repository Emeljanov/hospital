package by.emel.anton.model.beans.users.patients;

import by.emel.anton.model.beans.users.UserType;

import java.time.LocalDate;


public class OrdinaryPatient extends Patient {
    public OrdinaryPatient() {
        setUserType(UserType.PATIENT);
    }

    public OrdinaryPatient(int id, String login, String password, String name, LocalDate birthday, int doctorId) {
        super(id, login, password, name, birthday, doctorId);
    }
}
