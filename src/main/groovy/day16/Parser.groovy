package day16


class Parser {
    Packet parsePacket(String message) {
        parsePackets(message, Integer.MAX_VALUE)[0]
    }

    private List<Packet> parsePackets(String message, int remainingChildrenCount) {
        List<Packet> siblings = []

        while (!message.isEmpty() && message.count("0") != message.length() && remainingChildrenCount > 0) {
            def header = Packet.Header.of(message)
            if (header.isLiteral()) {
                def literal = literal(message)
                def literalMessage = literal[0]
                def literalHeader = Packet.Header.of(literalMessage)
                def value = literal[1]
                def packet = new Packet(header: literalHeader, length: literalMessage.length(), value: value)
                siblings << packet
                remainingChildrenCount--
                message = message.substring(packet.length)
            } else {
                if (isLengthTypeId0(message)) {
                    int subPacketsLength = Integer.parseInt(message.substring(7, 22), 2)
                    def subPacketsBinMerged = message.substring(22, 22 + subPacketsLength)
                    def children = parsePackets(subPacketsBinMerged, remainingChildrenCount)
                    int length = children.flatten().sum { it.length } + 22
                    def packet = new Packet(header: header, length: length, subPackets: children)
                    remainingChildrenCount--
                    siblings << packet
                    message = message.substring(length)
                } else {
                    def childrenCount = Integer.parseInt(message.substring(7, 18),2)
                    def children = parsePackets(message.substring(18), childrenCount)
                    remainingChildrenCount--
                    int length = children.flatten().sum { it.length } + 18
                    def packet = new Packet(header: header, length: length, subPackets: children)
                    siblings << packet
                    message = message.substring(length)
                }
            }
        }

        return siblings
    }

    boolean isLengthTypeId0(String message) {
        message[6] as int == 0
    }

    String hexToBin(String hex) {
        def dict = ["0": "0000",
                    "1": "0001",
                    "2": "0010",
                    "3": "0011",
                    "4": "0100",
                    "5": "0101",
                    "6": "0110",
                    "7": "0111",
                    "8": "1000",
                    "9": "1001",
                    "A": "1010",
                    "B": "1011",
                    "C": "1100",
                    "D": "1101",
                    "E": "1110",
                    "F": "1111"]

        hex.split("").collect { dict[it] }.join()
    }

    Tuple literal(String message) {
        def start = 6
        int prefix
        def messageAccumulator = message.substring(0, 6)
        def valueAccumulator = ""

        do {
            def part = message.substring(start, start + 5)
            prefix = part[0] as int
            messageAccumulator += part
            valueAccumulator += part.substring(1)
            start += 5
        } while (prefix != 0)

        new Tuple(messageAccumulator, Long.parseLong(valueAccumulator, 2))
    }
}