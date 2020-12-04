package by.emel.anton.service;
import by.emel.anton.constants.Constans;
import java.util.ArrayList;


public class StringToList {
    public static ArrayList<Integer> toIntegerList(String line) {
        line = line.replaceAll("[\\]\\[\\ ]", Constans.EMPTY);
        String[] arrayFromLine = line.split(Constans.COMMA);
        ArrayList<Integer> result = new ArrayList<>();
        for(String x : arrayFromLine) {
            result.add(Integer.valueOf(x));
        }
        return result;
    }
}
