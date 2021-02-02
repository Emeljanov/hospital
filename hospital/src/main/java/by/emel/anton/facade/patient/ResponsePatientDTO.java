package by.emel.anton.facade.patient;

import java.util.List;

public class ResponsePatientDTO {

    private int id;
    private String login;
    private String name;
    private List<Integer> therapyIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Integer> getTherapyIds() {
        return therapyIds;
    }

    public void setTherapyIds(List<Integer> therapyIds) {
        this.therapyIds = therapyIds;
    }
}
