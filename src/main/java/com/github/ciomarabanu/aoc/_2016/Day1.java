package com.github.ciomarabanu.aoc._2016;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;
import com.github.ciomarabanu.aoc.utils.GridUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Day1 {
    public static void main(String[] args) {

        var reader = FileReaderFactory.build();
        var input = Arrays.stream(reader.readFileAsStrings("2016/day1")
            .get(0).split(", "))
            .map(s -> new DirAndSteps(s.substring(0, 1), Integer.parseInt(s.substring(1))))
            .collect(Collectors.toList());

        System.out.println(easterBunnyHQDistance(input));
    }

    public static int easterBunnyHQDistance(List<DirAndSteps> path) {
        var checkpoints = new HashSet<GridUtils.Point2D>();
        int dir = 0; // 0 == north
        var lastCheckpoint = new GridUtils.Point2D(0, 0);
        checkpoints.add(lastCheckpoint);

        for (var move : path) {
            dir += move.dir.equals("L") ? 3 : 1;
            dir = dir % 4;
            int i = addIntersections(dir, lastCheckpoint, checkpoints, move);
            if (i != -1) {
                return i;
            }
            switch (dir) {
                case 0 -> lastCheckpoint = new GridUtils.Point2D(lastCheckpoint.x(), lastCheckpoint.y() + move.steps);
                case 1 -> lastCheckpoint = new GridUtils.Point2D(lastCheckpoint.x() + move.steps, lastCheckpoint.y());
                case 2 -> lastCheckpoint = new GridUtils.Point2D(lastCheckpoint.x(), lastCheckpoint.y() - move.steps);
                case 3 -> lastCheckpoint = new GridUtils.Point2D(lastCheckpoint.x() - move.steps, lastCheckpoint.y());
            }

            if (checkpoints.contains(lastCheckpoint)){
                return Math.abs(lastCheckpoint.x()) + Math.abs(lastCheckpoint.y());
            } else {
                checkpoints.add(lastCheckpoint);
            }

        }
        return Math.abs(lastCheckpoint.x()) + Math.abs(lastCheckpoint.y());
    }

    private static int addIntersections (int dir,
                                          GridUtils.Point2D lastCheckpoint,
                                          HashSet<GridUtils.Point2D> checkpoints,
                                          DirAndSteps move) {

        for (int i = 1; i < move.steps; i++) {
            var intermediate = lastCheckpoint;
            switch (dir) {
                case 0 -> intermediate = new GridUtils.Point2D(lastCheckpoint.x(), lastCheckpoint.y() + i);
                case 1 -> intermediate = new GridUtils.Point2D(lastCheckpoint.x() + i, lastCheckpoint.y());
                case 2 -> intermediate = new GridUtils.Point2D(lastCheckpoint.x(), lastCheckpoint.y() - i);
                case 3 -> intermediate = new GridUtils.Point2D(lastCheckpoint.x() - i, lastCheckpoint.y());
            }
            if (!checkpoints.add(intermediate)) {
                return Math.abs(intermediate.x()) + Math.abs(intermediate.y());
            }
        }

        return -1;
    }
    record DirAndSteps(String dir, int steps){}
}
