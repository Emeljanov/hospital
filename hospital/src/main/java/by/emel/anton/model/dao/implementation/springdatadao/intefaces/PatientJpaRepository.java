package by.emel.anton.model.dao.implementation.springdatadao.intefaces;

import by.emel.anton.model.beans.users.patients.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PatientJpaRepository extends JpaRepository<Patient,Integer> {

    @Query("select id from User where login = :login and password = :password")
    Optional<Integer> getPatientIdByLoginAndPassword(@Param("login") String login, @Param("password") String password);



}
