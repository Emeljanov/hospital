package by.emel.anton.facade.patient;

import by.emel.anton.model.dao.exceptions.UserDAOException;

public interface PatientFacade {

    ResponsePatientDTO getPatientById(int id) throws UserDAOException;

    ResponsePatientDTO getPatientByLogPass(String login, String password) throws UserDAOException;

}
