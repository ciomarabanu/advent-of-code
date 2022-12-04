package com.github.ciomarabanu.aoc._2015;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day7 {
    public static void main(String[] args) {
        var reader = FileReaderFactory.build();
        var input = reader.readFileAsStrings("2015/day7");
       wiresValuesMap(input);
    }

    private static HashMap<String, Integer> wiresValuesMap(List<String> input) {
        var wires = new HashMap<String, Integer>();

//        separates wires from their commands
        var parseInput = input.stream().map(line -> Arrays.stream(line.split("-> "))
            .map(l -> Arrays.asList(l.split(" "))).collect(Collectors.toList()))
            .peek(command -> {
                var wire = command.get(1).get(0);
                wires.put(wire, -1);
            })
            .peek(command -> {
                if (command.get(0).size() == 1){
                    var value = Integer.parseInt(command.get(0).get(0));
                    wires.put(command.get(1).get(0), value);
                }
            })
           .collect(Collectors.toList());

        return wires;
    }

    private void parseLine(String command) {
        var a  = command.split(" -> ");

        if (a[0].contains("NOT")) {
            var result = parseNOT(a[0]);
        }

    }

    private Not parseNOT(String command) {
        var wire = command.split(" ")[1];
        return new Not(wire);
    }

    private WireValue parseWireValue(String s) {
        try {
            return new FinalValue(Integer.parseInt(s));
        } catch (NumberFormatException nfe) {
            return new Reference(s);
        }
    }

    private interface WireValue {}
    record Reference(String wire) implements WireValue {}
    record FinalValue(int wire) implements WireValue {}

    private interface Command {}
    record Not(String wire) implements Command {}
    record LShift(String wire, int shift) implements Command {}
    record RShift(String wire, int shift) implements Command {}
    record And(WireValue wire1, WireValue wire2) implements Command {}
    record Or(WireValue wire1, WireValue wire2) implements Command {}
    record Pipe(WireValue wire) implements Command {}
}
