package com.github.ciomarabanu.aoc._2021;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day10 {
    public static void main(String[] args) {
        var reader = FileReaderFactory.build();
        var paranthesis = reader.readFileAsStrings("2021/day10");
        System.out.println(getParenScore(paranthesis));
        System.out.println(getMiddleScore(paranthesis));
    }

    public static int getParenScore(List<String> paren) {
        var points = new HashMap<Character, Integer>();
        points.put(')', 3);
        points.put(']', 57);
        points.put('}', 1197);
        points.put('>', 25137);

        var score = 0;

        for (var line : paren){
            var helper = new Stack<Character>();
            for (var p : line.toCharArray()){
                if (p == '(' || p == '[' || p == '{' || p == '<'){
                    helper.add(p);
                } else {
                    var last = helper.peek();
                    if (p - last == 2 || p - last == 1){
                        helper.pop();
                    } else {
                        score += points.get(p);
                        break;
                    }
                }
            }
        }
        return score;
    }

    public static long getMiddleScore(List<String> paren) {
        var points = new HashMap<Character, Integer>();
        points.put('(', 1);
        points.put('[', 2);
        points.put('{', 3);
        points.put('<', 4);

        var allScores = paren.stream()
                .map(line -> {
            var helper = new Stack<Character>();
            for (var curr : line.toCharArray()){
                if (curr == '(' || curr == '[' || curr == '{' || curr == '<'){
                    helper.add(curr);
                } else {
                    var prev = helper.peek();
                    if (curr - prev == 2 || curr - prev == 1){
                    helper.pop();
                    } else {
                       return new Stack<>();
                }}

            }
            return helper;
        })
                .filter(openParens -> !openParens.isEmpty())
                .map(openParens -> {
                    var score = 0L;
                    while (!openParens.isEmpty()) {
                      score *= 5;
                      score += points.get(openParens.pop());
                    }
                    return score;
                })
                .sorted()
                .collect(Collectors.toList());

        return allScores.get(allScores.size() / 2);
    }

}
