package by.emel.anton.model.dao.implementation.springdatadao.intefaces;

import by.emel.anton.model.beans.users.patients.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientJpaRepository extends JpaRepository<Patient,Integer> {
}
