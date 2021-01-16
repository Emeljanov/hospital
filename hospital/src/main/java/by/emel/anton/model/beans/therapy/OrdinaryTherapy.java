package by.emel.anton.model.beans.therapy;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
//@Table(name = "therapies")
public class OrdinaryTherapy extends Therapy {

    public OrdinaryTherapy() {
    }

    public OrdinaryTherapy(int id , String description, LocalDate startDate, LocalDate endDate) {
        super(id,description, startDate, endDate);
    }
}
