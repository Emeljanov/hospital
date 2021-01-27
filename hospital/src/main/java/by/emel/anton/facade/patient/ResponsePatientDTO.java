package by.emel.anton.facade.patient;

import java.util.List;

public class ResponsePatientDTO {

    private int id;
    private String login;
    private String name;
    private List<Integer> therapнIds;

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

    public List<Integer> getTherapнIds() {
        return therapнIds;
    }

    public void setTherapнIds(List<Integer> therapнIds) {
        this.therapнIds = therapнIds;
    }
}
