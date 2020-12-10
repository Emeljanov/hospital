package by.emel.anton.model.dao.implementation;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.users.patients.OrdinaryPatient;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import by.emel.anton.service.StringToList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class FilePatientDAO implements PatientDAO {
    @Override
    public Optional<Patient> getPatient(String login, String password) throws UserDAOException {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(Constants.FILE_PATH_PATIENTS))) {
            String line = bufferedReader.readLine();

            while (line != null) {
                String[] userData = line.split(Constants.SEPARATOR);
                if(userData[1].equals(login) && userData[2].equals(password)) {
                    int id = Integer.parseInt(userData[0]);
                    String name = userData[4];
                    LocalDate birthday = LocalDate.parse(userData[5]);
                    int doctorId = Integer.parseInt(userData[6]);
                    List<Integer> therapiesId = StringToList.toIntegerList(userData[7]);
                    OrdinaryPatient ordinaryPatient = new OrdinaryPatient(id,login,password,name,birthday,doctorId);
                    ordinaryPatient.setTherapies(therapiesId);
                    return Optional.of(ordinaryPatient);
                }

                line = bufferedReader.readLine();
            }
            throw new UserDAOException(Constants.EXCEPTION_MESSAGE_LP_INCORRECT);




        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Patient> getPatientById(int id) throws UserDAOException {

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(Constants.FILE_PATH_PATIENTS))) {
            String line = bufferedReader.readLine();

            while (line != null) {
                String[] userData = line.split(Constants.SEPARATOR);
                if(Integer.parseInt(userData[0]) == id) {
                    String login = userData[1];
                    String password = userData[2];
                    String name = userData[4];
                    LocalDate birthday = LocalDate.parse(userData[5]);
                    int doctorId = Integer.parseInt(userData[6]);
                    List<Integer> therapiesId = StringToList.toIntegerList(userData[7]);
                    OrdinaryPatient ordinaryPatient = new OrdinaryPatient(id,login,password,name,birthday,doctorId);
                    ordinaryPatient.setTherapies(therapiesId);
                    return Optional.of(ordinaryPatient);
                }

                line = bufferedReader.readLine();
            }
            throw new UserDAOException(Constants.EXCEPTION_MESSAGE_LP_INCORRECT);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
