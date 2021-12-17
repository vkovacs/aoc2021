package day16

import groovy.transform.Field

def inputFile = new File("../../resources/day16/input")

@Field
Parser parser = new Parser()

def message = inputFile.readLines().get(0)

def part1(String message) {
    parser.parseMessage(parser.hexToBin(message)).get(0).sum()
}

println part1(message)