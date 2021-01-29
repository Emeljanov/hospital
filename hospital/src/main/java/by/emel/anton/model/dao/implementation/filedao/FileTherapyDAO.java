package by.emel.anton.model.dao.implementation.filedao;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.TherapyDAOException;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository("FileTherapyDAO")
public class FileTherapyDAO implements TherapyDAO {

    private FilePatientDAO filePatientDAO;
    private FileServiceDAO fileServiceDAO;

    @Autowired
    public FileTherapyDAO(FilePatientDAO filePatientDAO, FileServiceDAO fileServiceDAO) {
        this.filePatientDAO = filePatientDAO;
        this.fileServiceDAO = fileServiceDAO;

    }

    @Override
    public void saveTherapy(Therapy therapy) throws TherapyDAOException {

        try {
            therapy.setId(getNextID());
            List<String> lines = Collections.singletonList(therapy.toString());
            Files.write(Paths.get(Constants.FILE_PATH_THERAPIES), lines, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new TherapyDAOException("ERROR save Therapy in file");
        }
    }

    public int getNextID() throws TherapyDAOException {

        try {
            return fileServiceDAO.getNextLineId(Paths.get(Constants.FILE_PATH_THERAPIES));
        } catch (UserDAOException e) {
            throw new TherapyDAOException("ERROR get Next therapy file");
        }
    }

    @Override
    public Optional<Therapy> getTherapy(int id) throws TherapyDAOException, UserDAOException {

        try {
            List<String> fileData = Files.readAllLines(Paths.get(Constants.FILE_PATH_THERAPIES));
            Optional<String> therapyString = fileData.stream().filter(s -> fileServiceDAO.isIdCorrect(s, id))
                    .findFirst();

            if (!therapyString.isPresent()) throw new TherapyDAOException("ERROR getTherapy");

            Therapy therapy = fileServiceDAO.createTherapyFromLine(therapyString.get());
            Optional<Patient> patient = filePatientDAO.getPatientById(therapy.getPatient().getId());
            patient.ifPresent(therapy::setPatient);

            return Optional.ofNullable(therapy);

        } catch (IOException e) {
            throw new TherapyDAOException("Error get therapy from file");
        }
    }

}
