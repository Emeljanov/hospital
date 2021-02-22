package by.emel.anton.model.dao.implementation.springdatadao;

import by.emel.anton.model.entity.users.patients.Patient;
import by.emel.anton.model.dao.implementation.springdatadao.intefaces.PatientJpaRepository;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("SpringDataPatientDAO")
public class SpringDataPatientDAO implements PatientDAO {

    private PatientJpaRepository patientJpaRepository;

    @Autowired
    public SpringDataPatientDAO(PatientJpaRepository patientJpaRepository) {
        this.patientJpaRepository = patientJpaRepository;
    }

    @Override
    public Optional<Patient> getPatient(String login, String password) {
        Optional<Integer> optionalId = patientJpaRepository.getPatientIdByLoginAndPassword(login, password);
        return optionalId.flatMap(patientId -> patientJpaRepository.findById(patientId));
    }

    @Override
    public Optional<Patient> getPatientById(int id) {
        return patientJpaRepository.findById(id);
    }

    @Override
    public Optional<Patient> getPatientByLogin(String login) {
        return patientJpaRepository.findByLogin(login);
    }
}
