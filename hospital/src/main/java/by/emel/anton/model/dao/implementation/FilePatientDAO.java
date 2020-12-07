package by.emel.anton.model.dao.implementation;

import by.emel.anton.constants.Constans;
import by.emel.anton.model.beans.users.doctors.GeneralDoctor;
import by.emel.anton.model.beans.users.patients.OrdinaryPatient;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import by.emel.anton.service.StringToList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class FilePatientDAO implements PatientDAO {
    @Override
    public Patient getPatient(String login, String password) throws UserDAOException {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(Constans.FILE_PATH_PATIENTS))) {
            String line = bufferedReader.readLine();

            while (line != null) {
                String[] userData = line.split(Constans.SEPARATOR);
                if(userData[1].equals(login) && userData[2].equals(password)) {
                    int id = Integer.parseInt(userData[0]);
                    String name = userData[4];
                    LocalDate birthday = LocalDate.parse(userData[5]);
                    int doctorId = Integer.parseInt(userData[6]);
                    ArrayList<Integer> therapiesId = StringToList.toIntegerList(userData[7]);
                    OrdinaryPatient ordinaryPatient = new OrdinaryPatient(id,login,password,name,birthday,doctorId);
                    ordinaryPatient.setTherapies(therapiesId);
                    return ordinaryPatient;
                }

                line = bufferedReader.readLine();
            }
            throw new UserDAOException("Login or password are incorrect");




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Patient getPatientById(int id) throws UserDAOException {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(Constans.FILE_PATH_PATIENTS))) {
            String line = bufferedReader.readLine();

            while (line != null) {
                String[] userData = line.split(Constans.SEPARATOR);
                if(Integer.valueOf(userData[0]) == id) {
                    String login = userData[1];
                    String password = userData[2];
                    String name = userData[4];
                    LocalDate birthday = LocalDate.parse(userData[5]);
                    int doctorId = Integer.parseInt(userData[6]);
                    ArrayList<Integer> therapiesId = StringToList.toIntegerList(userData[7]);
                    OrdinaryPatient ordinaryPatient = new OrdinaryPatient(id,login,password,name,birthday,doctorId);
                    ordinaryPatient.setTherapies(therapiesId);
                    return ordinaryPatient;
                }

                line = bufferedReader.readLine();
            }
            throw new UserDAOException("Login or password are incorrect");




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
