package by.emel.anton.model.dao.implementation.springdatadao;

import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.model.dao.implementation.springdatadao.intefaces.TherapyJpaRepository;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository("SpringDataTherapyDAO")
public class SpringDataTherapyDAO implements TherapyDAO {

    private TherapyJpaRepository therapyJpaRepository;

    @Autowired
    public SpringDataTherapyDAO(TherapyJpaRepository therapyJpaRepository) {
        this.therapyJpaRepository = therapyJpaRepository;
    }

    @Override
    public void saveTherapy(Therapy therapy) {
        therapyJpaRepository.save(therapy);
    }

    @Override
    public Optional<Therapy> getTherapy(int id) {
        return therapyJpaRepository.findById(id);
    }
}
