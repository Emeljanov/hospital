package by.emel.anton.facade;

public interface PatientFacade {
    ResponsePatientDTO getPatientById(String id);

    ResponsePatientDTO getPatientByLogPass(String login, String password);
}
