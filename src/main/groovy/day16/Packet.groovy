package day16

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
class Packet {
    Header header
    int length
    long value
    List<Packet> subPackets = []

    int sum() {
        header.version + sumSubPackets(this)
    }

    private int sumSubPackets(Packet packet) {
        long l = 0
        for (p in packet.subPackets) {
            l += p.header.version + sumSubPackets(p)
        }
        l
    }

    long eval() {
        evalSubPackets(this)
    }

    long evalSubPackets(Packet packet) {
        switch (packet.header.typeID) {
            case 4: return packet.value
            case 0: return subPackets.sum { evalSubPackets(it) } as long
            case 1: return subPackets.collect {
                evalSubPackets(it)
            }.inject(1, { sum, value -> sum * value }) as long
            case 2: return subPackets.collect {
                evalSubPackets(it)
            }.min()
            case 3: return subPackets.collect {
                evalSubPackets(it)
            }.max()
            case 5: return (evalSubPackets(subPackets[0]) > evalSubPackets(subPackets[1]) ? 1 : 0) as long
            case 6: return (evalSubPackets(subPackets[0]) < evalSubPackets(subPackets[1]) ? 1 : 0) as long

        }
    }

    @ToString
    @EqualsAndHashCode
    static class Header {
        int version
        int typeID

        static Header of(String bin) {
            int version = Integer.parseInt(bin.substring(0, 3), 2)
            int typeID = Integer.parseInt(bin.substring(3, 6), 2)

            new Header(version: version, typeID: typeID)
        }

        boolean isLiteral() {
            typeID == 4
        }
    }
}

