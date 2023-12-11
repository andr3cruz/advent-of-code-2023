fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        for (line in input) {
            var maxRed = 0
            var maxGreen = 0
            var maxBlue = 0
            val gameId = Integer.parseInt(Regex("[0-9]+").find(line)?.groupValues?.get(0))
            val extractions = line.split(":")[1].split(";")
            for (extraction in extractions) {
                var red = 0
                var green = 0
                var blue = 0
                val values = extraction.split(",")
                for (value in values) {
                    val colorValue = Integer.parseInt(value.trim().split(" ")[0])
                    val color = value.trim().split(" ")[1]
                    if (color == "red") red += colorValue
                    if (color == "green") green += colorValue
                    if (color == "blue") blue += colorValue
                }
                if (red > maxRed) maxRed = red
                if (green > maxGreen) maxGreen = green
                if (blue > maxBlue) maxBlue = blue
            }
            if (isPossible(maxRed, maxGreen, maxBlue)) {
                result += gameId
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        for (line in input) {
            var minRed = 0
            var minGreen = 0
            var minBlue = 0
            val extractions = line.split(":")[1].split(";")
            for (extraction in extractions) {
                val values = extraction.split(",")
                for (value in values) {
                    val colorValue = Integer.parseInt(value.trim().split(" ")[0])
                    val color = value.trim().split(" ")[1]
                    if (color == "red") if (colorValue > minRed) minRed = colorValue
                    if (color == "green") if (colorValue > minGreen) minGreen = colorValue
                    if (color == "blue") if (colorValue > minBlue) minBlue = colorValue
                }
            }
            result += (minRed * minGreen * minBlue)
        }
        return result
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

private fun isPossible(red: Int, green: Int, blue: Int): Boolean {
    val MAX_RED = 12
    val MAX_GREEN = 13
    val MAX_BLUE = 14
    return (red <= MAX_RED && green <= MAX_GREEN && blue <= MAX_BLUE)
}

