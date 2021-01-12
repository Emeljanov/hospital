package by.emel.anton.model.beans.therapy;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class OrdinaryTherapy extends Therapy {

    public OrdinaryTherapy() {
    }

    public OrdinaryTherapy(int id , String description, LocalDate startDate, LocalDate endDate) {
        super(id,description, startDate, endDate);
    }
}
