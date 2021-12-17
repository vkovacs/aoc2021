package day16

import groovy.transform.Field

def inputFile = new File("../../resources/day16/input")

@Field
Parser parser = new Parser()

def message = inputFile.readLines().get(0)

def packet = parser.parsePacket(parser.hexToBin(message))

def part1(Packet packet) {
    packet.sum()
}

def part2(Packet packet) {
    packet.eval()
}
println part1(packet)
println part2(packet)