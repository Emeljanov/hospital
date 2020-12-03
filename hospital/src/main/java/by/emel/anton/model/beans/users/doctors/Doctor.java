package by.emel.anton.model.beans.users.doctors;

import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.UserType;
import by.emel.anton.model.beans.users.patients.*;

import by.emel.anton.model.beans.therapy.Therapy;

import java.time.LocalDate;
import java.util.List;

public abstract class Doctor {

    private List<Integer> patientsId;

//    public Doctor(String login, String password, UserType userType, List<Integer> patientsId) {
//        super(login, password, userType);
//        this.patientsId = patientsId;
//    }
//
//    public Doctor(String login, String password, UserType userType, String name, LocalDate birthday, List<Integer> patientsId) {
//        super(login, password, userType, name, birthday);
//        this.patientsId = patientsId;
//    }

    public List<Integer> getPatientsId() {
        return patientsId;
    }
    public void setTherapy (Patient patient, Therapy therapy) {
        patient
                .getTherapies()
                .add(therapy);
    }
}
