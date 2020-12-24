package by.emel.anton.model.dao.implementation.filedoa;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.therapy.OrdinaryTherapy;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository("TherapyFromFile")
public class FileTherapyDAO implements TherapyDAO {

    @Override
    public void saveTherapy(Therapy therapy) throws IOException {

        List<String> lines = Collections.singletonList(therapy.toString());
        Files.write(Paths.get(Constants.FILE_PATH_THERAPIES), lines, StandardOpenOption.APPEND);

    }

    @Override
    public int getNextID() throws IOException {

        return FileService.getNextLineId(Constants.FILE_PATH_THERAPIES);

    }

    @Override
    public Optional<Therapy> getTherapy(int id) throws IOException {

        List<String> fileData = Files.readAllLines(Paths.get(Constants.FILE_PATH_THERAPIES));

        return fileData
                .stream()
                .filter(s -> FileService.findLineById(s,id))
                .findFirst()
                .map(this::createTherapyFromLine);

    }
    private Therapy createTherapyFromLine(String lineData) {

        String[] therapyData = lineData.split(Constants.SEPARATOR);
        int id = Integer.parseInt(therapyData[0]);
        String description = therapyData[1];
        LocalDate startDate = LocalDate.parse(therapyData[2]);
        LocalDate endDate = LocalDate.parse(therapyData[3]);

        return new OrdinaryTherapy(id,description,startDate,endDate);
    }
}
