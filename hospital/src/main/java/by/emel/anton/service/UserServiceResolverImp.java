package by.emel.anton.service;

import by.emel.anton.terminalprog.AnswerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserServiceResolverImp implements UserServiceResolver{

    private final Map<AnswerType, UserService> map;

    @Autowired
    public UserServiceResolverImp(
            @Qualifier("FromFile")UserService userServiceFile,
            @Qualifier("FromJDBCTemplate")UserService userServiceJDBCTemplate) {
        map = new HashMap<>();
        map.put(AnswerType.FILE,userServiceFile);
        map.put(AnswerType.THERAPY,userServiceJDBCTemplate);
    }

    @Override
    public UserService resolveUserService(AnswerType answerType) {
        return map.get(answerType);
    }

}
