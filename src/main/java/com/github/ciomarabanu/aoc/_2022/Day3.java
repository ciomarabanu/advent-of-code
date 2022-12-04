package com.github.ciomarabanu.aoc._2022;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;
import com.google.common.collect.Lists;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Day3 {
    public static void main(String[] args) {
        var reader = FileReaderFactory.build();
        var parseInput = reader.readFileAsStrings("2022/day3");

        System.out.println(part1(parseInput));
        System.out.println(part2(parseInput));
    }

    public static int part1(List<String> input) {
        return input.stream().map(l -> {
            var firstHalf = l.substring(0, l.length() / 2);
            var secHalf = l.substring(l.length() / 2);
            var intersection = strIntersect(firstHalf, secHalf);
            int priority = getPriority(intersection);
            return priority;
        }).reduce(0, Integer::sum);

    }

    public static int part2(List<String> input) {
        return Lists.partition(input, 3).stream()
            .map(group -> getPriority(strIntersectPart2(group.get(0), group.get(1), group.get(2))))
            .reduce(0, Integer::sum);

        }

    private static Character strIntersect(String s1, String s2) {
        var a = s1.chars().boxed().collect(Collectors.toSet());
        var b = s2.chars().boxed().collect(Collectors.toSet());
        var c = new HashSet<>(a);

        c.retainAll(b);
        return Character.toChars(c.iterator().next())[0];
    }

    private static Character strIntersectPart2(String s1, String s2, String s3) {
        var a = s1.chars().boxed().collect(Collectors.toSet());
        var b = s2.chars().boxed().collect(Collectors.toSet());
        var c = s3.chars().boxed().collect(Collectors.toSet());
        var d = new HashSet<>(a);

        d.retainAll(b);
        d.retainAll(c);
        return Character.toChars(d.iterator().next())[0];
    }

    private static int getPriority(Character ch) {
        if (Character.isLowerCase(ch)) {
            return ch - 'a' + 1;
        } else {
            return ch - 'A' + 27;
        }
    }
}
