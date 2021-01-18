package by.emel.anton.model.dao.implementation.hibernatedao;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

@Repository("PatientHibernate")
@Transactional
public class HibernatePatientDAO implements PatientDAO {

    EntityManager entityManager;

    @Autowired
    public HibernatePatientDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Patient> getPatient(String login, String password) throws UserDAOException {
        String queryGetUserId = "select id from User where login = ?0 and password = ?1";

        try{
            int patientId = (Integer) entityManager
                    .createQuery(queryGetUserId)
                    .setParameter(0,login)
                    .setParameter(1,password)
                    .getSingleResult();

            return Optional.ofNullable(entityManager.find(Patient.class,patientId));
        }
        catch (NoResultException e) {
            throw new UserDAOException(Constants.EXCEPTION_MESSAGE_LP_INCORRECT);
        }
    }

    @Override
    public Optional<Patient> getPatientById(int id) throws UserDAOException {
        try{
            return Optional.ofNullable(entityManager.find(Patient.class,id));
        }
        catch (NoResultException e) {
            throw new UserDAOException(Constants.EXCEPTION_NO_ID);
        }
    }
}
