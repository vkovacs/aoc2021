package day4

def inputFile = new File("../../resources/day4/input")
def inputLines = inputFile.readLines()

static List<Integer> markedNumbers(String line) {
    return line.split(",")
            .collect { it as int }
}

static List<Board> boards(List<String> inputLines) {
    List<Board> boards = []

    Board board = new Board()
    for (def row : inputLines) {
        if (!row.isEmpty()) {
            board.add(row.strip().split(/[ ]+/)
                    .collect {
                        new MarkableNumber(number: it as int, isMarked: false)
                    })
        } else {
            boards.add(board)
            board = new Board()
        }
    }

    if (!board.isEmpty()) {
        boards.add(board)
    }

    return boards
}

def drawnNumbers = markedNumbers(inputLines.get(0))
def boards = boards(inputLines.subList(2, inputLines.size()))

Board finishedBoard

numberRegisteringLoop:
for (def markedNumber : drawnNumbers) {
    for (def board : boards) {
        if (board.register(markedNumber)) {
            finishedBoard = board
            break numberRegisteringLoop
        }
    }
}

assert finishedBoard != null

println(finishedBoard.score())