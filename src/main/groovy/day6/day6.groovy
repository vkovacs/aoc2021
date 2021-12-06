package day6

def inputFile = new File("../../resources/day6/input")
def inputLines = inputFile.readLines()

final List<Integer> fishes = inputLines.get(0).split(",").collect { it as int }
final List<Integer> fishesCopy = fishes.collect()

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

println part1(fishesCopy, 80)

static long part2(List<Integer> fishes, int days) {
    Map<Integer, Long> fishAgeDistribution = [0: 0, 1: 0, 2: 0, 3: 0, 4: 0, 5: 0, 6: 0, 7: 0, 8: 0]
    fishes.each {
        fishAgeDistribution[it] = fishAgeDistribution[it] + 1
    }

    (1..days).each {
        long newParentsCount = 0
        (0..8).each {
            if (it > 0) {
                fishAgeDistribution[it - 1] = fishAgeDistribution[it]
            } else {
                newParentsCount = fishAgeDistribution[0]
            }
        }
        fishAgeDistribution[6] = fishAgeDistribution[6] + newParentsCount //new parents are put to group 6
        fishAgeDistribution[8] = newParentsCount //new spawns are put to group 8 (each parent has only 1 spawn)
    }

    println(fishAgeDistribution)

    return fishAgeDistribution.values().sum() as long
}

println part2(fishes, 256)