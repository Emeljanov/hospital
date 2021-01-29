package by.emel.anton.model.dao.implementation.springdatadao;

import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.dao.implementation.springdatadao.intefaces.DoctorJpaRepository;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("SpringDataDoctorDAO")
public class SpringDataDoctorDAO implements DoctorDAO {

    DoctorJpaRepository doctorJpaRepository;

    @Autowired
    public SpringDataDoctorDAO(DoctorJpaRepository doctorJpaRepository) {
        this.doctorJpaRepository = doctorJpaRepository;
    }

    @Override
    public Optional<Doctor> getDoctor(String login, String password) {
        Optional<Integer> optionalDoctorId = doctorJpaRepository.getDoctorIdByLoginAndPassword(login, password);
        return optionalDoctorId.flatMap(doctorId -> doctorJpaRepository.findById(doctorId));
    }

    @Override
    public Optional<Doctor> getDoctorById(int id) {
        return doctorJpaRepository.findById(id);
    }


}
