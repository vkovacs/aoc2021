package day4

def inputFile = new File("../../resources/day4/input-test")
def inputLines = inputFile.readLines()

static List<Integer> drawnNumbers(String line) {
    return line.split(",")
            .collect { it as int }
}

static List<Matrix> boards(List<String> inputLines) {
    List<Matrix> boards = []

    Matrix board = new Matrix()
    for (def row : inputLines) {
        if (!row.isEmpty()) {
            board.add(row.strip().split(/[ ]+/)
                    .collect {
                        new ChoosableNumber(number: it as int, isChoosen: false)
                    })
        } else {
            boards.add(board)
            board = new Matrix()
        }
    }

    if (!board.isEmpty()) {
        boards.add(board)
    }

    return boards
}

def drawnNumbers = drawnNumbers(inputLines.get(0))
def boards = boards(inputLines.subList(2, inputLines.size()))
