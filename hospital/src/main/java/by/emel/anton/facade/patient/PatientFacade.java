package by.emel.anton.facade.patient;

public interface PatientFacade {

    ResponsePatientDTO getPatientById(String id);

    ResponsePatientDTO getPatientByLogPass(String login, String password);

}
