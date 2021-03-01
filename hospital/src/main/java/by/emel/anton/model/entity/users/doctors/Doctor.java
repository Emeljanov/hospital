package by.emel.anton.model.entity.users.doctors;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.model.entity.users.User;
import by.emel.anton.model.entity.users.UserType;
import by.emel.anton.model.entity.users.patients.Patient;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
public class Doctor extends User {

    public Doctor() {
        setUserType(UserType.DOCTOR);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
    private List<Patient> patients;

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void setTherapy(Patient patient, Therapy therapy) {
        patient.addTherapy(therapy);
    }

    @Override
    public String toString() {
        String patientsToString = patients.stream().map(Patient::getId).collect(Collectors.toList()).toString();

        return String.join(Constants.SEPARATOR, super.toString(), patientsToString);
    }
}
