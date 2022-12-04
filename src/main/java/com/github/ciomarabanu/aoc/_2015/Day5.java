package com.github.ciomarabanu.aoc._2015;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;

import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day5 {
    public static void main(String[] args) {
        var reader = FileReaderFactory.build();
        var input = reader.readFileAsStrings("2015/day5");
        System.out.println(partI(input));
        System.out.println(partII(input));
//        System.out.println(hasPair("acacvbnfgrt"));
//        System.out.println(hasPair("aacvbnfgrac"));
//        System.out.println(hasPair("aaaaacvbnfgr"));
//        System.out.println(hasPair("bnfgrcvaaa"));

    }

    //FU****G REGEX!!!!!!
    private static long partI(List<String> input) {

        var regex = "[[ab|cd]|[pq|xy]]+";
        var a = input.stream()
            .filter(string -> {
                Pattern pattern = Pattern.compile("[aeiou].*[aeiou].*[aeiou]");
                Matcher matcher = pattern.matcher(string);
                boolean matchFound = matcher.find();

                return matchFound;
            })
            .filter(string -> {
                Pattern pattern = Pattern.compile("(ab|cd|pq|xy)");
                Matcher matcher = pattern.matcher(string);
                boolean matchFound = matcher.find();

                return !matchFound;
            })
            .filter(string -> {
                Pattern pattern = Pattern.compile(".*([a-z])\\1{1}.*");
                Matcher matcher = pattern.matcher(string);
                boolean matchFound = matcher.find();

                return matchFound;
            })
            .count();
        return a;
    }

    private static long partII(List<String> input) {
        return input.stream()
            .filter(string -> hasPairsWithStreams(string ) && hasRepeatWithLetterBetween(string))
            .count();
    }

//    this is wrong vvv... but it's mine <3
    private static boolean hasPair(String string) {
        var working = string.toCharArray();
        var possiblePairs = new HashSet<String>();
        String pair = string.substring(0,2);
        possiblePairs.add(pair);
        var consecutiveCount = 1;
        if (pair.charAt(0) == pair.charAt(1))
            consecutiveCount++;

        for (int i = 1; i < working.length - 1; i++) {
            pair = string.substring(i, i + 2);
            if (possiblePairs.contains(pair) && working[i] != working[i - 1])
                return true;
            if (pair.charAt(0) == pair.charAt(1)) {
                consecutiveCount++;
                if (consecutiveCount == 4)
                return true;
            }
            possiblePairs.add(pair);
        }
        return false;
    }

    private static boolean hasPairsWithStreams(String s) {
        return IntStream.range(0,s.length() - 3)
            .anyMatch(i -> s.substring(i + 2).contains(s.substring(i, i + 2)));
    }

    private static boolean hasRepeatWithLetterBetween(String s) {
        return IntStream.range(2, s.length())
            .anyMatch(i -> s.charAt(i) == s.charAt(i - 2));
    }
}
