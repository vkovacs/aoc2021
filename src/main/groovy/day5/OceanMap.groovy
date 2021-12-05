package day5

import com.github.javaparser.utils.LineSeparator

import static java.lang.Math.max
import static java.lang.Math.min

class OceanMap {
    public static final int MAP_SIZE = 1000 + 1
    int[][] map = new int[MAP_SIZE][MAP_SIZE]

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
    }

    List<Integer> flatMap() {
        def list = []
        for (i in 0..<MAP_SIZE) {
            for (j in 0..<MAP_SIZE) {
                list.add(map[i][j])
            }
        }
        return list
    }

    @Override
    String toString() {
        StringBuilder result = new StringBuilder(MAP_SIZE * MAP_SIZE)
        for (i in 0..<MAP_SIZE) {
            for (j in 0..<MAP_SIZE) {
                result.append(map[j][i] + " ")
            }
            result.append(LineSeparator.SYSTEM)
        }
        return result.toString()
    }
}
