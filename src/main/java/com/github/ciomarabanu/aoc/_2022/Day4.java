package com.github.ciomarabanu.aoc._2022;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day4 {
    public static void main(String[] args) {
        var reader = FileReaderFactory.build();
        var parseInput = reader.readFileAsStrings("2022/day4");

        System.out.println(part1(parseInput));
       // System.out.println(part2(parseInput));
    }

    public static long part1(List<String> input) {
        return input.stream()
            .map(pair -> pair.split(","))
            .filter(p -> pairContainsTheOther(p[0], p[1]))
            .count();

    }

    private static boolean pairContainsTheOther (String range1, String range2) {
        var r1 = Arrays.stream(range1.split("-"))
            .map(Integer::parseInt)
            .collect(Collectors.toList());
        var r2 = Arrays.stream(range2.split("-"))
            .map(Integer::parseInt)
            .collect(Collectors.toList());

        //RETURN FOR PART1 :
        // return ((r1.get(0) <= r2.get(0)) && (r1.get(1) >= r2.get(1))) ||
        //     ((r2.get(0) <= r1.get(0)) && (r2.get(1) >= r1.get(1)));


        //RETURN FOR PART2 :
        return !((Math.max(r1.get(0), r1.get(1)) < Math.min(r2.get(0), r2.get(1))) ||
            (Math.max(r2.get(0), r2.get(1)) < Math.min(r1.get(0), r1.get(1))));
    }
}
