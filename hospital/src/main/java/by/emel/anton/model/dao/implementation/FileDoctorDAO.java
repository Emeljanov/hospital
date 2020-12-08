package by.emel.anton.model.dao.implementation;

import by.emel.anton.constants.Constans;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.doctors.GeneralDoctor;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import by.emel.anton.service.StringToList;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class FileDoctorDAO implements DoctorDAO {

    @Override
    public Doctor getDoctor(String login, String password) throws UserDAOException {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(Constans.FILE_PATH_DOCTORS))) {
            String line = bufferedReader.readLine();

            while (line != null) {
                String[] userData = line.split(Constans.SEPARATOR);
                if(userData[1].equals(login) && userData[2].equals(password)) {
                    int id = Integer.parseInt(userData[0]);
                    LocalDate birthday = LocalDate.parse(userData[5]);
                    String name = userData[4];
                    ArrayList<Integer> patients = StringToList.toIntegerList(userData[6]);

                    GeneralDoctor generalDoctor = new GeneralDoctor(id,login,password,name,birthday);
                    generalDoctor.setPatientsId(patients);
                    return generalDoctor;
                }

                line = bufferedReader.readLine();
            }
            throw new UserDAOException(Constans.EXCEPTION_MESSAGE_LP_INCORRECT);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void setDoctorIdToPatient(Doctor doctor, Patient patient) {
        patient.setDoctorId(doctor.getId());
    }

}
