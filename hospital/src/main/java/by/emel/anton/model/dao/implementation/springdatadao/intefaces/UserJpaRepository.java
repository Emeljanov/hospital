package by.emel.anton.model.dao.implementation.springdatadao.intefaces;

import by.emel.anton.model.beans.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserJpaRepository extends JpaRepository<User, Integer> {

    @Query("select count(*) from User where login = :login")
    int loginCount(@Param("login") String login);

}
