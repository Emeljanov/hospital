package by.emel.anton.facade.doctor;

import by.emel.anton.facade.therapy.RequestTherapyDTO;
import by.emel.anton.model.dao.exceptions.TherapyDAOException;
import by.emel.anton.model.dao.exceptions.UserDAOException;

public interface DoctorFacade {

    ResponseDoctorDTO getDoctorByLoginPassword(String login, String password) throws UserDAOException;

    void setPatientToDoctor(int doctorId, int patientId) throws UserDAOException;

    void setTherapyToPatient(int doctorId, RequestTherapyDTO requestTherapyDTO) throws UserDAOException, TherapyDAOException;
}
