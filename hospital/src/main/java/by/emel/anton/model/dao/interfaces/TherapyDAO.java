package by.emel.anton.model.dao.interfaces;

import by.emel.anton.model.beans.therapy.Therapy;

import java.util.Optional;

public interface TherapyDAO {

    void saveTherapy(Therapy therapy);

    Optional<Therapy> getTherapy(int id);
}
