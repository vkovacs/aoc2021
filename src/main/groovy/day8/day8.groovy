package day8

def inputFile = new File("../../resources/day8/input")

static def part1(File inputFile) {
    int sum = 0
    inputFile.eachLine {
        def output = it.split("\\|")[1].trim().split(" ")
        def ones = output.count() { it.length() == 2 }
        def fours = output.count { it.length() == 4 }
        def sevens = output.count { it.length() == 3 }
        def eights = output.count { it.length() == 7 }
        sum += ones + fours + sevens + eights
    }
}

println(part1(inputFile))

static def part2(File inputFile) {
    int sum = 0
    inputFile.eachLine {
        def split = it.split("\\|")
        def signals = split[0].trim().split(" ")
        Map<Integer, String> numberSignalDictionary = [0: "", 1: "", 2: "", 3: "", 4: "", 5: "", 6: "", 7: "", 8: "", 9: ""]

        numberSignalDictionary[1] = signals.find { it.length() == 2 }
        numberSignalDictionary[4] = signals.find { it.length() == 4 }
        numberSignalDictionary[7] = signals.find { it.length() == 3 }
        numberSignalDictionary[8] = signals.find { it.length() == 7 }
        numberSignalDictionary[9] = signals.find { it.length() == 6 && containsAllLetters(it, numberSignalDictionary[4]) }
        numberSignalDictionary[0] = signals.find { it.length() == 6 && it != numberSignalDictionary[9] && containsAllLetters(it, numberSignalDictionary[1]) }
        numberSignalDictionary[6] = signals.find { it.length() == 6 && it != numberSignalDictionary[9] && it != numberSignalDictionary[0] }
        numberSignalDictionary[3] = signals.find { it.length() == 5 && containsAllLetters(it, numberSignalDictionary[7]) }
        numberSignalDictionary[5] = signals.find { it.length() == 5 && containsAllLetters(numberSignalDictionary[9], it) && it != numberSignalDictionary[3] }
        numberSignalDictionary[2] = signals.find { it.length() == 5 && it != numberSignalDictionary[3] && it != numberSignalDictionary[5] }

        Map<String, Integer> normalizedSignalNumberDictionary = numberSignalDictionary.collectEntries { key, value -> [normalizeSignal(value), key] }

        def output = split[1].trim().split(" ").collect { normalizeSignal(it) }
        def row = output.collect() {
            normalizedSignalNumberDictionary[it]
        }.join() as int
        sum += row

    }
}

println(part2(inputFile))

static boolean containsAllLetters(String isContains, String what) {
    isContains.toSet().containsAll(what.toSet())
}

static String normalizeSignal(String signal) {
    signal.split("").sort().join()
}