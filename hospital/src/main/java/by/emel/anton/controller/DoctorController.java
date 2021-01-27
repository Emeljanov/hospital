package by.emel.anton.controller;

import by.emel.anton.facade.doctor.DoctorFacade;
import by.emel.anton.facade.doctor.RequestDoctorDTO;
import by.emel.anton.facade.doctor.ResponseDoctorDTO;
import by.emel.anton.facade.patient.RequestPatientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorFacade doctorFacade;

    @PostMapping("/login")
    public ResponseDoctorDTO getDoctorByLoginPass(@RequestBody RequestDoctorDTO requestDoctorDTO) {
        String login = requestDoctorDTO.getLogin();
        String password = requestDoctorDTO.getPassword();
        return doctorFacade.getDoctorByLoginPassword(login,password);
    }
}
