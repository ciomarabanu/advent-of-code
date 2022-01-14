package com.github.ciomarabanu.aoc.utils;

import java.util.List;

public interface FileReader {
    List<String> readFileAsStrings(String fileName);
    List<Integer> readFileAsIntegers(String fileName);
}
