package com.github.ciomarabanu.aoc._2021;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day8 {
    public static void main(String[] args) {
        var reader = FileReaderFactory.build();
        var entries = reader.readFileAsStrings("2021/day8").stream()
                .map(entry -> entry.split(" \\| "))
                .map(entry -> new Entry(Arrays.asList(entry[0].split(" ")), (Arrays.asList(entry[1].split(" ")))))
                .collect(Collectors.toList());
        var outputs = entries.stream()
                .map(Entry::output)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        var allNums = entries.stream()
                .map(Entry::litLines)
                .collect(Collectors.toList());

        System.out.println(partII(entries));

    }

    public static int count1and4and7and8(List<String> outputs) {
        return (int) outputs.stream().filter(output -> output.length() == 2 ||
                output.length() == 3 ||
                output.length() == 4 ||
                output.length() == 7)
                .count();
    }

    public static int decoder(Entry entry) {
        StringBuilder result = new StringBuilder();
        var legend = new HashMap<Set<Character>, Integer>();
        legend.put(new HashSet<>(Arrays.asList('a', 'b', 'c', 'e', 'f', 'g')), 0);
        legend.put(new HashSet<>(Arrays.asList('c', 'f')), 1);
        legend.put(new HashSet<>(Arrays.asList('a', 'c', 'd', 'e', 'g')), 2);
        legend.put(new HashSet<>(Arrays.asList('a', 'c', 'd', 'f', 'g')), 3);
        legend.put(new HashSet<>(Arrays.asList('b', 'd', 'c', 'f')), 4);
        legend.put(new HashSet<>(Arrays.asList('a', 'b', 'd', 'f', 'g')), 5);
        legend.put(new HashSet<>(Arrays.asList('a', 'b', 'd', 'e', 'f', 'g')), 6);
        legend.put(new HashSet<>(Arrays.asList('a', 'c', 'f')), 7);
        legend.put(new HashSet<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g')), 8);
        legend.put(new HashSet<>(Arrays.asList('a', 'b', 'c', 'd', 'f', 'g')), 9);


        var iterator = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g'};

        var generate1to9 = new HashMap<Integer, Set<Character>>();

        generate1to9.put(1, entry.litLines().stream()
                .filter(elem -> elem.length() == 2)
                .collect(Collectors.toList())
                .get(0)
                .chars()
                .mapToObj(chr -> (char) chr)
                .collect(Collectors.toSet()));


        generate1to9.put(7, entry.litLines().stream()
                .filter(elem -> elem.length() == 3)
                .collect(Collectors.toList())
                .get(0)
                .chars()
                .mapToObj(chr -> (char) chr)
                .collect(Collectors.toSet()));

        generate1to9.put(4, entry.litLines().stream()
                .filter(elem -> elem.length() == 4)
                .collect(Collectors.toList())
                .get(0)
                .chars()
                .mapToObj(chr -> (char) chr)
                .collect(Collectors.toSet()));

        generate1to9.put(8, entry.litLines().stream()
                .filter(elem -> elem.length() == 7)
                .collect(Collectors.toList())
                .get(0)
                .chars()
                .mapToObj(chr -> (char) chr)
                .collect(Collectors.toSet()));

        var lineCount = String.join("", entry.litLines())
                .chars().mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        var a = new HashSet<>(generate1to9.get(7));
        Set<Character> c1 = generate1to9.get(1);
        a.removeAll(c1);

        var g = new HashSet<>(generate1to9.get(8));
        g.removeAll(generate1to9.get(7));
        g.removeAll(generate1to9.get(4));
        for (var c2 : iterator) {
            if (lineCount.get(c2) == 4) {
                g.remove(c2);
            }
        }

        var decoder = new HashMap<Character, String>();
        decoder.put(a.toString().charAt(1), "a");
        decoder.put(g.toString().charAt(1), "g");

        for (var line : iterator) {
            switch (lineCount.get(line).intValue()) {
                case 9 -> decoder.put(line, "f");
                case 6 -> decoder.put(line, "b");
                case 4 -> decoder.put(line, "e");
                case 8 -> {
                    if (!decoder.containsKey(line)) {
                        decoder.put(line, "c");
                    }
                }
                case 7 -> {
                    if (!decoder.containsKey(line)) {
                        decoder.put(line, "d");
                    }
                }
            }
        }

        for (var digit : entry.output()) {
            StringBuilder decodedDigit = new StringBuilder();
            for (var line : digit.toCharArray()) {
                decodedDigit.append(decoder.get(line));
            }
            var decodedDigitSet = decodedDigit.toString().chars().mapToObj(chr -> (char) chr)
                    .collect(Collectors.toSet());
            result.append(Integer.toString(legend.get(decodedDigitSet)));

        }

        return Integer.parseInt(result.toString());
    }

    public static int partII(List<Entry> input) {
        var result = 0;

        for (var entry : input) {
            result += decoder(entry);
        }
        return result;
    }
}

record Entry(List<String> litLines, List<String> output) {
}