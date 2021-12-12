package day12

import groovyjarjarantlr4.v4.misc.Graph

def inputFile = new File("../../resources/day12/input-test")

Graph<String> graph = new Graph()

inputFile.eachLine {
    def split = it.split("-")
    graph.addEdge(split[0], split[1])
    graph.addEdge(split[1], split[0])
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

boolean isSmallCave(String string) {
    return string == string.toLowerCase()
}

