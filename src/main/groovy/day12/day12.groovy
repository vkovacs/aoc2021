package day12

import groovyjarjarantlr4.v4.misc.Graph

def inputFile = new File("../../resources/day12/input")

Graph<String> graph = new Graph()

Set<String> smallCaves = []

inputFile.eachLine {
    def split = it.split("-")
    graph.addEdge(split[0], split[1])
    graph.addEdge(split[1], split[0])

    if (isSmallCave(split[0])) smallCaves.add(split[0])
    if (isSmallCave(split[1])) smallCaves.add(split[1])
}

int findRouteCount(Graph.Node<String> node, Set<Graph.Node<String>> visited, List<String> path) {
    if (visited.contains(node)) return 0

    def newPath = path.collect()
    newPath.add(node.payload)

    if (node.payload == "end") {
        //println(newPath)
        return 1
    }

    def newVisited = visited.collect() as Set
    if (isSmallCave(node.payload)) {
        newVisited.add(node)
    }

    int sum = 0
    for (target in node.edges) {
        sum += findRouteCount(target, newVisited, newPath)
    }
    return sum
}

def part1(Graph graph) {
    def start = graph.getNode("start")
    findRouteCount(start, [] as Set, [])
}

println part1(graph)

int part2(Graph graph, Set<String> smallCaves) {
    def start = graph.getNode("start")

    smallCaves.collect { findRouteCountOneSmallCaveCanBeVisitedTwice(start, [] as Set, [], it, 1) }
            .inject ([] as Set, {sum, v -> sum + v}).size()
}

println part2(graph, smallCaves)

Set<List<String>> findRouteCountOneSmallCaveCanBeVisitedTwice(Graph.Node<String> node, Set<Graph.Node<String>> visited, List<String> path, String smallCaveThatCanBeVisitedTwice, int smallCaveVisitOpportunity) {
    if (visited.contains(node)) return []

    def newPath = path.collect()
    newPath.add(node.payload)

    if (node.payload == "end") {
        return [path] as Set
    }

    def newVisited = visited.collect() as Set

    if (isSmallCave(node.payload)) {
        if (node.payload != "start") {
            if (node.payload == smallCaveThatCanBeVisitedTwice && smallCaveVisitOpportunity == 1) {
                smallCaveVisitOpportunity = smallCaveVisitOpportunity - 1
            } else {
                newVisited.add(node)
            }
        } else {
            newVisited.add(node)
        }
    }

    Set<List<String>> paths = []
    for (target in node.edges) {
        paths += findRouteCountOneSmallCaveCanBeVisitedTwice(target, newVisited, newPath, smallCaveThatCanBeVisitedTwice, smallCaveVisitOpportunity)

    }
    return paths
}

boolean isSmallCave(String string) {
    return string == string.toLowerCase()
}

