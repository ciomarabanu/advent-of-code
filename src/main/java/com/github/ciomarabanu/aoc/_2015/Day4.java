package com.github.ciomarabanu.aoc._2015;

import com.github.ciomarabanu.aoc.utils.FileReaderFactory;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Optional;
import java.util.stream.Stream;

public class Day4 {
    public static void main(String[] args) {
        var reader = FileReaderFactory.build();
        var input = reader.readFileAsStrings("2015/day4").get(0);
        System.out.println(solve(input, "00000"));
        System.out.println(solve(input, "000000"));
    }

    private static Optional<Integer> solve(String input, String prefix) {
        return Stream.iterate(1, i -> i + 1)
            .filter(suffix -> DigestUtils.md5Hex(input + suffix).startsWith(prefix))
            .findFirst();
    }
}
