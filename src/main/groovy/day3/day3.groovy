package day3

def inputFile = new File("../../resources/day3/input-test")

List<List<Integer>> matrix = []
inputFile.splitEachLine("", {
    matrix.add(it.collect { it as int })
})

def column(List<List<Integer>> matrix, def number) {
    matrix.collect({
        it.get(number)
    })
}

def part1(def matrix) {
    var gammaRateString = ""
    var epsilonRateString = ""

    (0..<matrix[0].size()).each {
        def column = column(matrix, it)

        def zeros = column.count(0)
        def ones = matrix.size() - zeros

        if (zeros > ones) {
            gammaRateString += 1
            epsilonRateString += 0
        } else {
            gammaRateString += 0
            epsilonRateString += 1
        }
    }

    def gammaRate = Integer.parseInt(gammaRateString, 2)
    def epsilonRate = Integer.parseInt(epsilonRateString, 2)

    gammaRate * epsilonRate
}

println(part1(matrix))


// part2


