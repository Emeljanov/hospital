package by.emel.anton.model.dao.implementation.springdatadao;

import by.emel.anton.model.entity.users.doctors.Doctor;
import by.emel.anton.model.dao.implementation.springdatadao.intefaces.DoctorJpaRepository;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("SpringDataDoctorDAO")
public class SpringDataDoctorDAO implements DoctorDAO {

    private DoctorJpaRepository doctorJpaRepository;

    @Autowired
    public SpringDataDoctorDAO(DoctorJpaRepository doctorJpaRepository) {
        this.doctorJpaRepository = doctorJpaRepository;
    }

    @Override
    public Optional<Doctor> getDoctor(String login, String password) {

        return doctorJpaRepository.getDoctorIdByLoginAndPassword(login, password).flatMap(doctorJpaRepository::findById);
    }

    @Override
    public Optional<Doctor> getDoctorById(int id) {
        return doctorJpaRepository.findById(id);
    }

    @Override
    public Optional<Doctor> getDoctorByLogin(String login) {
        return doctorJpaRepository.findByLogin(login);
    }


}
