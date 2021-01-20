package by.emel.anton.model.dao.interfaces;

import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.dao.exceptions.TherapyDAOException;
import by.emel.anton.model.dao.exceptions.UserDAOException;

import java.util.Optional;

public interface TherapyDAO {

    void saveTherapy(Therapy therapy) throws TherapyDAOException;

    Optional<Therapy> getTherapy(int id) throws TherapyDAOException, UserDAOException;
}
