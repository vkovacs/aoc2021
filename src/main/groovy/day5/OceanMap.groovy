package day5

import com.github.javaparser.utils.LineSeparator

import static java.lang.Math.max
import static java.lang.Math.min

class OceanMap {
    int[][] map

    OceanMap(int size) {
        map = new int[size][size]
    }

    void addVent(Vent vent) {
        if (vent.x1 == vent.x2) {
            def (int x, int minY, int maxY) = [vent.x1, min(vent.y1, vent.y2), max(vent.y1, vent.y2)]

            for (int y = minY; y <= maxY; y++) {
                map[x][y] += 1
            }
        }

        if (vent.y1 == vent.y2) {
            def (int y, int minX, int maxX) = [vent.y1, min(vent.x1, vent.x2), max(vent.x1, vent.x2)]

            for (int x = minX; x <= maxX; x++) {
                map[x][y] += 1
            }
        }

        if (vent.x1 != vent.x2 && vent.y1 != vent.y2) {
            int xDirection = vent.x1 < vent.x2 ? 1 : -1
            int yDirection = vent.y1 < vent.y2 ? 1 : -1

            int x = vent.x1
            int y = vent.y1
            map[x][y] += 1

            while (x != vent.x2 && y != vent.y2) {
                x += xDirection
                y += yDirection
                map[x][y] += 1
            }
        }
    }

    List<Integer> flatMap() {
        def list = []
        for (i in 0..<map.length) {
            for (j in 0..<map.length) {
                list.add(map[i][j])
            }
        }
        return list
    }

    @Override
    String toString() {
        StringBuilder result = new StringBuilder(map.length * map.length)
        for (i in 0..<map.length) {
            for (j in 0..<map.length) {
                result.append(map[j][i] + " ")
            }
            result.append(LineSeparator.SYSTEM)
        }
        return result.toString()
    }
}
