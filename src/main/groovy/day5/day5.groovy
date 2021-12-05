package day5

def inputFile = new File("../../resources/day5/input")
def inputLines = inputFile.readLines()


List<Vent> vents = []

inputLines.collect {

    String commaSeparatedCoordinates = it.replaceAll(/->/, ",")
    def (int x1, int y1, int x2, int y2) = commaSeparatedCoordinates.split(",").collect { it as int }

    vents.add(new Vent(x1: x1, y1: y1, x2: x2, y2: y2))
}

final int mapSize = vents.collect {
    [it.x1, it.x2, it.y1, it.y2].max()
}.max() + 1


//part1

static int part1(List<Vent> vents, int mapSize) {
    List<Vent> horizontalOrVerticalVents = vents.findAll {
        it.x1 == it.x2 || it.y1 == it.y2
    }

    OceanMap oceanMap = new OceanMap(mapSize)

    horizontalOrVerticalVents.each {
        oceanMap.addVent(it)
    }
    //println(oceanMap)

    oceanMap.flatMap().count { it > 1 }
}

println part1(vents, mapSize)

static int part2(List<Vent> vents, int mapSize) {
    OceanMap oceanMap = new OceanMap(mapSize)

    vents.each {
        oceanMap.addVent(it)
    }
    //println(oceanMap)

    oceanMap.flatMap().count { it > 1 }
}

println part2(vents, mapSize)
