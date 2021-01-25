package by.emel.anton.service;

import by.emel.anton.terminalprog.AnswerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserServiceResolverImp implements UserServiceResolver {

    private final Map<AnswerType, UserService> map;

    @Autowired
    public UserServiceResolverImp(
            @Qualifier("HibernateService") UserService hibernateUserService,
            @Qualifier("JdbcTemplateService") UserService jdbcTemplateUserService,
            @Qualifier("FileService") UserService fileUserService) {
        map = new HashMap<>();
        map.put(AnswerType.HIBERNATE, hibernateUserService);
        map.put(AnswerType.TEMPLATE, jdbcTemplateUserService);
        map.put(AnswerType.FILE, fileUserService);
    }

    @Override
    public UserService resolveUserService(AnswerType answerType) {
        return map.get(answerType);
    }

}
