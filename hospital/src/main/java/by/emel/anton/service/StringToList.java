package by.emel.anton.service;

import by.emel.anton.constants.Constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StringToList {

    public static List<Integer> toIntegerList(String line) {

        line = line.replaceAll("[\\]\\[\\ ]", Constants.EMPTY);

        if(!line.isEmpty()) {

            return Arrays
                    .stream(line.split(Constants.COMMA))
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
            }

        return Collections.emptyList();
    }
}
