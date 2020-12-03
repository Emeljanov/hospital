package by.emel.anton.model.beans.users.patients;

import by.emel.anton.model.beans.therapy.Therapy;

import java.time.LocalDate;
import java.util.List;

public abstract class Patient {
    private int id;
    private String name;
    private LocalDate birthday;
    private int doctorId;
    private List<Therapy> therapies;

    public Patient(int id, String name, LocalDate birthday, int doctorId, List<Therapy> therapies) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.doctorId = doctorId;
        this.therapies = therapies;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public List<Therapy> getTherapies() {
        return therapies;
    }

}
