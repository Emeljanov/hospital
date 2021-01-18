package by.emel.anton.model.dao.implementation.hibernatedao;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

@Repository("DoctorHibernate")
@Transactional
public class HibernateDoctorDAO implements DoctorDAO {

    EntityManager entityManager;

    @Autowired
    public HibernateDoctorDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Doctor> getDoctor(String login, String password) throws UserDAOException {
        String queryGetDoctorId = "select id from User where login = ?0 and password = ?1";
        try {
            int doctorId = (Integer) entityManager
                    .createQuery(queryGetDoctorId)
                    .setParameter(0,login)
                    .setParameter(1,password)
                    .getSingleResult();

            return Optional.ofNullable(entityManager.find(Doctor.class,doctorId));
        }
        catch (NoResultException e) {
            throw new UserDAOException(Constants.EXCEPTION_MESSAGE_LP_INCORRECT);
        }
    }
}