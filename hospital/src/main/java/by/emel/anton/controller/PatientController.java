package by.emel.anton.controller;

import by.emel.anton.facade.patient.PatientFacade;
import by.emel.anton.facade.patient.RequestPatientDTO;
import by.emel.anton.facade.patient.ResponsePatientDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/therapy")
public class PatientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    PatientFacade patientFacade;

    @GetMapping(value = "/patient/{id}")
    public ResponsePatientDTO getPatientById(@PathVariable String id) {
        return patientFacade.getPatientById(id);
    }

    @PostMapping(value = "/patient/login" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponsePatientDTO getPatientByLoginPass(@RequestBody RequestPatientDTO requestPatientDTO) {

        String login = requestPatientDTO.getLogin();
        String password = requestPatientDTO.getPassword();

//        LOGGER.info(String.join(" ","Try to get by login ",login, "and password ",password));
        LOGGER.info("try to get by login {}",login);

        return patientFacade.getPatientByLogPass(login,password);
    }

  /*  @GetMapping("/getlist")
    public Optional<Therapy> therapyList() {
        return springDataTherapyDAO.getTherapy(1);
    }*/
   /* @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public void exceptionHanlder(Exception exception) {
        LOGGER.error("some error",exception);
    }*/
}
