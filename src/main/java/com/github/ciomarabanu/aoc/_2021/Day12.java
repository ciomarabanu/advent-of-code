package com.github.ciomarabanu.aoc._2021;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;

import java.sql.Connection;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day12 {
    public static void main(String[] args) {
        var reader = FileReaderFactory.build();
        var input = reader.readFileAsStrings("2021/day12");

        var v = input.stream()
            .flatMap(s -> Arrays.stream(s.split("-")));

        var roomConnections = input.stream()
            .flatMap(s -> Arrays.stream(s.split("-")))
            .distinct()
            .collect(Collectors.toMap(Function.identity(), ignored -> new ArrayList<String>()));

         input.stream()
            .map(s -> Arrays.asList(s.split("-")))
            .forEach(pair -> {
                var room1 = pair.get(0);
                var room2 = pair.get(1);
                roomConnections.get(room1).add(room2);
                roomConnections.get(room2).add(room1);
            });

        System.out.println(roomConnections);

    }

//    private static int part1(Map<String, ArrayList<String>> roomConnections) {
//        var pathsFound = new HashMap<String , HashSet<List<String>>>(); //  <room, paths found for said room>
//        for (var room : roomConnections.get("start")) {
//
//        }
//    }
}
