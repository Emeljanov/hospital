package by.emel.anton.facade.doctor;

import by.emel.anton.facade.therapy.RequestTherapyDTO;
import by.emel.anton.service.exception.UserServiceException;

public interface DoctorFacade {

    void setPatientToDoctor(int patientId) throws UserServiceException;

    void setTherapyToPatient(RequestTherapyDTO requestTherapyDTO) throws UserServiceException;

    ResponseDoctorDTO getDoctorByLogin(String login) throws UserServiceException;
}
