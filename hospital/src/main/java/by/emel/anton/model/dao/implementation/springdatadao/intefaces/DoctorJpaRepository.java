package by.emel.anton.model.dao.implementation.springdatadao.intefaces;

import by.emel.anton.model.entity.users.doctors.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DoctorJpaRepository extends JpaRepository<Doctor, Integer> {

    @Query("select id from User where login = :login and password = :password")
    Optional<Integer> getDoctorIdByLoginAndPassword(@Param("login") String login, @Param("password") String password);

    Optional<Doctor> findByLogin(String login);
}
