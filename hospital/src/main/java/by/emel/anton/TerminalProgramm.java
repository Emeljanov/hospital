package by.emel.anton;

import by.emel.anton.model.beans.therapy.OrdinaryTherapy;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import by.emel.anton.model.dao.interfaces.UserDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.time.LocalDate;

public class TerminalProgramm {
    UserDAO userDAO;
    PatientDAO patientDAO;
    DoctorDAO doctorDAO;
    TherapyDAO therapyDAO;

    public TerminalProgramm(UserDAO userDAO, PatientDAO patientDAO, DoctorDAO doctorDAO, TherapyDAO therapyDAO) {
        this.userDAO = userDAO;
        this.patientDAO = patientDAO;
        this.doctorDAO = doctorDAO;
        this.therapyDAO = therapyDAO;
    }

    public void startProgramm() {
        System.out.println("Hi, you are a doctor or patient? (d/p)");
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            String answer = bufferedReader.readLine();
            if(answer.equals("d")) {
                System.out.println("You are the doctor! ");
                enterDoctor(bufferedReader);
            }
            else if (answer.equals("p")) {
                System.out.println("You are the patient!");
                enterPatient(bufferedReader);
            }
        } catch (IOException | UserDAOException e) {
            e.printStackTrace();
        }
    }

    private void enterDoctor(BufferedReader bufferedReader) throws IOException, UserDAOException {
        System.out.println("Enter login and password");
        String answer = bufferedReader.readLine();
        String[] userData = answer.split(" ");
        String login = userData[0];
        String password = userData[1];
        Doctor doctor = doctorDAO.getDoctor(login,password);
        System.out.println("Hi! " + doctor.getName());
        forDoctor(bufferedReader, doctor);

    }

    private void forDoctor(BufferedReader bufferedReader, Doctor doctor) throws IOException, UserDAOException {

        System.out.println("What do you want? See your patients (see)/Add new patient(add)/set therapy(set)?/exit(exit)");

        String answer = bufferedReader.readLine();

            if(answer.equals("add")) {
                System.out.println("add");
            }
            else if(answer.equals("see")) {
                System.out.println("see");
                System.out.println(doctor.getPatientsId());
                forDoctor(bufferedReader,doctor);
            }
            else if(answer.equals("set")) {
                System.out.println("set");
                setTherapyToPatinet(bufferedReader,doctor);
            }
            else if(answer.equals("exit")) {
                startProgramm();
            }

    }

    private void setTherapyToPatinet(BufferedReader bufferedReader,Doctor doctor) throws IOException, UserDAOException {
        System.out.println("Enter Patient ID");
        int patientId = Integer.valueOf(bufferedReader.readLine());
        Patient patient = patientDAO.getPatientById(patientId);
        System.out.println("Enter therapy description");
        String description = bufferedReader.readLine();
        System.out.println("Enter end date (yyyy-mm-dd");
        String endDate = bufferedReader.readLine();

        int id = therapyDAO.getLastID();

        Therapy therapy = new OrdinaryTherapy(id,description, LocalDate.now(),LocalDate.parse(endDate));
        doctor.setTherapy(patient,therapy);
        userDAO.updateUser(patient);
        therapyDAO.saveTherapy(therapy);
        forDoctor(bufferedReader,doctor);

    }

    private void enterPatient(BufferedReader bufferedReader) throws IOException, UserDAOException {
        System.out.println("Enter login and password");
        String answer = bufferedReader.readLine();
        String[] userData = answer.split(" ");
        String login = userData[0];
        String password = userData[1];
        Patient patient = patientDAO.getPatient(login,password);
        System.out.println("Hi! " + patient.getName());
        forPatient(bufferedReader, patient);
    }

    private void forPatient(BufferedReader bufferedReader, Patient patient) throws IOException {
        System.out.println("What do you want? See your therapies (therapies)/See you therapy(therapy)?/exit(exit)");

        String answer = bufferedReader.readLine();

        if(answer.equals("therapies")) {
            System.out.println(patient.getTherapies().toString());
        }
        else if(answer.equals("therapy")) {
            System.out.println("Please, enter id therapy");
            int id = Integer.valueOf(bufferedReader.readLine());
            Therapy therapy = therapyDAO.getTherapy(id);
            System.out.println(therapy);
            forPatient(bufferedReader,patient);
        }

        else if(answer.equals("exit")) {
            startProgramm();
        }
        forPatient(bufferedReader,patient);

    }
}
