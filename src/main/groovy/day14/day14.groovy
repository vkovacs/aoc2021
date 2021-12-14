package day14

import groovy.transform.Field

@Field def lines = new File("../../resources/day14/input").readLines()
@Field Map<String, String> insertionPairs = lines.subList(2, lines.size()).collectEntries {
    def split = it.split(" -> ")
    [(split[0]): split[1]]
}

String expand(String template) {
    StringBuilder accumulator = new StringBuilder()
    for (int i in 0..<template.size() - 1) {
        def (letter0, letter1) = [template[i], template[i + 1]]
        def product = insertionPairs.get(letter0 + letter1)
        if (product != null) accumulator.append(letter0).append(product)
    }
    accumulator.append(template[template.size() - 1]) toString()
}

println part1(lines[0])

def part1(String template) {
    10.times {
        template = expand(template).toString()
    }

    def letterCount = template.split("").countBy { it }
    letterCount.max { it.value }.value - letterCount.min { it.value }.value
}

