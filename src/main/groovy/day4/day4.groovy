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

def markedNumbers = markedNumbers(inputLines.get(0))
def boards = boards(inputLines.subList(2, inputLines.size()))

//par1
static int part1(List<Board> boards, List<Integer> markedNumbers) {
    Board finishedBoard

    numberRegisteringLoop:
    for (def markedNumber : markedNumbers) {
        for (def board : boards) {
            if (board.register(markedNumber)) {
                finishedBoard = board
                break numberRegisteringLoop
            }
        }
    }

    assert finishedBoard != null

    return finishedBoard.score()
}

println part1(boards, markedNumbers)

//part2
static List<Board> winningOrders(List<Board> boards, List<Integer> drawnNumbers) {
    List<Board> boardsWithWinningNumbers = boards.collect()
    int winningOrder = 0
    for (def markedNumber : drawnNumbers) {
        for (def board : boardsWithWinningNumbers) {
            if (!board.isFinished()) {
                if (board.register(markedNumber)) {
                    board.winningOrder = winningOrder
                    winningOrder++
                }
            }
        }
    }
    return boardsWithWinningNumbers
}

static def part2(List<Board> boards, List<Integer> markedNumbers) {
    winningOrders(boards, markedNumbers).max() { it.winningOrder }.score()
}

println part2(boards, markedNumbers)