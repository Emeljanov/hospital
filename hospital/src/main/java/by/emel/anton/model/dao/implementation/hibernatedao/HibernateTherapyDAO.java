package by.emel.anton.model.dao.implementation.hibernatedao;

import by.emel.anton.model.beans.therapy.OrdinaryTherapy;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.dao.exceptions.TherapyDAOException;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
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
    public int getNextID() throws TherapyDAOException {
        return 0;
    }

    @Override
    public Optional<Therapy> getTherapy(int id) throws TherapyDAOException {

       /* return Optional.ofNullable(entityManager.find(OrdinaryTherapy.class,id));*/
        return Optional.empty();
    }

//    public Optional<OrdinaryTherapy> getOrdTh(int id) {
//        return  hibbRepo.findById(id);
//    }

}
