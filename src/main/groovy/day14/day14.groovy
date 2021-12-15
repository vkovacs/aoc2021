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

def part2(String template) {
    Map<String, Long> pairCountMap = [:]
    for (int i in 0..<template.size() - 1) {
        def (letter0, letter1) = [template[i], template[i + 1]]
        pairCountMap[letter0 + letter1] = pairCountMap.get(letter0 + letter1, 0L) + 1
    }

    40.times {
        pairCountMap = expand2(pairCountMap)
    }

    Map<String, Long> letterCountMap = [:]

    pairCountMap.each {
        def (String letter0, String letter1) = it.key.split("")
        letterCountMap[letter0] = letterCountMap.get(letter0, 0L) + it.value
        letterCountMap[letter1] = letterCountMap.get(letter1, 0L) + it.value
    }

    letterCountMap.each {
        letterCountMap[it.key] = Math.ceil(letterCountMap[it.key] / 2) as long
    }

    letterCountMap.max { it.value }.value as Long - letterCountMap.min { it.value }.value as Long
}

println part2(lines[0])

Map<String, Long> expand2(LinkedHashMap<String, Long> countMap) {
    Map<String, Long> newCountMap = [:]
    for (entry in countMap) {
        def product = insertionPairs.get(entry.key)
        def (String letter0, String letter1) = entry.key.split("")
        newCountMap[letter0 + product] = newCountMap.get(letter0 + product, 0L) + entry.value
        newCountMap[product + letter1] = newCountMap.get(product + letter1, 0L) + entry.value
    }
    newCountMap
}
