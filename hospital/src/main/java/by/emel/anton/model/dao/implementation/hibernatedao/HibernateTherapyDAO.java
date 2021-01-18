package by.emel.anton.model.dao.implementation.hibernatedao;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.dao.exceptions.TherapyDAOException;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

@Repository("TherapyHibernate")
@Transactional
public class HibernateTherapyDAO implements TherapyDAO {

    EntityManager entityManager;

    @Autowired
    public HibernateTherapyDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void saveTherapy(Therapy therapy) throws TherapyDAOException {
        entityManager.persist(therapy);
    }

    @Override
    public Optional<Therapy> getTherapy(int id) throws TherapyDAOException {
        try {
            return Optional.ofNullable(entityManager.find(Therapy.class,id));
        }
        catch (NoResultException e) {
            throw new TherapyDAOException(Constants.EXCEPTION_NO_ID);
        }
    }

}
