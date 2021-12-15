package day15

def inputFile = new File("../../resources/day15/input")

def lines = inputFile.readLines()
List<List<Integer>> cavern = lines.collect { it.split("").collect { it as int } }

static Set<Position> adjacentLocations(int col, int row, int colSize, int rowSize) {
    def adjacentLocations = []

    if (row < rowSize - 1) adjacentLocations.add(new Position(row: row + 1, col: col))
    if (row > 0) adjacentLocations.add(new Position(row: row - 1, col: col))
    if (col < colSize - 1) adjacentLocations.add(new Position(row: row, col: col + 1))
    if (col > 0) adjacentLocations.add(new Position(row: row, col: col - 1))

    adjacentLocations
}

def part1(def cavern) {
    final int MAX_COL = cavern.size()
    final int MAX_ROW = cavern[0].size()

    int[][] cost = [[Integer.MAX_VALUE] * MAX_COL] * MAX_ROW

    cost[0][0] = 0

    def notVisited = [] as Set
    for (i in 0..<MAX_COL) {
        for (j in 0..<MAX_ROW) {
            notVisited.add(new Position(col: i, row: j))
        }
    }
    while (!notVisited.isEmpty()) {
        Position u = minDistancePoint(cost, notVisited)
        notVisited.remove(u)
        def neighbours = adjacentLocations(u.col, u.row, MAX_COL, MAX_ROW)
        for (def neighbour : neighbours) {
            int newDistance = cost[u.col][u.row] + cavern[neighbour.col][neighbour.row]
            int oldDistance = cost[neighbour.col][neighbour.row]
            if (newDistance < oldDistance) {
                cost[neighbour.col][neighbour.row] = newDistance
            }
        }
    }
    cost[MAX_COL - 1][MAX_ROW - 1]
}

println part1(cavern)

Position minDistancePoint(int[][] cost, Set<Position> positions) {
    int minDist = Integer.MAX_VALUE
    Position minPosition = null
    for (position in positions) {
        if (cost[position.col][position.row] < minDist) {
            minDist = cost[position.col][position.row]
            minPosition = position
        }
    }
    minPosition
}