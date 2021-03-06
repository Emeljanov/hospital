package by.emel.anton.model.dao.implementation.hibernatedao;

import by.emel.anton.model.entity.users.doctors.Doctor;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository("HibernateDoctorDAO")
@Transactional
public class HibernateDoctorDAO implements DoctorDAO {

    private EntityManager entityManager;

    @Autowired
    public HibernateDoctorDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Doctor> getDoctor(String login, String password) {

        return Optional.ofNullable(entityManager.find(Doctor.class, HibernateUserDAO.getUserId(login, password, entityManager)));

    }

    @Override
    public Optional<Doctor> getDoctorById(int id) {
        return Optional.ofNullable(entityManager.find(Doctor.class, id));
    }
}
