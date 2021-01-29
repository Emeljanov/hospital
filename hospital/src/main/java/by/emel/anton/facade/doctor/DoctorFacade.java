package by.emel.anton.facade.doctor;

import by.emel.anton.facade.therapy.RequestTherapyDTO;
import by.emel.anton.model.dao.exceptions.TherapyDaoUncheckedException;
import by.emel.anton.model.dao.exceptions.UserDaoUncheckedException;

public interface DoctorFacade {

    ResponseDoctorDTO getDoctorByLoginPassword(String login, String password) throws UserDaoUncheckedException;

    void setPatientToDoctor(int doctorId, int patientId) throws UserDaoUncheckedException;

    void setTherapyToPatient(int doctorId, RequestTherapyDTO requestTherapyDTO) throws UserDaoUncheckedException, TherapyDaoUncheckedException;
}
