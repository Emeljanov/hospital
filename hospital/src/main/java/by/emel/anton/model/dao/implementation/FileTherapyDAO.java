package by.emel.anton.model.dao.implementation;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.therapy.OrdinaryTherapy;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.dao.interfaces.TherapyDAO;

import java.io.*;
import java.time.LocalDate;
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
    public int getNextID() {

        int nextId = 1;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(Constants.FILE_PATH_THERAPIES))) {

            String line = bufferedReader.readLine();

            while (line != null) {

                String[] therapyDate = line.split(Constants.SEPARATOR);
                int id = Integer.parseInt(therapyDate[0]);

                if(id >= nextId) {
                    nextId = id + 1;

                }
                line = bufferedReader.readLine();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return nextId;
    }

    @Override
    public Optional<Therapy> getTherapy(int id) {

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(Constants.FILE_PATH_THERAPIES))) {

            String line = bufferedReader.readLine();
            while (line != null) {

                String[] therapyData = line.split(Constants.SEPARATOR);

                if(Integer.parseInt(therapyData[0]) == id) {

                    String description = therapyData[1];
                    LocalDate startDate = LocalDate.parse(therapyData[2]);
                    LocalDate endDate = LocalDate.parse(therapyData[3]);

                    return Optional.of(new OrdinaryTherapy(id,description,startDate,endDate));

                }

                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();

    }
}
