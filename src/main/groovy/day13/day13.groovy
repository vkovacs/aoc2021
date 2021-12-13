package day13

import day9.Position

def inputFile = new File("../../resources/day13/input")

Set<Position> positions = []
List<Tuple> folds = []
inputFile.eachLine {
    if (it.contains(",")) {
        def split = it.split(",")
        positions.add(new Position(col: split[0] as int, row: split[1] as int))
    } else if (it.contains("=")) {
        def match = it =~ /fold along (x|y)=([0-9]+)/
        folds.add(new Tuple(match[0][1] == "x" ? "col" : "row", match[0][2] as int))
    }
}

Set<Position> foldRow(Set<Position> positions, Tuple fold) {
    return positions.collect {
        if (it.row > fold[1]) {
            def d = it.row - fold[1]
            return new Position(col: it.col, row: fold[1] - d)
        } else {
            return it
        }
    }
}

Set<Position> foldCol(Set<Position> positions, Tuple fold) {
    positions.collect {
        if (it.col > fold[1]) {
            def d = it.col - fold[1]
            return new Position(col: fold[1] - d, row: it.row)
        } else {
            return it
        }
    }
}

Set<Position> fold(Set<Position> positions, Tuple fold) {
    if (fold[0] == "row") {
        return foldRow(positions, fold)
    } else {
        return foldCol(positions, fold)
    }
}

println part1(positions, folds[0])

int part1(Set<Position> positions, Tuple fold) {
    this.fold(positions, fold).size()
}

def part2(Set<Position> positions, List<Tuple> folds) {
    folds.forEach {
        positions = this.fold(positions, it)
    }

    int maxCol = positions.max { it.col }.col
    int maxRow = positions.max { it.row }.row

    StringBuilder sb = new StringBuilder()
    for (i in 0..maxRow) {
        for (j in 0..maxCol) {
            if (positions.contains(new Position(col: j, row: i))) sb.append("#") else sb.append(".")
        }
         sb.append("\n")
    }

    return sb.toString()
}

println part2(positions, folds)