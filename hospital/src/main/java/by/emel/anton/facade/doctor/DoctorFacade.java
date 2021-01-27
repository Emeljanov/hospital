package by.emel.anton.facade.doctor;

public interface DoctorFacade {
//     ResponseDoctorDTO getDoctorById(String id);
     ResponseDoctorDTO getDoctorByLoginPassword(String login, String password);
}
