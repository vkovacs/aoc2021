package day14

import groovy.transform.Field

@Field def inputFile = new File("../../resources/day14/input")
@Field def lines = inputFile.readLines()
@Field Map<String, String> insertionPairs = lines.subList(2, lines.size()).collectEntries{
    def split = it.split(" -> ")
    [(split[0]): split[1]]
}

StringBuilder expand(String template, StringBuilder accumulator) {
    if (template.length() < 2) return accumulator.append(template[0])

    def (letter0, letter1) = [template[0], template[1]]
    def product = insertionPairs.get(letter0 + letter1)
    if (product != null) accumulator.append(letter0).append(product)

    expand(template.substring(1), accumulator)
}

println part1(lines[0])

def part1(String template) {
    10.times {
        template = expand(template, new StringBuilder()).toString()
    }

    def letterCount = template.split("").countBy { it }
    return letterCount.max {it.value}.value - letterCount.min {it.value}.value
}

