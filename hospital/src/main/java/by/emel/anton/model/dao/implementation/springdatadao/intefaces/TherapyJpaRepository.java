package by.emel.anton.model.dao.implementation.springdatadao.intefaces;

import by.emel.anton.model.entity.therapy.Therapy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface TherapyJpaRepository extends JpaRepository<Therapy,Integer> {
}
