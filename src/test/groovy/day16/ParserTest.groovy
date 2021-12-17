package day16

import org.junit.jupiter.api.Test

class ParserTest {
    def parser = new Parser()

    @Test
    void parseHex() {
        assert "01100010000000001000000000000000000101100001000101010110001011001000100000000010000100011000111000110100" == parser.hexToBin("620080001611562C8802118E34")
    }

    @Test
    void parseOnlyLiteralMessage() {
        assert new Packet(header: new Packet.Header(version: 6, typeID: 4), length: 21, value: 2021, subPackets: []) == parser.parseMessage("110100101111111000101000")
    }

    @Test
    void parseOperatorLenType0() {

        def subPacket0 = new Packet(header: new Packet.Header(version: 6, typeID: 4), length: 11, value: 10)
        def subPacket1 = new Packet(header: new Packet.Header(version: 2, typeID: 4), length: 16, value: 20)
        def packet = new Packet(header: new Packet.Header(version: 1, typeID: 6), length: 49, subPackets: [subPacket0, subPacket1])

        assert packet == parser.parseMessage("00111000000000000110111101000101001010010001001000000000")

    }

    @Test
    void parseOperatorLenType1() {
        def subPacket0 = new Packet(header: new Packet.Header(version: 2, typeID: 4), length: 11, value: 1)
        def subPacket1 = new Packet(header: new Packet.Header(version: 4, typeID: 4), length: 11, value: 2)
        def subPacket2 = new Packet(header: new Packet.Header(version: 1, typeID: 4), length: 11, value: 3)
        def packet = new Packet(header: new Packet.Header(version: 7, typeID: 3), length: 51, subPackets: [subPacket0, subPacket1, subPacket2])

        assert packet == parser.parseMessage("11101110000000001101010000001100100000100011000001100000")
    }

    @Test
    void nestedOperators() {
         assert 16 == parser.parseMessage(parser.hexToBin("8A004A801A8002F478")).sum()
    }

    @Test
    void multipleSubPackets() {
        assert 12 == parser.parseMessage(parser.hexToBin("620080001611562C8802118E34")).sum()
    }

    @Test
    void multipleSubPacketsDifferentLengthTypeId() {
        assert 23 == parser.parseMessage(parser.hexToBin("C0015000016115A2E0802F182340")).sum()
    }

    @Test
    void nestedOperatorsMultipleLiteral() {
        assert 31 == parser.parseMessage(parser.hexToBin("A0016C880162017C3686B18A3D4780")).sum()
    }

    @Test
    void solution() {
        assert 957 == parser.parseMessage(parser.hexToBin("2052ED9802D3B9F465E9AE6003E52B8DEE3AF97CA38100957401A88803D05A25C1E00043E1545883B397259385B47E40257CCEDC7401700043E3F42A8AE0008741E8831EC8020099459D40994E996C8F4801CDC3395039CB60E24B583193DD75D299E95ADB3D3004E5FB941A004AE4E69128D240130D80252E6B27991EC8AD90020F22DF2A8F32EA200AC748CAA0064F6EEEA000B948DFBED7FA4660084BCCEAC01000042E37C3E8BA0008446D8751E0C014A0036E69E226C9FFDE2020016A3B454200CBAC01399BEE299337DC52A7E2C2600BF802B274C8848FA02F331D563B3D300566107C0109B4198B5E888200E90021115E31C5120043A31C3E85E400874428D30AA0E3804D32D32EED236459DC6AC86600E4F3B4AAA4C2A10050336373ED536553855301A600B6802B2B994516469EE45467968C016D004E6E9EE7CE656B6D34491D8018E6805E3B01620C053080136CA0060801C6004A801880360300C226007B8018E0073801A801938004E2400E01801E800434FA790097F39E5FB004A5B3CF47F7ED5965B3CF47F7ED59D401694DEB57F7382D3F6A908005ED253B3449CE9E0399649EB19A005E5398E9142396BD1CA56DFB25C8C65A0930056613FC0141006626C5586E200DC26837080C0169D5DC00D5C40188730D616000215192094311007A5E87B26B12FCD5E5087A896402978002111960DC1E0004363942F8880008741A8E10EE4E778FA2F723A2F60089E4F1FE2E4C5B29B0318005982E600AD802F26672368CB1EC044C2E380552229399D93C9D6A813B98D04272D94440093E2CCCFF158B2CCFE8E24017CE002AD2940294A00CD5638726004066362F1B0C0109311F00424CFE4CF4C016C004AE70CA632A33D2513004F003339A86739F5BAD5350CE73EB75A24DD22280055F34A30EA59FE15CC62F9500")).sum()
    }

    @Test
    void evalSum() {
        assert 3L == parser.parseMessage(parser.hexToBin("C200B40A82")).eval()
    }

    @Test
    void evalProduct() {
        assert 54L == parser.parseMessage(parser.hexToBin("04005AC33890")).eval()
    }

    @Test
    void evalMin() {
        assert 7L == parser.parseMessage(parser.hexToBin("880086C3E88112")).eval()
    }

    @Test
    void evalMax() {
        assert 9L == parser.parseMessage(parser.hexToBin("CE00C43D881120")).eval()
    }

    @Test
    void evalGreaterThan() {
        assert 1L == parser.parseMessage(parser.hexToBin("D8005AC2A8F0")).eval()
    }

    @Test
    void evalLesserThan() {
        assert 0L == parser.parseMessage(parser.hexToBin("F600BC2D8F")).eval()
    }

    //@Test
    void eval() {
        assert 1L == parser.parseMessage(parser.hexToBin("9C0141080250320F1802104A08")).eval()
    }
}

