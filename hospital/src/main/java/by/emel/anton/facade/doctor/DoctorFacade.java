package by.emel.anton.facade.doctor;

import by.emel.anton.facade.therapy.RequestTherapyDTO;

public interface DoctorFacade {

    void setPatientToDoctor(int patientId);

    void setTherapyToPatient(RequestTherapyDTO requestTherapyDTO);

    ResponseDoctorDTO getDoctorByLogin(String login);
}
