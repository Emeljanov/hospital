package by.emel.anton.model.beans.users.doctors;

import by.emel.anton.constants.Constans;
import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.UserType;
import by.emel.anton.model.beans.users.patients.*;

import by.emel.anton.model.beans.therapy.Therapy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Doctor extends User {

    public Doctor(int id, String login, String password, String name, LocalDate birthday) {
        super(id,login, password, UserType.DOCTOR,name,birthday);
        this.patientsId = new ArrayList<>();
    }

    private List<Integer> patientsId;

    public void setPatientsId(List<Integer> patientsId) {
        this.patientsId = patientsId;
    }

    public void setPatientId(int id) {
        patientsId.add(id);
    }

    public List<Integer> getPatientsId() {
        return patientsId;
    }

    public void setTherapy (Patient patient, Therapy therapy) {
        patient
                .getTherapies()
                .add(therapy.getId());
    }

    @Override
    public String toString() {
        return super.toString() + Constans.SEPARATOR + patientsId.toString();
    }
}
