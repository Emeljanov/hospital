package by.emel.anton.model.beans.users.doctors;




import java.time.LocalDate;

public class GeneralDoctor extends Doctor {


    public GeneralDoctor(int id, String login, String password, String name, LocalDate birthday) {
        super(id, login, password, name, birthday);
    }
}
