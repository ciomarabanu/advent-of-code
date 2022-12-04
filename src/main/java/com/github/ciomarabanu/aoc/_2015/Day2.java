package com.github.ciomarabanu.aoc._2015;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day2 {
    public static void main(String[] args) {
        var reader = FileReaderFactory.build();
        var input = reader.readFileAsStrings("2015/day2");
        System.out.println(partII(input));
    }

    private static int partI(List<String> input) {
        return input.stream()
            .map(box -> box.split("x"))
            .map(box -> Arrays.stream(box).map(Integer::parseInt).collect(Collectors.toList()))
            .map(box -> {
                int a = 2 * box.get(0) * box.get(1);
                int b = 2 * box.get(1) * box.get(2);
                int c = 2 * box.get(0) * box.get(2);
                return a + b + c + Math.min(Math.min(a, b), c) / 2;
            })
            .flatMapToInt(IntStream::of)
            .sum();
    }

    private static int partII(List<String> input) {
        return input.stream()
            .map(box -> box.split("x"))
            .map(box -> Arrays.stream(box).map(Integer::parseInt).collect(Collectors.toList()))
            .map(box -> {
                int small = Math.min(box.get(0), box.get(1));
                int medium = Math.min(Math.max(box.get(0), box.get(1)), box.get(2));
                return 2 * small + 2 * medium + box.get(0) * box.get(1) * box.get(2);
            })
            .flatMapToInt(IntStream::of)
            .sum();
    }
}
