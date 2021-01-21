package by.emel.anton.model.dao.implementation.hibernatedao;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.interfaces.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Repository("HibernateUserDAO")
@Transactional
public class HibernateUserDAO implements UserDAO {


    EntityManager entityManager;

    @Autowired
    public HibernateUserDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean isLoginExist(String login) throws UserDAOException {
        String query = "select count(*) from User where login = ?0";
        int count = (Integer) entityManager.createQuery(query).setParameter(0, login).getSingleResult();
        return count >= 1;

    }

    @Override
    public void updateUser(User user) throws UserDAOException {
        entityManager.merge(user);
    }

    @Override
    public void saveUser(User user) throws UserDAOException {
        entityManager.persist(user);
    }

    protected static int getUserId(String login, String password, EntityManager entityManager) throws UserDAOException {
        String queryGetDoctorId = "select id from User where login = ?0 and password = ?1";
        try {
            return (int) entityManager
                    .createQuery(queryGetDoctorId)
                    .setParameter(0, login)
                    .setParameter(1, password)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new UserDAOException(Constants.EXCEPTION_MESSAGE_LP_INCORRECT);
        }
    }
}
