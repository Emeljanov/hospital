package by.emel.anton.model.beans.users.patients;

import java.time.LocalDate;


public class OrdinaryPatient extends Patient {
    public OrdinaryPatient() {
    }

    public OrdinaryPatient(int id, String login, String password, String name, LocalDate birthday, int doctorId) {
        super(id, login, password, name, birthday, doctorId);
    }
}
