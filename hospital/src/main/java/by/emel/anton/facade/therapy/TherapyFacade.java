package by.emel.anton.facade.therapy;

import by.emel.anton.model.dao.exceptions.TherapyDaoUncheckedException;
import by.emel.anton.model.dao.exceptions.UserDaoUncheckedException;

public interface TherapyFacade {
    ResponseTherapyDTO getTherapy(int id) throws TherapyDaoUncheckedException, UserDaoUncheckedException;
}
