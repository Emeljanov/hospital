package by.emel.anton.model.entity.users.patients;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.model.entity.users.User;
import by.emel.anton.model.entity.users.UserType;
import by.emel.anton.model.entity.users.doctors.Doctor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
public class Patient extends User {

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Therapy> therapies;

    public Patient() {
        setUserType(UserType.PATIENT);
    }

    public void addTherapy(Therapy therapy) {
        therapies.add(therapy);
    }

    @Override
    public String toString() {
        String doctorId = (null != doctor) ? String.valueOf(doctor.getId()) : "no doctor";
        String therapiesToString = therapies.stream().map(Therapy::getId).collect(Collectors.toList()).toString();

        return String.join(Constants.SEPARATOR, super.toString(), doctorId, therapiesToString);
    }
}
