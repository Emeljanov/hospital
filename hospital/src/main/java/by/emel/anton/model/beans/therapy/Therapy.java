package by.emel.anton.model.beans.therapy;

import java.time.LocalDate;

public abstract class Therapy {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    public Therapy(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
