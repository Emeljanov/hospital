package by.emel.anton.model.dao.interfaces;

import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.dao.exceptions.TherapyDaoUncheckedException;
import by.emel.anton.model.dao.exceptions.UserDaoUncheckedException;

import java.util.Optional;

public interface TherapyDAO {

    void saveTherapy(Therapy therapy) throws TherapyDaoUncheckedException;

    Optional<Therapy> getTherapy(int id) throws TherapyDaoUncheckedException, UserDaoUncheckedException;
}
