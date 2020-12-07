package by.emel.anton.model.dao.interfaces;

import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.patients.Patient;

public interface TherapyDAO {
    void saveTherapy(Therapy therapy);

    int getNextID();

    Therapy getTherapy(int id);
}
