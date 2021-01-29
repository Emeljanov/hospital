package by.emel.anton.facade.patient;

import by.emel.anton.model.dao.exceptions.UserDaoUncheckedException;

public interface PatientFacade {

    ResponsePatientDTO getPatientById(int id) throws UserDaoUncheckedException;

    ResponsePatientDTO getPatientByLogPass(String login, String password) throws UserDaoUncheckedException;

}
