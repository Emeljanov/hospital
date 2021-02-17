package by.emel.anton.model.dao.implementation.filedao;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.model.entity.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.TherapyDaoException;
import by.emel.anton.model.dao.exceptions.UserDaoException;
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
    public void saveTherapy(Therapy therapy) {

        try {
            therapy.setId(getNextID());
            List<String> lines = Collections.singletonList(therapy.toString());
            Files.write(Paths.get(Constants.FILE_PATH_THERAPIES), lines, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new TherapyDaoException("ERROR save Therapy in file");
        }
    }

    public int getNextID() {

        try {
            return fileServiceDAO.getNextLineId(Paths.get(Constants.FILE_PATH_THERAPIES));
        } catch (UserDaoException e) {
            throw new TherapyDaoException("ERROR get Next therapy file");
        }
    }

    @Override
    public Optional<Therapy> getTherapy(int id) {

        try {
            List<String> fileData = Files.readAllLines(Paths.get(Constants.FILE_PATH_THERAPIES));

            Optional<String> therapyString = fileData
                    .stream()
                    .filter(s -> fileServiceDAO.isIdCorrect(s, id))
                    .findFirst();

            Therapy therapy = therapyString
                    .map(fileServiceDAO::createTherapyFromLine)
                    .orElseThrow(TherapyDaoException::new);

            Patient patient = filePatientDAO
                    .getPatientById(therapy.getPatient().getId())
                    .orElseThrow(UserDaoException::new);

            therapy.setPatient(patient);

            return Optional.ofNullable(therapy);

        } catch (IOException e) {
            throw new TherapyDaoException("Error get therapy from file");
        }
    }

}
