package by.emel.anton.facade;

import by.emel.anton.model.beans.therapy.Therapy;

import java.time.LocalDate;

public class TherapyFacade {

    private int id;
    private String discr;
    private LocalDate endDate;
    private LocalDate startDate;
    private int patientId;

    public TherapyFacade() {
    }

    public void setFacade(Therapy therapy) {
        id = therapy.getId();
        discr = therapy.getDescription();
        endDate = therapy.getEndDate();
        startDate = therapy.getStartDate();
        patientId = therapy.getPatient().getId();
    }

}
