package by.emel.anton.model.beans.users.doctors;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.UserType;
import by.emel.anton.model.beans.users.patients.*;
import by.emel.anton.model.beans.therapy.Therapy;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Doctor extends User {

    public Doctor() {
        setUserType(UserType.DOCTOR);
    }

    public Doctor(int id, String login, String password, String name, LocalDate birthday) {
        super(id,login, password, UserType.DOCTOR,name,birthday);
    }

//    private List<Integer> patientsId = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "doctor")
    private List<Patient> patients;


    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    /*public void setPatientId(int id) {

        if(!patientsId.contains(id)) {
            patientsId.add(id);
        }

    }*/

   /* public void setDoctorIdToPatient(Patient patient) {
        int doctorId = getId();
        patient.setDoctorId(doctorId);
    }*/



    public void setTherapy (Patient patient, Therapy therapy) {
        patient
                .getTherapies()
                .add(therapy.getId());
    }

    @Override
    public String toString() {
        return super.toString() + Constants.SEPARATOR + patients.stream().map(Patient::getId).collect(Collectors.toList());
    }
}
