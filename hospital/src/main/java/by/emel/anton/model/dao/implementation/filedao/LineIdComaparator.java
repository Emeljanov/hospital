package by.emel.anton.model.dao.implementation.filedao;

import by.emel.anton.constants.Constants;

import java.util.Comparator;

public class LineIdComaparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        int o1Id = Integer.parseInt(o1.split(Constants.SEPARATOR)[0]);
        int o2Id = Integer.parseInt(o2.split(Constants.SEPARATOR)[0]);

        return o1Id - o2Id;
    }
}
