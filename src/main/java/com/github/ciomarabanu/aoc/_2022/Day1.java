package com.github.ciomarabanu.aoc._2022;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;

import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class Day1 {
    public static void main(String[] args) {
        var reader = FileReaderFactory.build();
        var parseInput = reader.readFileAsStrings("2022/day1");
        sortByCalories(parseInput);
    }

    public static void sortByCalories(List<String> input) {
        var elves = new HashMap<Integer, Integer>();
        var elfCount = 0;
        var calSum = 0;

        for (var num: input) {
            if (!num.equals("")) {
                calSum += Integer.parseInt(num);
            }
            else {
                elves.put(elfCount, calSum);
                elfCount++;
                calSum = 0;
            }
        }

        var a = elves.values().stream()
            .sorted(Comparator.reverseOrder())
            .limit(3)
            .reduce(0, Integer::sum);


        System.out.println(a);

    }



}
