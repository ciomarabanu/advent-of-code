package com.github.ciomarabanu.aoc._2021;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Day10 {
    public static void main(String[] args) {
        var reader = FileReaderFactory.build();
        var paranthesis = reader.readFileAsStrings("2021/day10");
        System.out.println(getParenScore(paranthesis));
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
            helper.add(line.toCharArray()[0]);
            for (var p : line.toCharArray()){
                var last = helper.peek();
                if (p == '(' || p == '[' || p == '{' || p == '<'){
                    helper.add(p);
                } else if (p - last == 2 || p - last == 1){
                    helper.pop();
                } else {
                    score += points.get(p);
                    break;
                }
            }
        }
        return score;
    }

}
