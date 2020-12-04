package by.emel.anton.model.dao.implementation;

import by.emel.anton.constants.Constans;
import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.UserType;
import by.emel.anton.model.beans.users.doctors.GeneralDoctor;
import by.emel.anton.model.beans.users.patients.OrdinaryPatient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.interfaces.UserDAO;

import java.io.*;
import java.time.LocalDate;

public class FileUserDAO implements UserDAO {
  /*  @Override
    public User getUser(String login, String password) {

        try(FileReader fileReader = new FileReader(") {

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            while(line != null) {

                String[] userData = line.split(Constans.SEPARATOR);
                if(userData[0].equals(login) && userData[1].equals(password)) {
                    if(userData[2].equals(UserType.DOCTOR.toString())) {
                        return new GeneralDoctor(login,password);
                    }
                    else {
                        return new OrdinaryPatient(login,password);
                    }
                }

                line = bufferedReader.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveUser(User user) {
        String login = user.getLogin();
        String password = user.getPassword();
        String userTypeString = user.getUserType().toString();
        LocalDate userBirthday = LocalDate.of(2020,11,01);

        if(!isLoginExist(login)) {
            try(FileWriter fileWriter = new FileWriter()) {
                fileWriter.write(login);
                fileWriter.write(Constans.SEPARATOR);
                fileWriter.write(password);
                fileWriter.write(Constans.SEPARATOR);
                fileWriter.write(userTypeString);
                fileWriter.write(Constans.SEPARATOR);
                fileWriter.write(String.valueOf(userBirthday));
                fileWriter.write(Constans.DESCENT);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        else {
            return false;
        }


    }
*/
    public boolean isLoginExist(String login, String filePath) {
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while (line != null) {
                String[] userData = line.split(Constans.SEPARATOR);
                if(userData[1].equals(login)) {
                    return true;
                }
                line = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
