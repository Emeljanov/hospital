package by.emel.anton.facade.patient;

import by.emel.anton.service.exception.UserServiceException;

public interface PatientFacade {

    ResponsePatientDTO getPatientById(int id) throws UserServiceException;

    ResponsePatientDTO getPatientByLogin(String login);

}
