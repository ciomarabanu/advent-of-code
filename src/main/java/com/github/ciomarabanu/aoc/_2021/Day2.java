package com.github.ciomarabanu.aoc._2021;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;

import java.util.List;
import java.util.stream.Collectors;

public class Day2 {
    public static void main(String[] args) {
        var reader = FileReaderFactory.build();
        var trajectoryPLan = reader.readFileAsStrings("2021/day2").stream()
                .map(s -> new directionAndValue(s.split(" ")[0],
                        Integer.parseInt(s.split(" ")[1]))).collect(Collectors.toList());
        System.out.println(computeSubmarineTrajectoryPlan(trajectoryPLan));
        System.out.println(computeTrajectoryBetter(trajectoryPLan));

    }

    public static int computeSubmarineTrajectoryPlan(List<directionAndValue> plan) {
        int depth = 0;
        int forward = 0;
        for (var move : plan) {
            switch (move.direction()) {
                case "forward" -> forward += move.value();
                case "up" -> depth -= move.value();
                case "down" -> depth += move.value();
            }
        }
        return depth * forward;
//        Acc acc = new Acc(0, 0);
//        var preliminary = plan.stream().reduce(acc, elem -> {
//            if (elem.direction().equals("up"))
//                acc.depth += elem.value();
//            if (elem.direction().equals("down"))
//                acc.depth -= elem.value();
//            if (elem.direction().equals("forward"))
//                acc.forward += elem.value();
//        })


    }

    public static int computeTrajectoryBetter(List<directionAndValue> plan) {
        int depth = 0;
        int forward = 0;
        int aim = 0;
        for (var move : plan) {
            switch (move.direction()) {
                case "forward" -> {
                    forward += move.value();
                    depth += aim * move.value();
                }
                case "up" -> {
                    aim -= move.value();
                }
                case "down" -> {
                    aim += move.value();
                }
            }
        }
        return depth * forward;
    }
}

record directionAndValue(String direction, int value) {
}

class Acc {
    int depth = 0;
    int forward = 0;

    public Acc(int depth, int forward) {
        this.depth = depth;
        this.forward = forward;
    }
}
