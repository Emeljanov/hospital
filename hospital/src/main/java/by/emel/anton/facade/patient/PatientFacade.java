package by.emel.anton.facade.patient;

public interface PatientFacade {

    ResponsePatientDTO getPatientById(int id);

    ResponsePatientDTO getPatientByLogPass(String login, String password);

    ResponsePatientDTO getPatientByLogin(String login);

}
