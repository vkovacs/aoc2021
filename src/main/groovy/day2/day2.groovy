package day2

def inputFile = new File("../../resources/day2/input")
def lines = inputFile.readLines()

//part 1

static def movement(String movementString) {
    def split = movementString.split()

    new Tuple(split[0], split[1] as int)
}

static def positionChange(Tuple movement) {
    switch (movement.get(0)) {
        case "forward": return new Tuple(movement.get(1), 0)
        case "down": return new Tuple(0, movement.get(1))
        case "up": return new Tuple(0, -movement.get(1))
    }
}

def horizontalPosition = 0
def depth = 0

for (def line : lines) {
    def movement = movement(line)
    def change = positionChange(movement)
    horizontalPosition += change.get(0)
    depth += change.get(1)
}

println horizontalPosition * depth