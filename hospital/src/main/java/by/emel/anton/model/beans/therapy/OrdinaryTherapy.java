package by.emel.anton.model.beans.therapy;

import java.time.LocalDate;

public class OrdinaryTherapy extends Therapy {
    public OrdinaryTherapy(String name, LocalDate startDate, LocalDate endDate) {
        super(name, startDate, endDate);
    }
}
