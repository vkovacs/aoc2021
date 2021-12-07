package day7

def inputFile = new File("../../resources/day7/input")
def crabPositions = inputFile.readLines().get(0).split(",").collect(it -> it as int)

static int solution(List<Integer> crabPositions, Closure cost) {
    int min = crabPositions.min()
    int max = crabPositions.max()

    Map<Integer, Integer> fuelCost = [:]

    (min..max).each { base ->
        int sum = 0
        (crabPositions).each {
            sum += cost(base, it)
        }
        fuelCost[base] = sum
    }

    fuelCost.values().min()
}

def part1Cost = (int from, int to) -> Math.abs(from - to)
def part2Cost = (int from, int to) -> {
    int n = Math.abs(from - to)
    return ((1 + n) * n) / 2
}

println solution(crabPositions, part1Cost)
println solution(crabPositions, part2Cost)

