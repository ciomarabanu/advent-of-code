package com.github.ciomarabanu.aoc._2022;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day2 {
    public static void main(String[] args) {
        var reader = FileReaderFactory.build();
        var parseInput = reader.readFileAsStrings("2022/day2");

        part1(parseInput);
        part2(parseInput);
    }

    public static int part1(List<String> input) {
        var rounds = input.stream()
            .map(l -> { var norm = l.split(" ");
                if (l.charAt(2) == 'X')
                norm[1] = "A";
                else if (l.charAt(2) == 'Y')
                    norm[1] = "B";
                else if (l.charAt(2) == 'Z')
                    norm[1] = "C";

                return norm;
            })
            .collect(Collectors.toList());

        var score = 0;

        for (var round : rounds) {
            score += calculateScore(round[0].charAt(0), round[1].charAt(0));
        }
        System.out.println(score);
        return score;
    }

    public static int part2(List<String> input) {
        var rounds = input.stream().map(l -> Arrays.stream(l.split(" ")).mapToInt(str -> {
            return switch (str) {
               case "A" -> 1;
               case "B" -> 2;
               case "C" -> 3;
               case "X" -> 0;
               case "Y" -> 3;
               case "Z" -> 6;
               default -> -1;
            };
        }).boxed().collect(Collectors.toList()))
            .collect(Collectors.toList());

        int score = 0;
        int myMove = 0;

        for (var round : rounds) {
            if (round.get(1) == 3) {
                score = score + round.get(0) + round.get(1);
            }
            else if (round.get(1) == 0) {
                myMove = round.get(0) - 1;
                if (myMove == 0)
                    myMove = 3;
                score += myMove;
            } else {
                myMove = round.get(0) + 1;
                if (myMove == 4)
                    myMove = 1;
                score += myMove + 6;
            }
        }
        System.out.println(score);
        return score;

    }

    private static int calculateScore (char a, char b) {
        int result = switch (b) {
            case 'A' -> 1;
            case 'B' -> 2;
            case 'C' -> 3;
            default -> 0;
        };
        if (a == b)
            result += 3;

        else if ((a - b == - 1) || (a - b) == 2)
            result += 6;

        return result;

    }
}
