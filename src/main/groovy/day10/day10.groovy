package day10

def inputFile = new File("../../resources/day10/input")

openers = ["(", "[", "{", "<"]
closers = [")", "]", "}", ">"]

def part1(inputFile) {
    def scoreMap = [")": 3, "]": 57, "}": 1197, ">": 25137]

    int score = 0
    inputFile.eachLine {
        findSyntaxError(it).ifPresent(e -> score += scoreMap[e])
    }
    score
}

println part1(inputFile)

Optional<String> findSyntaxError(String line) {
    def tokens = line.split("")
    def stack = []

    for (String token : tokens) {
        if (openers.contains(token)) {
            stack.push(token)
        } else if (closers.contains(token)) {
            def previousOpener = stack.pop()
            if (!isPair(previousOpener, token)) {
                return Optional.of(token)
            }
        }
    }

    Optional.empty()
}

boolean isPair(String opener, String closer) {
    opener == "(" && closer == ")" ||
            opener == "[" && closer == "]" ||
            opener == "{" && closer == "}" ||
            opener == "<" && closer == ">"
}

def part2(inputFile) {
    List<String> incompleteLines = inputFile.findAll {
        findSyntaxError(it).isEmpty()
    }

    def scores = incompleteLines.collect {
        score(findMissingClosers(it))
    }.sort()

    return scores.get(scores.size() / 2 as int)
}

println part2(inputFile)

long score(List<String> missingClosers) {
    def scoreMap = [")": 1, "]": 2, "}": 3, ">": 4]

    long score = 0
    missingClosers.each {
        score = score * 5 + scoreMap[it]
    }

    score
}

String myPair(String opener) {
    switch (opener) {
        case "(": return ")"
        case "[": return "]"
        case "{": return "}"
        case "<": return ">"
        default: throw new IllegalArgumentException()
    }
}

List<String> findMissingClosers(String line) {
    def tokens = line.split("")
    def stack = []

    for (String token : tokens) {
        if (openers.contains(token)) {
            stack.push(token)
        } else if (closers.contains(token)) {
            stack.pop()
        }
    }

    stack.collect {
        myPair(it)
    }
}