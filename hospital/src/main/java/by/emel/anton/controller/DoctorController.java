package by.emel.anton.controller;

import by.emel.anton.facade.doctor.DoctorFacade;
import by.emel.anton.facade.doctor.RequestDoctorDTO;
import by.emel.anton.facade.doctor.ResponseDoctorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorFacade doctorFacade;

    @PostMapping("/login")
    public ResponseDoctorDTO getDoctorByLoginPass(@RequestBody RequestDoctorDTO requestDoctorDTO, HttpSession httpSession) {
        httpSession.setAttribute("abc","bbc");

        String login = requestDoctorDTO.getLogin();
        String password = requestDoctorDTO.getPassword();
        return doctorFacade.getDoctorByLoginPassword(login,password);
    }

    @PostMapping("/login/setpatient")
    public void setPatientToDoctor(@RequestBody ResponseDoctorDTO responseDoctorDTO) {

    }
    @GetMapping("/log")
    public void getSomeString(@SessionAttribute("abc") String abc) {
        System.out.println(abc);
    }
}
