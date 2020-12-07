package by.emel.anton.model.beans.therapy;

import by.emel.anton.constants.Constans;

import java.time.LocalDate;

public abstract class Therapy {
    private int id;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    public Therapy(int id ,String description, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return id + Constans.SEPARATOR + description + Constans.SEPARATOR + startDate + Constans.SEPARATOR + endDate;
    }
}
