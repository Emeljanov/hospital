package by.emel.anton.model.beans.therapy;

import java.time.LocalDate;

public class OrdinaryTherapy extends Therapy {

    public OrdinaryTherapy() {
    }

    public OrdinaryTherapy(int id , String description, LocalDate startDate, LocalDate endDate) {
        super(id,description, startDate, endDate);
    }
}
