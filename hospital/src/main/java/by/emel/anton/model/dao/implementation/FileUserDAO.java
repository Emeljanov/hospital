package by.emel.anton.model.dao.implementation;

import by.emel.anton.constants.Constans;
import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.UserType;
import by.emel.anton.model.dao.interfaces.UserDAO;

import java.io.*;

public class FileUserDAO implements UserDAO {

    public boolean isLoginExist(String login, String filePath) {
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while (line != null) {
                String[] userData = line.split(Constans.SEPARATOR);
                if(userData[1].equals(login)) {
                    return true;
                }
                line = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public void saveUser(User user) {

        String login = user.getLogin();

        if(user.getUserType().equals(UserType.DOCTOR)) {
            if(!isLoginExist(login,Constans.FILE_PATH_DOCTORS)) {
                try(FileWriter fw = new FileWriter(Constans.FILE_PATH_DOCTORS,true)) {
                    fw.write(user.toString());
                    fw.write(Constans.DESCENT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        else if (user.getUserType().equals(UserType.PATIENT)) {
            if(!isLoginExist(login,Constans.FILE_PATH_PATIENTS)) {
                try(FileWriter fw = new FileWriter(Constans.FILE_PATH_PATIENTS,true)) {
                    fw.write(user.toString());
                    fw.write(Constans.DESCENT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    @Override
    public int getNextId(UserType userType) {
        int nextId = 1;
        String filePath = "";
        if(userType == UserType.DOCTOR) {
            filePath = Constans.FILE_PATH_DOCTORS;
        }
        else if(userType == UserType.PATIENT) {
            filePath = Constans.FILE_PATH_PATIENTS;
        }

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] userData = line.split(Constans.SEPARATOR);
                int id = Integer.valueOf(userData[0]);
                if(id > nextId) {
                    nextId = id + 1;
                }
                line = bufferedReader.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nextId;
    }


    @Override
    public void updateUser(User user) {
        int userId = user.getId();
        File tempFile = new File(Constans.FILE_PATH_TEMP);

        File userFile;

        if(user.getUserType().equals(UserType.DOCTOR)) {
           userFile = new File(Constans.FILE_PATH_DOCTORS);
        }
        else {
            userFile =  new File(Constans.FILE_PATH_PATIENTS);
        }
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(userFile))) {

            String line = bufferedReader.readLine();
            while (line != null) {
                String[] userData = line.split(Constans.SEPARATOR);
                if(userId != Integer.valueOf(userData[0])) {
                    try(FileWriter fw = new FileWriter(tempFile,true)) {
                        fw.write(line);
                        fw.write(Constans.DESCENT);
                    }

                }
                line = bufferedReader.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filePath = userFile.getPath();
        userFile.delete();
        tempFile.renameTo(new File(filePath));

        saveUser(user);


    }


}