package by.emel.anton.model.dao.implementation.hibernatedao;

import by.emel.anton.model.beans.therapy.OrdinaryTherapy;
import by.emel.anton.model.beans.therapy.Therapy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface HibbRepo extends JpaRepository<OrdinaryTherapy, Integer> {
}
