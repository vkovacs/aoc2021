package day9

def inputFile = new File("../../resources/day9/input")

List<List<Integer>> heightMap = []
inputFile.eachLine {
    line -> heightMap.add(line.split("").collect { it as int })
}

static List<Integer> adjacentLocationHeights(List<List<Integer>> heightMap, int row, int col) {
    int rowSize = heightMap.size()
    int colSize = heightMap[0].size()
    def adjacentLocationHeights = []

    if (row < rowSize - 1) adjacentLocationHeights.add(heightMap[row + 1][col])
    if (row > 0) adjacentLocationHeights.add(heightMap[row - 1][col])
    if (col < colSize - 1) adjacentLocationHeights.add(heightMap[row][col + 1])
    if (col > 0) adjacentLocationHeights.add(heightMap[row][col - 1])

    return adjacentLocationHeights
}

static def part1(List<List<Integer>> heightMap) {
    int riskLevel = 0

    for (i in 0..<heightMap.size()) {
        for (j in 0..<heightMap[0].size()) {
            int point = heightMap[i][j]
            if (point < adjacentLocationHeights(heightMap, i, j).min()) {
                riskLevel += 1 + point
            }
        }
    }
    riskLevel
}

println part1(heightMap)