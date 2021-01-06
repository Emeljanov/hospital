package by.emel.anton.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DataStorageService {

    private UserService userServiceFile;
    private UserService userServiceJDBCTemplate;

    @Autowired
    public DataStorageService(
            @Qualifier("FromFile")UserService userServiceFile,
            @Qualifier("FromJDBCTemplate")UserService userServiceJDBCTemplate) {
        this.userServiceFile = userServiceFile;
        this.userServiceJDBCTemplate = userServiceJDBCTemplate;
    }

    public UserService getUserServiceFile() {
        return userServiceFile;
    }

    public UserService getUserServiceJDBCTemplate() {
        return userServiceJDBCTemplate;
    }
}
