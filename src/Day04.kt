fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        input.map { line ->
            var lineResult = 0
            val numberSet = mutableSetOf<Int>()

            line.split(":")[1].split("|")[0].trim().split("\\s+".toRegex())
            .map {
                numberSet.add(it.toInt())
            }

            line.split(":")[1].split("|")[1].trim().split("\\s+".toRegex())
            .map {
                if (!numberSet.add(it.toInt())) {
                    if (lineResult == 0) lineResult = 1
                    else lineResult *= 2
                }
            }

            result += lineResult
        }
        return result
    }

    fun part2(input: List<String>): Int {
        val lines = mutableListOf<String>()
        input.map {
            lines.add(it)
        }

        var i = 0
        while (i <= lines.size - 1) {
            var winningNumbers = 0
            val numberSet = mutableSetOf<Int>()

            val lineIndex = lines[i].split(":")[0].trim().split("\\s+".toRegex())[1].toInt()

            lines[i].split(":")[1].split("|")[0].trim().split("\\s+".toRegex())
                .map {
                    numberSet.add(it.toInt())
                }

            lines[i].split(":")[1].split("|")[1].trim().split("\\s+".toRegex())
                .map {
                    if (!numberSet.add(it.toInt())) winningNumbers++
                }

            for (j in lineIndex..<lineIndex+winningNumbers) {
                lines.add(lines[j])
            }
            i++
        }
        return lines.size
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
