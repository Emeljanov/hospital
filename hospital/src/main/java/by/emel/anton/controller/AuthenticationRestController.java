package by.emel.anton.controller;

import by.emel.anton.config.Role;
import by.emel.anton.config.security.JwtTokenProvider;
import by.emel.anton.facade.doctor.DoctorFacade;
import by.emel.anton.facade.doctor.RequestDoctorDTO;
import by.emel.anton.facade.doctor.ResponseDoctorDTO;
import by.emel.anton.facade.patient.PatientFacade;
import by.emel.anton.facade.patient.ResponsePatientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private final DoctorFacade doctorFacade;
    private final PatientFacade patientFacade;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager,
                                        DoctorFacade doctorFacade,
                                        PatientFacade patientFacade,
                                        JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.doctorFacade = doctorFacade;
        this.patientFacade = patientFacade;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login/{usertype}")
    public ResponseEntity<?> authenticate(@RequestBody @Valid RequestDoctorDTO requestDoctorDTO,@PathVariable(name = "usertype") String usertype) {
        String s = "k";


        try {
            String login = requestDoctorDTO.getLogin();
            String password = requestDoctorDTO.getPassword();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
//            Doctor doctor = userService.getDoctorByLogin(login).orElseThrow(()-> new UsernameNotFoundException("Doctor doesnt exist"))
            Map<Object, Object> response = new HashMap<>();
            if(usertype.equals("doctor")) {
                ResponseDoctorDTO responseDoctorDTO = doctorFacade.getDoctorByLogin(login);
                String token = jwtTokenProvider.createToken(login, Role.ADMIN.name());
                response.put("doc", responseDoctorDTO);
                response.put("token", token);
            }
            else if(usertype.equals("patient")) {
               ResponsePatientDTO responsePatientDTO = patientFacade.getPatientByLogin(login);
               String token = jwtTokenProvider.createToken(login,Role.USER.name());
               response.put("patient",responsePatientDTO);
               response.put("token",token);
            }

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid login or password", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
