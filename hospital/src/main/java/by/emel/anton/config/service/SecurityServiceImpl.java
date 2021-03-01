package by.emel.anton.config.service;

import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Data
@Service
public class SecurityServiceImpl implements SecurityService {

    @Override
    public String getLoginFromUserDetails() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }
}
