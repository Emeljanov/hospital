package by.emel.anton.facade.doctor;

import by.emel.anton.facade.therapy.RequestTherapyDTO;
import by.emel.anton.model.dao.exceptions.UserDaoException;

public interface DoctorFacade {

    ResponseDoctorDTO getDoctorByLoginPassword(String login, String password);

    void setPatientToDoctor(int doctorId, int patientId);

    void setTherapyToPatient(int doctorId, RequestTherapyDTO requestTherapyDTO);

    ResponseDoctorDTO getDoctorByLogin(String login);
}
