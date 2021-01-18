package by.emel.anton.model.beans.users.patients;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.UserType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patient extends User {

    private int doctorId = 0;
    private List<Integer> therapies = new ArrayList<>();

    public Patient() {
        setUserType(UserType.PATIENT);
    }

    public Patient(int id, String login, String password, String name, LocalDate birthday, int doctorId) {
        super(id, login, password, UserType.PATIENT, name, birthday);
        this.doctorId = doctorId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public List<Integer> getTherapies() {
        return therapies;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public void setTherapies(List<Integer> therapies) {
        this.therapies = therapies;
    }

    @Override
    public String toString() {
        return super.toString() + Constants.SEPARATOR + doctorId + Constants.SEPARATOR + therapies.toString();
    }
}
