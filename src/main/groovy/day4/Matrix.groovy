package day4

class Matrix {
    private List<List<ChoosableNumber>> value = []

    def column(List<List<ChoosableNumber>> matrix, def number) {
        matrix.collect({
            it.get(number)
        })
    }

    void add(List<ChoosableNumber> row) {
        value.add(row)

    }

    boolean isEmpty() {
        return value.isEmpty()
    }
}
