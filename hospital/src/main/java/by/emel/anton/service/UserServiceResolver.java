package by.emel.anton.service;

import by.emel.anton.terminalprog.AnswerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserServiceResolver {

    private final Map<AnswerType, UserService> map;

    @Autowired
    public UserServiceResolver(
            @Qualifier("FromFile")UserService userServiceFile,
            @Qualifier("FromJDBCTemplate")UserService userServiceJDBCTemplate) {
        map = new HashMap<>();
        map.put(AnswerType.F,userServiceFile);
        map.put(AnswerType.T,userServiceJDBCTemplate);
    }

    public UserService resolveUserService(AnswerType answerType) {
        return map.get(answerType);
    }
}
