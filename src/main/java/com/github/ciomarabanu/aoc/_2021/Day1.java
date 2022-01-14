package com.github.ciomarabanu.aoc._2021;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;

import java.util.List;
import java.util.stream.IntStream;

public class Day1 {

    public static void main(String[] args) {
        var reader = FileReaderFactory.build();
        var measurements = reader.readFileAsIntegers("2021/day1");

        System.out.println(countLargerThanPrevious(measurements));
        System.out.println(countWindowLargerThanPrev(measurements));
    }


    public static int countLargerThanPrevious(List<Integer> measurements) {
        return (int) IntStream.range(1, measurements.size())
                .filter(i -> measurements.get(i) > measurements.get(i - 1)).count();
    }

    public static int countWindowLargerThanPrev(List<Integer> measurements) {
        return (int) IntStream.range(3, measurements.size())
                .filter(i -> measurements.get(i) > measurements.get(i - 3)).count();
    }

}
