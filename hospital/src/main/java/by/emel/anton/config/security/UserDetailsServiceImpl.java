package by.emel.anton.config.security;

import by.emel.anton.model.dao.exceptions.UserDaoException;
import by.emel.anton.model.entity.users.doctors.Doctor;
import by.emel.anton.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetServImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(@Qualifier("SpringDataService") UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Doctor doctor = userService.getDoctorByLogin(login).orElseThrow(() -> new UserDaoException("blahhhhhhhh"));
        return SecurityUser.fromUser(doctor);
    }
}