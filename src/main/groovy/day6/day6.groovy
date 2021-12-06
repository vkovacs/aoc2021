package day6

def inputFile = new File("../../resources/day6/input")
def inputLines = inputFile.readLines()

final List<Integer> fishes = inputLines.get(0).split(",").collect {it as int}

static def part1(List<Integer> fishes, int days) {
    (1..days).each {
        (0..<fishes.size()).each {
            def fish = fishes.get(it)
            if (fish > 0) {
                fishes.set(it, --fish)
            } else {
                fishes.set(it, 6)
                fishes.add(8)
            }
        }
    }
    return fishes.size()
}

println part1(fishes, 80)