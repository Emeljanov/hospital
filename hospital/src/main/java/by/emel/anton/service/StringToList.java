package by.emel.anton.service;
import by.emel.anton.constants.Constans;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class StringToList {
    public static List<Integer> toIntegerList(String line) {

        line = line.replaceAll("[\\]\\[\\ ]", Constans.EMPTY);

        if(!line.equals(Constans.EMPTY)) {

            return Arrays
                    .stream(line.split(Constans.COMMA))
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
            }

        return Collections.emptyList();
    }
}
