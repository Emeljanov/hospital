package by.emel.anton.model.dao.implementation.springdatadao;

import by.emel.anton.model.entity.users.User;
import by.emel.anton.model.dao.implementation.springdatadao.intefaces.UserJpaRepository;
import by.emel.anton.model.dao.interfaces.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("SpringDataUserDAO")
public class SpringDataUserDAO implements UserDAO {

    private UserJpaRepository userJpaRepository;

    @Autowired
    public SpringDataUserDAO(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public boolean isLoginExist(String login) {
        int count = userJpaRepository.loginCount(login);
        return count >= 1;
    }

    @Override
    public void updateUser(User user) {
        userJpaRepository.save(user);
    }

    @Override
    public void saveUser(User user) {
        userJpaRepository.save(user);
    }


}
