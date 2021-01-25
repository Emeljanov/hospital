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
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository("FileTherapyDAO")
public class FileTherapyDAO implements TherapyDAO {


    private FilePatientDAO filePatientDAO;

    @Autowired
    public FileTherapyDAO( FilePatientDAO filePatientDAO) {
        this.filePatientDAO = filePatientDAO;

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
            return FileService.getNextLineId(Paths.get(Constants.FILE_PATH_THERAPIES));
        } catch (IOException e) {
            throw new TherapyDAOException("ERROR get Next therapy file");
        }
    }

    @Override
    public Optional<Therapy> getTherapy(int id) throws TherapyDAOException, UserDAOException {

        try {
            List<String> fileData = Files.readAllLines(Paths.get(Constants.FILE_PATH_THERAPIES));
            Optional<String> therapyString = fileData.stream().filter(s -> FileService.findLineById(s, id))
                    .findFirst();

            if (!therapyString.isPresent()) throw new TherapyDAOException("ERROR getTherapy");

//            Therapy therapy = createTherapyFromLine(therapyString.get());
            Therapy therapy = FileService.createTherapyFromLine(therapyString.get());
            Optional<Patient> patient = filePatientDAO.getPatientById(therapy.getPatient().getId());
            patient.ifPresent(therapy::setPatient);

            return Optional.ofNullable(therapy);

        } catch (IOException e) {
            throw new TherapyDAOException("Error get therapy from file");
        }
    }

  /*  private Therapy createTherapyFromLine(String lineData) throws UserDAOException {

        String[] therapyData = lineData.split(Constants.SEPARATOR);
        int id = Integer.parseInt(therapyData[0]);
        String description = therapyData[1];
        LocalDate startDate = LocalDate.parse(therapyData[2]);
        LocalDate endDate = LocalDate.parse(therapyData[3]);
        int patientId = Integer.parseInt(therapyData[4]);

        Therapy therapy = new Therapy();
        therapy.setId(id);
        therapy.setDescription(description);
        therapy.setStartDate(startDate);
        therapy.setEndDate(endDate);

        Optional<Patient> patient = filePatientDAO.getPatientById(patientId);
        patient.ifPresent(therapy::setPatient);

        return therapy;
    }*/
}
