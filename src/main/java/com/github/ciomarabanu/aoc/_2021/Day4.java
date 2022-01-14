package com.github.ciomarabanu.aoc._2021;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day4 {

    public static void main(String[] args) {

//        parsing... only parsing...

        var reader = FileReaderFactory.build();
        List<String> input = reader.readFileAsStrings("2021/day4");
        var bingoNums = Arrays
                .stream(input.get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        var lines = input.stream().skip(1)
                .filter(s -> !s.isEmpty())
                .map(row -> Arrays.stream(row
                        .split(" "))
                        .filter(num -> !num.isEmpty())
                        .map(Integer::parseInt)
                        .map(NumAndBool::new)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        var boardSize = 5;
        var boards = IntStream
                .range(0, (lines.size() + boardSize - 1) / boardSize)
                .mapToObj(i -> lines.subList(i * boardSize, Math.min(lines.size(), (i + 1) * boardSize)))
                .collect(Collectors.toList());
        var bingo = new ArrayList<List<String>>();

        System.out.println(bingoGame(bingoNums, boards));
    }

    public static List<Integer> bingoGame(List<Integer> bingoNums, List<List<List<NumAndBool>>> boards) {
        var winningBoardsScores = new ArrayList<Integer>();
        var activeBoardsIdxs = IntStream.range(0, boards.size()).boxed().collect(Collectors.toSet());
        var winningBoards = new HashSet<Integer>();
        for (int num : bingoNums) {
            for (var board : activeBoardsIdxs) {
                if (!winningBoards.contains(board)) {
                    if (isWinningBoard(boards.get(board), num)) {
                        winningBoardsScores.add(getScore(boards.get(board), num));
                        winningBoards.add(board);
                    }
                }
            }
        }
        return winningBoardsScores;
    }

    private static boolean isWinningBoard(List<List<NumAndBool>> board, int num) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (board.get(row).get(col).num == num) {
                    board.get(row).get(col).markGuessed();
                    if (rowCompleted(board.get(row)) || colCompleted(board, row)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean rowCompleted(List<NumAndBool> row) {
        for (NumAndBool num : row) {
            if (!num.guessed) {
                return false;
            }
        }
        return true;
    }

    private static boolean colCompleted(List<List<NumAndBool>> board, int referenceColIdx) {
        for (var row : board) {
            if (!row.get(referenceColIdx).guessed) {
                return false;
            }
        }
        return true;
    }

    private static int getScore(List<List<NumAndBool>> board, int winningNum) {
        int score = 0;
        for (var row : board) {
            for (var n : row) {
                if (!n.guessed)
                    score += n.num;
            }
        }
        return score * winningNum;
    }

}

class NumAndBool {
    int num;
    boolean guessed = false;

    public NumAndBool(int num) {
        this.num = num;
    }

    public void markGuessed() {
        this.guessed = true;
    }

}