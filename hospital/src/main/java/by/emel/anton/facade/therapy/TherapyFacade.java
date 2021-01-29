package by.emel.anton.facade.therapy;

import by.emel.anton.model.dao.exceptions.TherapyDAOException;
import by.emel.anton.model.dao.exceptions.UserDAOException;

public interface TherapyFacade {
    ResponseTherapyDTO getTherapy(int id) throws TherapyDAOException, UserDAOException;
}
