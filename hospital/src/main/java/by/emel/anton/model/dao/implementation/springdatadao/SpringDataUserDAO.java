package by.emel.anton.model.dao.implementation.springdatadao;

import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.implementation.springdatadao.intefaces.UserJpaRepository;
import by.emel.anton.model.dao.interfaces.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SpringDataUserDAO implements UserDAO {

    @Autowired
    UserJpaRepository userJpaRepository;
    @Override

    public boolean isLoginExist(String login) {
        int count = userJpaRepository.loginCount(login);
        return count>=1;
    }

    @Override
    public void updateUser(User user)  {
        userJpaRepository.save(user);
    }

    @Override
    public void saveUser(User user)  {
        userJpaRepository.save(user);
    }


}
