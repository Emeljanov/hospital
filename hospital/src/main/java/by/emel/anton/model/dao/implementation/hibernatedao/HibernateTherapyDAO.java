package by.emel.anton.model.dao.implementation.hibernatedao;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.model.dao.exceptions.TherapyDaoException;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

@Repository("HibernateTherapyDAO")
@Transactional
public class HibernateTherapyDAO implements TherapyDAO {

    private EntityManager entityManager;

    @Autowired
    public HibernateTherapyDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void saveTherapy(Therapy therapy) {
        entityManager.persist(therapy);
    }

    @Override
    public Optional<Therapy> getTherapy(int id) {
        try {
            return Optional.ofNullable(entityManager.find(Therapy.class, id));
        } catch (NoResultException e) {
            throw new TherapyDaoException(Constants.EXCEPTION_NO_ID);
        }
    }

}
