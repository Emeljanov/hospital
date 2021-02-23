package by.emel.anton.facade.patient;

public interface PatientFacade {

    ResponsePatientDTO getPatientById(int id);

    ResponsePatientDTO getPatientByLogin(String login);

}
