package day10

def inputFile = new File("../../resources/day10/input")

scoreMap = [")": 3, "]": 57, "}": 1197, ">": 25137]
openers = ["(", "[", "{", "<"]
closers = [")", "]", "}", ">"]

def part1(inputFile) {
    int score = 0
    inputFile.eachLine {
        findSyntaxError(it).ifPresent(e -> score += scoreMap[e])
    }
    score
}
println part1(inputFile)

Optional<String> findSyntaxError(String line) {
    def tokens = line.split("")
    Stack<String> stack = new Stack()

    for (String token : tokens)
        if (openers.contains(token)) {
            stack.push(token)
        } else if (closers.contains(token)) {
            def previousOpener = stack.pop()
            if (!isPair(previousOpener, token)) {
                return Optional.of(token)
            }
        } else {
            throw new IllegalArgumentException()
        }


    Optional.empty()
}

boolean isPair(String opener, String closer) {
    opener == "(" && closer == ")" ||
            opener == "[" && closer == "]" ||
            opener == "{" && closer == "}" ||
            opener == "<" && closer == ">"
}