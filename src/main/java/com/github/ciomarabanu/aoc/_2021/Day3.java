package com.github.ciomarabanu.aoc._2021;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day3 {
    public static void main(String[] args) {
        var reader = FileReaderFactory.build();
        var binary = reader.readFileAsStrings("2021/day3");

        System.out.println(powerConsumption(binary));
        System.out.println(partII(binary));
    }

    public static int powerConsumption(List<String> binary) {
        String gamma = "";
        String epsilon = "";
        var digitsSum = new int[(binary.get(0).length())];
        for (String num : binary) {
            for (int j = 0; j < digitsSum.length; j++) {
                digitsSum[j] += Character.getNumericValue(num.charAt(j));
            }
        }
        for (int j : digitsSum) {

            if (j >= binary.size() / 2) {
                gamma = gamma.concat("1");
                epsilon = epsilon.concat("0");
            } else {
                gamma = gamma.concat("0");
                epsilon = epsilon.concat("1");
            }
        }
        return Integer.parseInt(epsilon, 2) * Integer.parseInt(gamma, 2);
    }

    public static int partII(List<String> binary) {
        var o2 = new ArrayList<String>();
        var co2 = new ArrayList<String>();

        for (String num : binary) {
            if (num.charAt(0) == '1') {
                o2.add(num);
            } else {
                co2.add(num);
            }
        }
        if (o2.size() < co2.size()) {
            var dummy = co2;
            co2 = o2;
            o2 = dummy;
        }

        int i = 1;
        while (o2.size() > 1) {
            var unfilteredO2Size = o2.size();
            int finalI = i;
            var filteredO2 = (ArrayList<String>) o2.stream().filter(n -> n.charAt(finalI) == '1')
                   .collect(Collectors.toList());
            if (filteredO2.size() >= unfilteredO2Size - filteredO2.size()) {
                o2 = filteredO2;
            } else {
                o2 = (ArrayList<String>) o2.stream().filter(n -> n.charAt(finalI) == '0')
                        .collect(Collectors.toList());
            }
            i++;
        }

        int j = 1;
        while (co2.size() > 1) {
            var unfilteredCO2Size = co2.size();
            int finalJ = j;
            var filteredCO2 = (ArrayList<String>) co2.stream().filter(n -> n.charAt(finalJ) == '1')
                    .collect(Collectors.toList());
            if (filteredCO2.size() < unfilteredCO2Size - filteredCO2.size()) {
                co2 = filteredCO2;
            } else {
                co2 = (ArrayList<String>) co2.stream().filter(n -> n.charAt(finalJ) == '0')
                        .collect(Collectors.toList());
            }
            j++;
        }
        return Integer.parseInt(o2.get(0), 2) * Integer.parseInt(co2.get(0), 2);
    }
}
