package day11

import day9.Position

def inputFile = new File("../../resources/day11/input")

List<List<Integer>> cavern = []

inputFile.eachLine {
    cavern.add(it.split("").collect {
        it as int
    })
}

Set<Position> increaseAll(List<List<Integer>> cavern) {
    Set<Position> willFlash = []
    for (i in 0..<cavern.size()) {
        for (j in 0..<cavern[0].size()) {
            cavern[i][j] = cavern[i][j] + 1
            if (cavern[i][j] > 9) willFlash.add(new Position(row: i, col: j))
        }
    }
    return willFlash
}

int flash(List<List<Integer>> cavern, Set<Position> willFlash, Set<Position> alreadyFlashed) {
    Set<Position> nextWillFlashCandidates = []
    willFlash.each {
        alreadyFlashed.add(it)
        def locations = adjacentLocations(it.row, it.col)
        locations.each {
            cavern[it.row][it.col] = cavern[it.row][it.col] + 1
            if (cavern[it.row][it.col] > 9) {
                nextWillFlashCandidates.add(it)
            }
        }
    }

    nextWillFlashCandidates.removeAll(alreadyFlashed)
    if (nextWillFlashCandidates.isEmpty()) {
        return willFlash.size()
    } else {
        return willFlash.size() + flash(cavern, nextWillFlashCandidates, alreadyFlashed)
    }
}

void resetFlashed(List<List<Integer>> cavern) {
    for (i in 0..<cavern.size()) {
        for (j in 0..<cavern[0].size()) {
            if (cavern[i][j] > 9) cavern[i][j] = 0
        }
    }
}

static Set<Position> adjacentLocations(int row, int col) {
    int rowSize = 10
    int colSize = 10
    def adjacentLocations = []

    if (row < rowSize - 1) adjacentLocations.add(new Position(row: row + 1, col: col))
    if (row > 0) adjacentLocations.add(new Position(row: row - 1, col: col))
    if (col < colSize - 1) adjacentLocations.add(new Position(row: row, col: col + 1))
    if (col > 0) adjacentLocations.add(new Position(row: row, col: col - 1))

    if (row > 0 && col > 0) adjacentLocations.add(new Position(row: row - 1, col: col - 1))
    if (row > 0 && col < colSize - 1) adjacentLocations.add(new Position(row: row - 1, col: col + 1))
    if (row < rowSize - 1 && col > 0) adjacentLocations.add(new Position(row: row + 1, col: col - 1))
    if (row < rowSize - 1 && col < colSize - 1) adjacentLocations.add(new Position(row: row + 1, col: col + 1))

    return adjacentLocations
}

def part1(List<List<Integer>> cavern) {
    int flashCounter = 0
    100.times {
        def willFlash = increaseAll(cavern)
        flashCounter += flash(cavern, willFlash, new HashSet<Position>())
        resetFlashed(cavern)
    }
    flashCounter
}

println part1(cavern.collect {
    it.collect()
})
def part2(List<List<Integer>> cavern) {
    long step = 0
    while (cavern.flatten().sum() != 0) {
        def willFlash = increaseAll(cavern)
        flash(cavern, willFlash, new HashSet<Position>())
        resetFlashed(cavern)
        step++
    }
    step
}

println part2(cavern.collect {
    it.collect()
})