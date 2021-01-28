package by.emel.anton.controller;

import by.emel.anton.facade.doctor.DoctorFacade;
import by.emel.anton.facade.doctor.RequestDoctorDTO;
import by.emel.anton.facade.doctor.ResponseDoctorDTO;
import by.emel.anton.facade.therapy.RequestTherapyDTO;
import by.emel.anton.model.dao.exceptions.TherapyDAOException;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorFacade doctorFacade;

    @PostMapping("/login")
    public ResponseDoctorDTO getDoctorByLoginPass(@RequestBody RequestDoctorDTO requestDoctorDTO) throws UserDAOException {

        String login = requestDoctorDTO.getLogin();
        String password = requestDoctorDTO.getPassword();

        return doctorFacade.getDoctorByLoginPassword(login, password);
    }

    @PostMapping("/login/setpatient")
    public void setPatientToDoctor(@SessionAttribute("doctorId") int doctorId ,@RequestParam int patientId) throws UserDAOException {

        doctorFacade.setPatientToDoctor(doctorId,patientId);
    }
    @PostMapping("/login/settherapy")
    public void setTherapyToPatient(@RequestBody RequestTherapyDTO requestTherapyDTO,@SessionAttribute("doctorId") int doctorId) throws UserDAOException, TherapyDAOException {

       doctorFacade.setTherapyToPatient(doctorId,requestTherapyDTO);


    }
}
   /* @GetMapping("/log")
    public void getSomeString(@SessionAttribute("abc") String abc) {
        System.out.println(abc);
    }*/

