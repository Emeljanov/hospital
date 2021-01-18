package by.emel.anton.model.dao.implementation.hibernatedao;

import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.interfaces.UserDAO;
import org.hibernate.query.internal.NativeQueryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository("UserHibernate")
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
        int count = (Integer) entityManager.createQuery(query).setParameter(0,login).getSingleResult();
        return count >= 1;

    }

    @Override
    public void updateUser(User user) throws UserDAOException {
        entityManager.merge(user);
        entityManager.flush();
    }

    @Override
    public void saveUser(User user) throws UserDAOException {
        entityManager.persist(user);
        entityManager.flush();
    }
}
