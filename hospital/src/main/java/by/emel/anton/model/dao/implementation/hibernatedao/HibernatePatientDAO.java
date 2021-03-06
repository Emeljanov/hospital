package by.emel.anton.model.dao.implementation.hibernatedao;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.entity.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDaoException;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

@Repository("HibernatePatientDAO")
@Transactional
public class HibernatePatientDAO implements PatientDAO {

    private EntityManager entityManager;

    @Autowired
    public HibernatePatientDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Patient> getPatient(String login, String password) {

        return Optional.ofNullable(entityManager.find(Patient.class, HibernateUserDAO.getUserId(login, password, entityManager)));

    }

    @Override
    public Optional<Patient> getPatientById(int id) {

        try {
            return Optional.ofNullable(entityManager.find(Patient.class, id));
        } catch (NoResultException e) {
            throw new UserDaoException(Constants.EXCEPTION_NO_ID);
        }
    }
}
