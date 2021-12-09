package day9

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

def inputFile = new File("../../resources/day9/input")

List<List<Integer>> heightMap = []
inputFile.eachLine {
    line -> heightMap.add(line.split("").collect { it as int })
}

@ToString
@EqualsAndHashCode
class Position {
    int row, col
}

static def part1(List<List<Integer>> heightMap) {
    int riskLevel = 0
    Set<Position> lowPointPositions = []

    for (i in 0..<heightMap.size()) {
        for (j in 0..<heightMap[0].size()) {
            int point = heightMap[i][j]
            if (point < adjacentLocationHeights(heightMap, i, j).min()) {
                lowPointPositions.add(new Position(row: i, col: j))
                riskLevel += 1 + point
            }
        }
    }
    new Tuple(riskLevel, lowPointPositions)
}

static List<Integer> adjacentLocationHeights(List<List<Integer>> heightMap, int row, int col) {
    adjacentLocations(heightMap, row, col).collect{heightMap[it.row][it.col]}
}

static Set<Position> adjacentLocations(List<List<Integer>> heightMap, int row, int col) {
    int rowSize = heightMap.size()
    int colSize = heightMap[0].size()
    def adjacentLocationHeights = []

    if (row < rowSize - 1) adjacentLocationHeights.add(new Position(row: row + 1, col: col))
    if (row > 0) adjacentLocationHeights.add(new Position(row: row - 1, col: col))
    if (col < colSize - 1) adjacentLocationHeights.add(new Position(row: row, col: col + 1))
    if (col > 0) adjacentLocationHeights.add(new Position(row: row, col: col - 1))

    return adjacentLocationHeights
}

def (int riskLevel, Set<Position> lowPointPositions) = part1(heightMap)

println riskLevel

static def part2(List<List<Integer>> heightMap, Set<Position> lowPointPositions) {
    List<Integer> basinSizes = []
    lowPointPositions.each {
        Set<Position> basinPositions = findBasinPositions(heightMap, new HashSet<Position>(), adjacentLocations(heightMap, it.row, it.col).findAll {heightMap[it.row][it.col] != 9})
        basinSizes.add(basinPositions.size())
    }

    basinSizes.sort()
    return basinSizes[basinSizes.size() - 1] * basinSizes[basinSizes.size() - 2] * basinSizes[basinSizes.size() - 3]
}

static Set<Position> findBasinPositions(List<List<Integer>> heightMap, Set<Position> visitedPositions, Set<Position> newPositions) {
    visitedPositions.addAll(newPositions)

    Set<Position> newNewPoints = []
    newPositions.each {
        adjacentLocations(heightMap, it.row, it.col).findAll { heightMap[it.row][it.col] != 9 }.each {
            if (!visitedPositions.contains(it)) {
                newNewPoints.add(it)
            }
        }
    }

    if (newNewPoints.isEmpty()) {
        return visitedPositions
    } else {
        findBasinPositions(heightMap, visitedPositions, newNewPoints)
    }
}

println part2(heightMap, lowPointPositions)