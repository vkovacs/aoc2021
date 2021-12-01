package day1

// part1

Integer depth = null
def depthMeasurementIncreased = 0
def inputFile = new File("../../resources/day1/input")

def lines = inputFile.readLines()
        .collect { it as int }

lines.forEach {
    if (depth != null && it > depth) {
        depthMeasurementIncreased++
    }
    depth = it
}

println depthMeasurementIncreased

// part2

def sumOfMeasurementsInSlidingWindowIncreases = 0

def firstWindowStartIndex = 0

while (firstWindowStartIndex < lines.size() - 3) {
    def secondWindowStartIndex = firstWindowStartIndex + 1

    def firstWindowSum = lines[firstWindowStartIndex] + lines[firstWindowStartIndex + 1] + lines[firstWindowStartIndex + 2]
    def secondWindowSum = lines[secondWindowStartIndex] + lines[secondWindowStartIndex + 1] + lines[secondWindowStartIndex + 2]

    if (firstWindowSum < secondWindowSum) {
        sumOfMeasurementsInSlidingWindowIncreases++
    }

    firstWindowStartIndex++
}

println sumOfMeasurementsInSlidingWindowIncreases