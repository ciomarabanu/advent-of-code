package com.github.ciomarabanu.aoc._2015;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;
import com.github.ciomarabanu.aoc.utils.GridUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day6 {
    public static void main(String[] args) {
        var reader = FileReaderFactory.build();
        var input = reader.readFileAsStrings("2015/day6");
        System.out.println(input);
        var betterInput = parseInput(input);
        System.out.println(betterInput);
        System.out.println(partII(betterInput));
        System.out.println(16*16);
    }

//    private static int lightsOn(List<List<String>> input) {
//        var grid = new boolean[1000][1000];
//
//        var a = input.stream()
//            .peek(command -> {
//                if (command.get(0).equals("turn_on")){
//                    turnOn(grid, command.get(1), command.get(3));
//                }
//                else if (command.get(0).equals("turn_off")) {
//                    turnOff(grid, command.get(1), command.get(3));
//                }
//                else
//                    toggle(grid, command.get(1), command.get(3));
//            }).collect(Collectors.toList());
//
//        var numLightsOn = 0;
//        for (var row : grid){
//            for (var light : row) {
//                if (light)
//                    numLightsOn++;
//            }
//        }
//
//        return numLightsOn;
//    }

    private static int partII(List<List<String>> input){
        var grid = new int[1000][1000];

        var a = input.stream()
            .peek(command -> {
                if (command.get(0).equals("turn_on")){
                    turnOn(grid, command.get(1), command.get(3));
                }
                else if (command.get(0).equals("turn_off")) {
                    turnOff(grid, command.get(1), command.get(3));
                }
                else
                    toggle(grid, command.get(1), command.get(3));
            }).collect(Collectors.toList());

        var numLightsOn = 0;
        for (var row : grid){
            for (var light : row) {
                    numLightsOn += light;
            }
        }

        return numLightsOn;
    }

    private static void turnOn(int[][] input, String cell1, String cell2) {
        int x1 = Integer.parseInt(cell1.split(",")[0]);
        int y1 = Integer.parseInt(cell1.split(",")[1]);
        int x2 = Integer.parseInt(cell2.split(",")[0]);
        int y2 = Integer.parseInt(cell2.split(",")[1]);

        for (int i = Math.min(x1, x2); i <= Math.max(x1 , x2); i++){
            for (int j = Math.min(y1, y2); j <= Math.max(y1 , y2); j++){
//                for partI make boolean[][]
//                input[i][j] = true;
                //        uncomment for partII + make int[][]
                input[i][j]++;
            }
        }



    }

    private static void turnOff(int[][] input, String cell1, String cell2) {
        int x1 = Integer.parseInt(cell1.split(",")[0]);
        int y1 = Integer.parseInt(cell1.split(",")[1]);
        int x2 = Integer.parseInt(cell2.split(",")[0]);
        int y2 = Integer.parseInt(cell2.split(",")[1]);

        for (int i = Math.min(x1, x2); i <= Math.max(x1 , x2); i++){
            for (int j = Math.min(y1, y2); j <= Math.max(y1 , y2); j++){

                //                for partI make boolean[][]
//                input[i][j] = false;

                //        uncomment for partII + make int[][]
                input[i][j] = input[i][j] == 0? 0 : input[i][j] - 1;
            }
        }
    }

    private static void toggle(int[][] input, String cell1, String cell2) {
        int x1 = Integer.parseInt(cell1.split(",")[0]);
        int y1 = Integer.parseInt(cell1.split(",")[1]);
        int x2 = Integer.parseInt(cell2.split(",")[0]);
        int y2 = Integer.parseInt(cell2.split(",")[1]);

        for (int i = Math.min(x1, x2); i <= Math.max(x1 , x2); i++){
            for (int j = Math.min(y1, y2); j <= Math.max(y1 , y2); j++){

                //                for partI make boolean[][]
//                input[i][j] = !input[i][j];

                //        uncomment for partII + make int[][]
                input[i][j] += 2;
            }
        }
    }

    private static List<List<String>> parseInput (List<String> input) {
        var a = input.stream()
            .map(s -> Arrays.stream(s.split(" ")).collect(Collectors.toList()))
//                .map(st -> st.matches("\\D") ? st
//                    : new GridUtils.Point2D(Integer.parseInt(st.split(",")[0])
//                                          , Integer.parseInt(st.split(",")[1]))).collect(Collectors.toList()))
            .collect(Collectors.toList());

        return a;
    }
}
