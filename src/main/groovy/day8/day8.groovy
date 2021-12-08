package day8

def inputFile = new File("../../resources/day8/input")

int sum = 0
inputFile.eachLine {
    def output = it.split("\\|")[1].trim().split(" ")
    def ones = output.count() {it.length() == 2}
    def fours = output.count {it.length() == 4}
    def sevens = output.count {it.length() == 3}
    def eights = output.count {it.length() == 7}
    sum+=ones+ fours + sevens + eights
}

println(sum)