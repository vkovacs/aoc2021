package day4

class Board {
    static final int BOARD_SIZE = 5
    private List<List<MarkableNumber>> matrix = []
    private def winningNumber = -1
    def winningOrder = -1

    void add(List<MarkableNumber> row) {
        matrix.add(row)

    }

    boolean isEmpty() {
        return matrix.isEmpty()
    }

    boolean register(int markedNumber) {
        registeringLoop:
        for (def row in matrix) {
            for (def number in row) {
                if (number.getNumber() == markedNumber) {
                    number.isMarked = true
                    break registeringLoop
                }
            }
        }

        if (isFinished()) {
            winningNumber = markedNumber
            return true
        }

        return false
    }

    boolean isFinished() {
        isFinishedRow() || isFinishedColumn()
    }

    private isFinishedRow() {
        for (def row : matrix) {
            if (row.count { it.isMarked } == BOARD_SIZE) {
                return true
            }
        }
        return false
    }

    private boolean isFinishedColumn() {
        for (i in 0..<BOARD_SIZE) {
            if (column(i).count { it.isMarked } == BOARD_SIZE) {
                return true
            }
        }
        return false
    }

    private List<MarkableNumber> column(def number) {
        matrix.collect({
            it.get(number)
        })
    }

    int score() {
        List<MarkableNumber> flattenedMatrix = matrix.flatten() as List<MarkableNumber>
        int unmarkedNumbersSum = (int) flattenedMatrix.findAll { !it.isMarked }.sum { it.number }

        return unmarkedNumbersSum * winningNumber
    }
}
