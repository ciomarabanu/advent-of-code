package com.github.ciomarabanu.aoc._2021;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;
import com.github.ciomarabanu.aoc.utils.GridUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day11 {
    public static void main(String[] args) {
        var reader = FileReaderFactory.build();
//        var input = reader.readFileAsStrings("2021/day11")
//                .stream().map(line -> Arrays.stream(line.split(""))
//                        .map(Integer::parseInt)
//                        .collect(Collectors.toList()))
//                .collect(Collectors.toList());

        var input = reader.readFileAsStrings("2021/day11");
        var numRows = input.size();
        var numCols = input.get(0).length();
        var octopusis = parseInput(input, numRows, numCols);
        System.out.println(hundredDays(octopusis, 10, 10));
        System.out.println(allFlash(octopusis, 10, 10));
    }

    private static GridAndFlashCount takeStepDFSorBFSiDontReallyKnow(Map<GridUtils.Point2D, Integer> prevGrid, int numRows, int numCols) {
        var newGrid = prevGrid.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue() + 1));

        Queue<GridUtils.Point2D> toVisit = newGrid.entrySet()
            .stream().filter(entry -> entry.getValue() == 10)
            .map(Map.Entry::getKey)
            .collect(Collectors.toCollection(ArrayDeque::new));

        var visited = new HashSet<>(toVisit);

            while (!toVisit.isEmpty()) {
                var flashingOctopusi = toVisit.poll();
                newGrid.put(flashingOctopusi, 0);

                GridUtils
                    .neighbourCoords(flashingOctopusi.x(), flashingOctopusi.y(), numRows, numCols, true)
                    .stream().filter(nei -> !visited.contains(nei))
                    .forEach(nei -> {
                        var prevPower = newGrid.get(nei);
                        var newPower = prevPower + 1;
                        newGrid.put(nei, newPower);
                        if (newPower == 10) {
                            visited.add(nei);
                            toVisit.offer(nei);
                        }
                    });
            }


        return new GridAndFlashCount(newGrid, visited.size());
    }

    private static int hundredDays(Map<GridUtils.Point2D, Integer> prevGrid, int numRows, int numCols) {
        var result = 0;
        var grid = prevGrid;
        for (int i = 0; i < 100; i++) {
            var newGridAndFlashCount = takeStepDFSorBFSiDontReallyKnow(grid, numRows, numCols);
            result += newGridAndFlashCount.flashCount;
            grid = newGridAndFlashCount.grid;
        }
        return result;
    }

    private static int allFlash(Map<GridUtils.Point2D, Integer> prevGrid, int numRows, int numCols) {
        var day = 0;
        var grid = prevGrid;
        while(true) {
            var newGridAndFlashCount = takeStepDFSorBFSiDontReallyKnow(grid, numRows, numCols);
            day += 1;
            grid = newGridAndFlashCount.grid;
            if (newGridAndFlashCount.flashCount == numRows * numCols)
                return day;
        }

    }



    private static Map<GridUtils.Point2D, Integer> parseInput(List<String> input, int numRows, int numCols) {
        record Octo(GridUtils.Point2D position, int val) { }

        return IntStream.range(0, numRows)
            .boxed()
            .flatMap(rowIdx ->
                IntStream.range(0, numCols)
                    .mapToObj(colIdx -> new Octo(
                        new GridUtils.Point2D(rowIdx, colIdx),
                        input.get(rowIdx).charAt(colIdx) - '0')
                    )
            ).collect(Collectors.toMap(Octo::position, Octo::val));
    }

    record GridAndFlashCount(Map<GridUtils.Point2D, Integer> grid, int flashCount){}

}




