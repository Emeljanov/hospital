package by.emel.anton.model.dao.interfaces;

import by.emel.anton.model.beans.therapy.Therapy;

import java.io.IOException;
import java.util.Optional;

public interface TherapyDAO {

    void saveTherapy(Therapy therapy) throws IOException;

    int getNextID() throws IOException;

    Optional<Therapy> getTherapy(int id) throws IOException;
}
