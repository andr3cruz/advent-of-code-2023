fun main() {
    fun part1(input: List<String>): Int {
        val time = input[0].split(":")[1].trim().split("\\s+".toRegex()).map { it.toInt() }
        val distance = input[1].split(":")[1].trim().split("\\s+".toRegex()).map { it.toInt() }

        val results = time.indices.map { index ->
            (0..time[index]).count { timePressing ->
                timePressing * (time[index] - timePressing) > distance[index]
            }
        }

        return results.reduce { acc, element -> acc * element }
    }

    fun part2(input: List<String>): Long {
        val time = input[0].split(":")[1].replace("\\s".toRegex(), "").toLong()
        val distance = input[1].split(":")[1].replace("\\s".toRegex(), "").toLong()

        return (0..time).count { timePressing ->
            timePressing * (time - timePressing) > distance }.toLong()
    }

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
