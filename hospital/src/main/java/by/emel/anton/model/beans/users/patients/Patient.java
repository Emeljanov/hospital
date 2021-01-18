package by.emel.anton.model.beans.users.patients;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.UserType;
import by.emel.anton.model.beans.users.doctors.Doctor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Patient extends User {

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "patient",cascade = CascadeType.ALL)
    private List<Therapy> therapies;

    public Patient() {
        setUserType(UserType.PATIENT);
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public List<Therapy> getTherapies() {
        return therapies;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setTherapies(List<Therapy> therapies) {
        this.therapies = therapies;
    }

    @Override
    public String toString() {
        String s = (null != doctor) ? String.valueOf(doctor.getId()) : "no doctor";

        return super.toString() + Constants.SEPARATOR + s + Constants.SEPARATOR
                + therapies.stream().map(Therapy::getId).collect(Collectors.toList());
    }
}
