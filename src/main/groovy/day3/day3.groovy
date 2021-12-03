package day3

def inputFile = new File("../../resources/day3/input")

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

def eliminate(List<List<Integer>> matrix, int columnNumber, String selector) {
    if (matrix.size() == 1) {
        return matrix.get(0).join()
    } else {
        def column = column(matrix, columnNumber)

        def zeros = column.count(0)
        def ones = matrix.size() - zeros

        if (selector == "most") {
            if (ones >= zeros) {
                eliminate(matrix.findAll({
                    it.get(columnNumber) == 1
                }), columnNumber + 1, "most")
            } else {
                eliminate(matrix.findAll({
                    it.get(columnNumber) == 0
                }), columnNumber + 1, "most")
            }
        } else {
            if (ones >= zeros) {
                eliminate(matrix.findAll({
                    it.get(columnNumber) == 0
                }), columnNumber + 1, "least")
            } else {
                eliminate(matrix.findAll({
                    it.get(columnNumber) == 1
                }), columnNumber + 1, "least")
            }
        }

    }
}

def part2(def matrix) {
    String oxygenGeneratorRatingString = eliminate(matrix, 0, "most")
    String co2ScrubberRatingString = eliminate(matrix, 0, "least")

    def oxygenGeneratorRating = Integer.parseInt(oxygenGeneratorRatingString, 2)
    def co2ScrubberRating = Integer.parseInt(co2ScrubberRatingString, 2)

    oxygenGeneratorRating * co2ScrubberRating
}

println(part2(matrix))

