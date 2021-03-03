package by.emel.anton.controller;

import by.emel.anton.config.Role;
import by.emel.anton.config.security.JwtTokenProvider;
import by.emel.anton.facade.doctor.DoctorFacade;
import by.emel.anton.facade.doctor.ResponseDoctorDTO;
import by.emel.anton.facade.patient.PatientFacade;
import by.emel.anton.facade.patient.ResponsePatientDTO;
import by.emel.anton.facade.user.RequestUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private final DoctorFacade doctorFacade;
    private final PatientFacade patientFacade;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login/{usertype}")
    public ResponseEntity<?> authenticate(@RequestBody @Valid RequestUserDTO requestUserDTO, @PathVariable(name = "usertype") String usertype) {


        try {
            String login = requestUserDTO.getLogin();
            String password = requestUserDTO.getPassword();

            log.info("Login in with login : {} , password: {}", login, password);

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));

            Map<Object, Object> response = new HashMap<>();
            if (usertype.equals("doctor")) {
                ResponseDoctorDTO responseDoctorDTO = doctorFacade.getDoctorByLogin(login);
                String token = jwtTokenProvider.createToken(login, Role.ADMIN.name());
                response.put("doctor", responseDoctorDTO);
                response.put("token", token);
            } else if (usertype.equals("patient")) {
                ResponsePatientDTO responsePatientDTO = patientFacade.getPatientByLogin(login);
                String token = jwtTokenProvider.createToken(login, Role.USER.name());
                response.put("patient", responsePatientDTO);
                response.put("token", token);
            }

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid login or password");
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        log.info("Logout from token {}", request.getHeader("Authorization"));
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
