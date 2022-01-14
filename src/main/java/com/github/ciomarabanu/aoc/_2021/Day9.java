package com.github.ciomarabanu.aoc._2021;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;
import com.github.ciomarabanu.aoc.utils.GridUtils.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.github.ciomarabanu.aoc.utils.GridUtils.neighbourCoords;

public class Day9 {
    public static void main(String[] args) {
        var reader = FileReaderFactory.build();
        var caveMap = reader.readFileAsStrings("2021/day9")
                .stream()
                .map(line -> Arrays.stream(line.split(""))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        System.out.println(caveMap);
        System.out.println(getRisk(caveMap));
        System.out.println(getRiskII(caveMap));
        System.out.println(biggest3Basins(caveMap));
    }

    public static int getRisk(List<List<Integer>> caveMap) {
        var result = 0;
        var enhancedCaveMap = generatePerimeter(caveMap, 9);

        for (int i = 1; i < enhancedCaveMap.size() - 1; i++) {
            for (int j = 1; j < enhancedCaveMap.get(i).size() - 1; j++) {

                int num = enhancedCaveMap.get(i).get(j);

                int left = enhancedCaveMap.get(i).get(j - 1);
                int right = enhancedCaveMap.get(i).get(j + 1);
                int up = enhancedCaveMap.get(i - 1).get(j);
                int down = enhancedCaveMap.get(i + 1).get(j);

                if (num < left && num < right && num < up && num < down) {
                    result += num + 1;
                }
            }
        }
        return result;
    }

    private static List<List<Integer>> generatePerimeter(List<List<Integer>> caveMap, Integer p) {
        var suroundedCaveMap = new ArrayList<List<Integer>>();
        var border = new ArrayList<Integer>();

        for (int i = 0; i < caveMap.get(0).size() + 2; i++) {
            border.add(p);
        }

        suroundedCaveMap.add(border);

        for (var row : caveMap) {
            var suroundedRow = new ArrayList<Integer>();
            for (int j = 0; j < 3; j++) {

                if (j == 0) {
                    suroundedRow.add(p);
                } else if (j == 1) {
                    suroundedRow.addAll(row);
                } else {
                    suroundedRow.add(p);
                }
            }
            suroundedCaveMap.add(suroundedRow);
        }
        suroundedCaveMap.add(border);

        return suroundedCaveMap;
    }

    public static int getRiskII(List<List<Integer>> caveMap) {
        return IntStream.range(0, caveMap.size()).boxed()
                .flatMap(row -> IntStream.range(0, caveMap.get(row).size())
                        .mapToObj(col -> new Point2D(row, col)))
                .filter(point -> isLowPoint(point, caveMap))
                .map(point -> caveMap.get(point.x()).get(point.y()))
                .reduce(0, (acc, num) -> acc + num + 1);
    }

    private static boolean isLowPoint(Point2D point, List<List<Integer>> caveMap) {
        var num = caveMap.get(point.x()).get(point.y());
        return  neighbourCoords(point.x(), point.y(), caveMap.size(), caveMap.get(0).size())
                .stream().map(p -> caveMap.get(p.x()).get(p.y()))
                .allMatch(nei -> nei > num);
    }

    public static int biggest3Basins(List<List<Integer>> caveMap) {
        var visited = new HashSet<Point2D>();
        return IntStream.range(0, caveMap.size()).boxed()
                .flatMap(row -> IntStream.range(0, caveMap.get(row).size())
                        .mapToObj(col -> new Point2D(row, col)))
                .filter(point -> !visited.contains(point) && caveMap.get(point.x()).get(point.y()) != 9)
                .map(point -> basinSizeDFS(point, visited, caveMap))
                .sorted(Comparator.reverseOrder()).limit(3)
                .reduce(1, (a, b) -> a * b);
    }


    private static int basinSizeDFS(Point2D point, Set<Point2D> visited, List<List<Integer>> caveMap) {
        visited.add(point);
        return 1 + neighbourCoords(point.x(), point.y(), caveMap.size(), caveMap.get(0).size())
                .stream().filter(nei -> !visited.contains(nei) && caveMap.get(nei.x()).get(nei.y()) != 9)
                .map(nei -> basinSizeDFS(nei, visited, caveMap))
                .mapToInt(Integer::intValue)
                .sum();
    }


}
