package com.github.ciomarabanu.aoc.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GridUtils {

    public static List<Point2D> neighbourCoords(int x, int y, int numRows, int numCols, boolean includeDiagonals) {
        var result = new ArrayList<Point2D>();
        result.add(new Point2D(x, y - 1));
        result.add(new Point2D(x - 1, y));
        result.add(new Point2D(x, y + 1));
        result.add(new Point2D(x + 1, y));

        if (includeDiagonals) {
            result.add(new Point2D(x - 1, y - 1));
            result.add(new Point2D(x - 1, y + 1));
            result.add(new Point2D(x + 1, y + 1));
            result.add(new Point2D(x + 1, y - 1));
        }

        return result.stream()
                .filter(point -> (point.x >= 0 && point.x < numCols)
                        && (point.y >= 0 && point.y < numRows))
                .collect(Collectors.toList());
    }

    public record Point2D(int x, int y) {}
}

interface MakePoint2D<T> {
    GridUtils.Point2D transform(T t);
}
