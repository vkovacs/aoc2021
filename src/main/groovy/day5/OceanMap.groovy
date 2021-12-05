package day5

import com.github.javaparser.utils.LineSeparator

class OceanMap {
    public static final int MAP_SIZE = 1000 + 1
    int[][] map = new int[MAP_SIZE][MAP_SIZE]

    void addVent(Vent vent) {
        if (vent.x1 == vent.x2) {
            int x = vent.x1
            if (vent.y1 < vent.y2) {
                for (int y = vent.y1; y <= vent.y2; y++) {
                    map[x][y] += 1
                }
            } else {
                for (int y = vent.y2; y <= vent.y1; y++) {
                    map[x][y] += 1
                }
            }
        }
        if (vent.y1 == vent.y2) {
            int y = vent.y1
            if (vent.x1 < vent.x2) {
                for (int x = vent.x1; x <= vent.x2; x++) {
                    map[x][y] += 1
                }
            } else {
                for (int x = vent.x2; x <= vent.x1; x++) {
                    map[x][y] += 1
                }
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
        String result = ""
        for (i in 0..<MAP_SIZE) {
            for (j in 0..<MAP_SIZE) {
                result += map[j][i] + " "
            }
            result += LineSeparator.SYSTEM
        }
        return result
    }
}
