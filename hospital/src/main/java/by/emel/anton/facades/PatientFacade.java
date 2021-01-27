package by.emel.anton.facades;

public interface PatientFacade {
    ResponsePatientDTO getPatientById(String id);

    ResponsePatientDTO getPatientByLogPass(String login, String password);
}
