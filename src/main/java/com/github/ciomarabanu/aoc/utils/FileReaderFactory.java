package com.github.ciomarabanu.aoc.utils;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FileReaderFactory {
    public static FileReader build() {
        return new FileReaderImpl();
    }
}

class FileReaderImpl implements FileReader {

    @Override
    public List<String> readFileAsStrings(String fileName) {
       try { return Files.readAllLines(new File(getClass().getClassLoader().getResource(fileName).getFile()).toPath()); }
       catch (Exception e) {
           throw new RuntimeException("failed to open file", e);
       }

    }

    @Override
    public List<Integer> readFileAsIntegers(String fileName) {
        return readFileAsStrings(fileName).stream().map(Integer::valueOf).collect(Collectors.toList());
    }
}