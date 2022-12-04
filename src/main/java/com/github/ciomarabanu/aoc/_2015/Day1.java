package com.github.ciomarabanu.aoc._2015;
import com.github.ciomarabanu.aoc.utils.FileReaderFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day1 {
    public static void main(String[] args) {
        var reader = FileReaderFactory.build();
        var input = reader.readFileAsStrings("2015/day1");
        System.out.println(input);
        System.out.println(findFloor(input));
        System.out.println(partII(input));
    }

    private static int findFloor(List<String> input) {
        return input.stream()
            .map(s -> s.split(""))
            .flatMap(Stream::of)
            .map(paren -> paren.equals("(")? 1 : -1)
            .flatMapToInt(IntStream::of).sum();
    }

    private static int partII(List<String> input){
        var parens = input.stream()
            .map(s -> s.split(""))
            .flatMap(Stream::of)
            .map(paren -> paren.equals("(")? 1 : -1)
            .collect(Collectors.toList());

        var sum = 0;
        var idx = 0;

        while (sum != -1) {
            sum += parens.get(idx);
            idx++;
        }
        return idx;
    }
}
