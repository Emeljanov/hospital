package by.emel.anton.model.dao.implementation;

import by.emel.anton.constants.Constans;
import by.emel.anton.model.beans.therapy.OrdinaryTherapy;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.interfaces.TherapyDAO;

import java.io.*;
import java.time.LocalDate;

public class FileTherapyDAO implements TherapyDAO {
    @Override
    public void saveTherapy(Therapy therapy) {
        try(FileWriter fileWriter = new FileWriter(Constans.FILE_PATH_THERAPIES,true)) {
            fileWriter.write(therapy.toString());
            fileWriter.write(Constans.DESCENT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLastID() {
        int idMax = 0;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(Constans.FILE_PATH_THERAPIES))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] therapyDate = line.split(Constans.SEPARATOR);
                int id = Integer.valueOf(therapyDate[0]);
                if(id > idMax) {
                    idMax = id + 1;
                }
                line = bufferedReader.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return idMax;
    }

    @Override
    public Therapy getTherapy(int id) {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(Constans.FILE_PATH_THERAPIES))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] therapyData = line.split(Constans.SEPARATOR);
                if(Integer.valueOf(therapyData[0]) == id) {
                    String description = therapyData[1];
                    LocalDate startDate = LocalDate.parse(therapyData[2]);
                    LocalDate endDate = LocalDate.parse(therapyData[3]);
                    Therapy therapy = new OrdinaryTherapy(id,description,startDate,endDate);
                    return therapy;
                }
                line = bufferedReader.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;

    }
}
