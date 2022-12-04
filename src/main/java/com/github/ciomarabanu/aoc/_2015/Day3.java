package com.github.ciomarabanu.aoc._2015;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;
import com.github.ciomarabanu.aoc.utils.GridUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day3 {
    public static void main(String[] args) {
        var reader = FileReaderFactory.build();
        var input = reader.readFileAsStrings("2015/day3").get(0);
        System.out.println(partI(input));
        System.out.println(partII(input));

    }

    private static int partI(String input) {
        HashSet<Object> visited = getVisitedHouses(input);
        return visited.size();
    }

    private static int partII(String input) {

        var realSanta = divideSantasPaths(input).get(0);
        var roboSanta = divideSantasPaths(input).get(1);

        var realSHouses = getVisitedHouses(realSanta);
        var roboSHouses = getVisitedHouses(roboSanta);

        realSHouses.addAll(roboSHouses);

        return realSHouses.size();
    }

    private static HashSet<Object> getVisitedHouses(String input) {
        var visited = new HashSet<>();
        GridUtils.Point2D prevHouse = new GridUtils.Point2D(0, 0);
        visited.add(prevHouse);
        for (var arrow : input.toCharArray()) {
            switch (arrow) {
                case '<' -> prevHouse = new GridUtils.Point2D(prevHouse.x() - 1, prevHouse.y());
                case '>' -> prevHouse = new GridUtils.Point2D(prevHouse.x() + 1, prevHouse.y());
                case '^' -> prevHouse = new GridUtils.Point2D(prevHouse.x(), prevHouse.y() + 1);
                case 'v' -> prevHouse = new GridUtils.Point2D(prevHouse.x(), prevHouse.y() - 1);
            }
            visited.add(prevHouse);
        }
        return visited;
    }

    private static List<String> divideSantasPaths(String input) {
        var workingInput = input.toCharArray();
        var result = new ArrayList<String>();

//        StringBuilder realSanta = new StringBuilder();
//        StringBuilder roboSanta = new StringBuilder();
//
//        for (int i = 0; i < input.length(); i++) {
//            if (i % 2 == 0)
//                realSanta.append(workingInput[i]);
//            else roboSanta.append(workingInput[i]);
//        }
//
//        realSanta.toString()
//        roboSanta.toString()

        var realSanta = IntStream.range(0, input.length())
            .filter(i -> i % 2 == 0)
            .mapToObj(i -> Character.toString(workingInput[i]))
            .collect(Collectors.joining());

        var roboSanta = IntStream.range(0, input.length())
            .filter(i -> i % 2 != 0)
            .mapToObj(i -> Character.toString(workingInput[i]))
            .collect(Collectors.joining());

        result.add(realSanta);
        result.add(roboSanta);

        return result;
    }


}
