package com.github.ciomarabanu.aoc._2021;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day5 {
    public static void main(String[] args) {
        var reader = FileReaderFactory.build();
        var lines = reader
                .readFileAsStrings("2021/day5")
                .stream()
                .map(Day5::parseLine)
                .collect(Collectors.toList());

        var straightLines = lines.stream()
                .filter(l -> l.get(0).get(0).equals(l.get(1).get(0)) ||
                        l.get(0).get(1).equals(l.get(1).get(1)))
                .collect(Collectors.toList());
        System.out.println(lines);
        System.out.println(overlappingStraightLines(straightLines));
        System.out.println(overlappingStraightLines(lines));
    }

    private static List<List<Integer>> parseLine(String s) {
        return Arrays.stream(s.split(" -> "))
                .map(num -> Arrays
                        .stream(num.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList())
                ).collect(Collectors.toList());

    }

    private static int overlappingStraightLines(List<List<List<Integer>>> straightLines) {
        var result = 0;
        var canvas = new int[1000][1000];

        for (var line: straightLines) {
            drawLine(line, canvas);
        }

        for (var row : canvas) {
            for (var col: row) {
                if (col >= 2)
                    result += 1;
            }
        }
        return result;
    }

    private static boolean isHorizontal(List<List<Integer>> line) {
        return line.get(0).get(1).equals(line.get(1).get(1));
    }

    private static boolean isVertical(List<List<Integer>> line) {
        return line.get(0).get(0).equals(line.get(1).get(0));
    }

//    private static boolean isDiagonal(List<List<Integer>> line) {
//        return !line.get(0).get(1).equals(line.get(1).get(1))
//                && !line.get(0).get(0).equals(line.get(1).get(0));
//    }

    private static void drawLine(List<List<Integer>> line, int[][] canvas) {
        if (isHorizontal(line)) {
            int y = line.get(0).get(1);
            for (int x = Math.min(line.get(0).get(0), line.get(1).get(0)); x <= Math.max(line.get(0).get(0), line.get(1).get(0)); x++){
                int prevSum = canvas[y][x] + 1;
                canvas[y][x] = prevSum;
            }
        } else if (isVertical(line)){
            int x = line.get(0).get(0);
            for (int y = Math.min(line.get(0).get(1), line.get(1).get(1)); y <= Math.max(line.get(0).get(1), line.get(1).get(1)); y++) {
                int prevSum = canvas[y][x] + 1;
                canvas[y][x] = prevSum;
            }
        }else  {
            int x1 = line.get(0).get(0);
            int y1 = line.get(0).get(1);
            int x2 = line.get(1).get(0);
            int y2 = line.get(1).get(1);

            var xCoords = (x1 < x2) ?
                    IntStream.range(x1, x2+1).boxed()
                            .collect(Collectors.toList()) :
                    IntStream.range(x2, x1+1).boxed()
                            .sorted(Collections.reverseOrder())
                            .collect(Collectors.toList());
            var yCoords = (y1 < y2) ?
                    IntStream.range(y1, y2+1).boxed()
                            .collect(Collectors.toList()) :
                    IntStream.range(y2, y1+1).boxed()
                            .sorted(Collections.reverseOrder())
                            .collect(Collectors.toList());

            for (int i = 0; i < xCoords.size(); i++) {
                int x = xCoords.get(i);
                int y = yCoords.get(i);
                int prevSum = canvas[y][x] + 1;
                canvas[y][x] = prevSum;
            }

        }
    }
}
