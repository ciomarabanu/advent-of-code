package com.github.ciomarabanu.aoc._2021;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day6 {
    public static void main(String[] args) {
        var reader = FileReaderFactory.build();
        var fish = Arrays.stream(reader
                .readFileAsStrings("2021/day6").get(0).split(",")
        ).map(Integer::parseInt).toList();

        System.out.println(spawnFish(fish, 80));
        System.out.println(spawnFish(fish, 256));
    }

    public static long spawnFish(List<Integer> initialTimeToSpawn, Integer days) {
        var spawnSchedule = LongStream.of(new long[9]).boxed().collect(Collectors.toList());

        for (var timeToSpawn: initialTimeToSpawn) {
            spawnSchedule.set(timeToSpawn, spawnSchedule.get(timeToSpawn) + 1);
        }

        for (int i = 1; i <= days; i++) {
            Collections.rotate(spawnSchedule, -1);
            spawnSchedule.set(6, spawnSchedule.get(6) + spawnSchedule.get(8));
        }

        return spawnSchedule.stream().mapToLong(Long::longValue).sum();
    }
}
