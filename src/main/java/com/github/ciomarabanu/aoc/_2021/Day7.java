package com.github.ciomarabanu.aoc._2021;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day7 {
    public static void main(String[] args) {
        var reader = FileReaderFactory.build();
        var crabPositions = Arrays
                .stream(reader.readFileAsStrings("2021/day7").get(0).split(","))
                .map(Integer::parseInt).sorted().collect(Collectors.toList());

        System.out.println(alignToMedian(crabPositions));
        System.out.println(alignToMean(crabPositions));
    }

    public static int alignToMedian(List<Integer> crabPositions) {
        var n = crabPositions.size();
        var median = (n % 2 == 1)
                ? crabPositions.get((n + 1) / 2)
                : (crabPositions.get(n / 2) + crabPositions.get(n / 2 + 1)) / 2;

        return crabPositions.stream().reduce(0, (cost, elem) -> cost + Math.abs(elem - median));
    }

    public static long alignToMean(List<Integer> crabPositions) {
        var mean = (int) crabPositions.stream().mapToInt(Integer::intValue).average().getAsDouble();
        return IntStream
                .range(mean, mean + 2)
                .map(candidate ->
                    crabPositions
                        .stream()
                        .reduce(0, (cost, position) -> cost + gauss(Math.abs(position - candidate)))
                ).min().getAsInt();
    }

    public static int gauss (int n) {
        return (n * (n + 1)) / 2;
    }
}

