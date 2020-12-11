package by.emel.anton.model.dao.implementation;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.therapy.OrdinaryTherapy;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.dao.interfaces.TherapyDAO;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class FileTherapyDAO implements TherapyDAO {
    @Override
    public void saveTherapy(Therapy therapy) {

        try(FileWriter fileWriter = new FileWriter(Constants.FILE_PATH_THERAPIES,true)) {

            fileWriter.write(therapy.toString());
            fileWriter.write(Constants.DESCENT);

        } catch (IOException e) {
            e.printStackTrace();
        }
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
