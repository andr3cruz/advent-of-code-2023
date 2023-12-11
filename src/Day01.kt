fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        for (line in input) {
            val filteredLine: String = line.filter { it.isDigit() }
            val number: String = "" + filteredLine.first() + filteredLine.last()
            result += Integer.parseInt(number)
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        for (line in input) {
            // "twone" -> "t2one" -> "t2o1e"
            // "eightwo" -> "eight2o" -> "e8t2o"
            val transformedLine = line
                .replace("one", "o1e")
                .replace("two", "t2o")
                .replace("three", "t3e")
                .replace("four", "f4r")
                .replace("five", "f5e")
                .replace("six", "s6x")
                .replace("seven", "s7n")
                .replace("eight", "e8t")
                .replace("nine", "n9e")
            val filteredLine: String = transformedLine.filter { it.isDigit() }
            val numberString: String = "" + filteredLine.first() + filteredLine.last()
            result += Integer.parseInt(numberString)
        }
        return result
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
