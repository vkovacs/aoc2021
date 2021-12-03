package day2

def inputFile = new File("../../resources/day2/input")
lines = inputFile.readLines()

//part 1

static def movement(String movementString) {
    def split = movementString.split()

    new Tuple(split[0], split[1] as int)
}

static def positionChange(Tuple movement) {
    def command = movement.get(0)
    def value = movement.get(1) as int

    switch (command) {
        case "forward": return new Tuple(value, 0)
        case "down": return new Tuple(0, value)
        case "up": return new Tuple(0, -value)
    }
}

def part1() {
    def horizontalPosition = 0
    def depth = 0

    for (def line : lines) {
        def movement = movement(line)
        def change = positionChange(movement)
        horizontalPosition += change.get(0)
        depth += change.get(1)
    }

    println horizontalPosition * depth
}

part1()

//part 2

static def positionChangeWithAim(Tuple movement, def aim) {
    def command = movement.get(0)
    def value = movement.get(1) as int

    switch (command) {
        case "forward": return new Tuple(value, aim * value, 0)
        case "down": return new Tuple(0, 0, value)
        case "up": return new Tuple(0, 0, -value)
    }
}

def part2() {
    def horizontalPosition = 0
    def depth = 0
    def aim = 0

    for (def line : lines) {
        def movement = movement(line)
        def change = positionChangeWithAim(movement, aim)
        horizontalPosition += change.get(0)
        depth += change.get(1)
        aim += change.get(2)
    }

    println horizontalPosition * depth
}

part2()