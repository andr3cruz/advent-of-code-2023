fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        val lines = input.map { it.toList() }
        lines.indices.map { i ->
            var number = 0
            var hasGear = false
            lines[i].indices.map { j ->
                if (lines[i][j].isDigit()) {
                    number = number*10 + Integer.parseInt(lines[i][j].toString())
//                    much more complicated solution I coded initially :)
//                    if (!hasGear) hasGear = checkAdjacentSpaces(lines, Position(i, j))
                    if (!hasGear) hasGear = checkIfAdjacentGear(lines, Position(i, j))
                }
                if (j == lines[i].size - 1 || !lines[i][j].isDigit()) {
                    if (hasGear) result += number
                    number = 0
                    hasGear = false
                }
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        val lines = input.map { it.toList() }
        lines.indices.map { i ->
            lines[i].indices.map { j ->
                if (!lines[i][j].isDigit() && lines[i][j] != '.') {
                    result += checkIfAdjacentNumbers(lines, Position(i, j))
                }
            }
        }
        return result
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

private fun checkIfAdjacentNumbers(lines: List<List<Char>>, position: Position): Int {
    var number1 = 0
    var number2 = 0
    for (i in position.i - 1..position.i + 1) {
        if (i >= 0 && i <= lines.size - 1) {
            for (j in position.j - 1..position.j + 1) {
                if (j >= 0 && j <= lines[i].size - 1) {
                    if (lines[i][j].isDigit()) {
                        if (number1 == 0) {
                            number1 = retrieveNumber(lines, Position(i,j))
                        } else if (number2 == 0){
                            number2 = retrieveNumber(lines, Position(i, j))
                        }
                    }
                }
            }
        }
    }
    return number1 * number2
}

private fun retrieveNumber(lines: List<List<Char>>, position: Position): Int {
    var currentPosition = Position(position.i, position.j)
    var number = ""
    while (currentPosition.j - 1 >= 0 && lines[currentPosition.i][currentPosition.j - 1].isDigit()) {
        currentPosition = Position(currentPosition.i, currentPosition.j - 1)
    }
    while (currentPosition.j <= lines[position.i].size - 1 && lines[currentPosition.i][currentPosition.j].isDigit()) {
        number += lines[currentPosition.i][currentPosition.j].toString()
        currentPosition = Position(currentPosition.i, currentPosition.j + 1)
    }
    return Integer.parseInt(number)
}

private fun checkIfAdjacentGear(lines: List<List<Char>>, position: Position): Boolean {
    for (i in position.i - 1..position.i + 1) {
        if (i >= 0 && i <= lines.size - 1) {
            for (j in position.j - 1..position.j + 1) {
                if (j >= 0 && j <= lines[i].size - 1) {
                    if (!lines[i][j].isDigit() && lines[i][j] != '.') return true
                }
            }
        }
    }
    return false
}

private class Position(
    val i: Int, val j: Int
)

private fun classifyNumber(lines: List<List<Char>>, position: Position): Classifier {
    return when {
        (position.i == 0 && position.j == 0) -> return Classifier.leftUpperEdge
        (position.i == 0 && position.j == lines[0].size - 1) -> return Classifier.rightUpperEdge
        (position.i == lines.size - 1 && position.j == 0) -> return Classifier.leftLowerEdge
        (position.i == lines.size - 1 && position.j == lines[position.i].size - 1) -> return Classifier.rightLowerEdge
        (position.i == 0) -> return Classifier.upperBounded
        (position.j == 0) -> return Classifier.leftBounded
        (position.i == lines.size - 1) -> return Classifier.lowerBounded
        (position.j == lines[position.i].size - 1) -> return Classifier.rightBounded
        else -> { Classifier.none }
    }
}

private fun checkAdjacentSpaces(lines: List<List<Char>>, position: Position): Boolean {
    return when (classifyNumber(lines, position)) {
        Classifier.leftUpperEdge -> isGear(listOf(lines[position.i][position.j+1],
            lines[position.i+1][position.j+1], lines[position.i+1][position.j]))

        Classifier.rightUpperEdge -> isGear(listOf(lines[position.i][position.j-1],
            lines[position.i+1][position.j-1], lines[position.i+1][position.j]))

        Classifier.leftLowerEdge -> isGear(listOf(lines[position.i][position.j+1],
            lines[position.i-1][position.j+1], lines[position.i-1][position.j]))

        Classifier.rightLowerEdge -> isGear(listOf(lines[position.i][position.j-1],
            lines[position.i-1][position.j-1], lines[position.i-1][position.j]))

        Classifier.upperBounded -> isGear(listOf(lines[position.i][position.j-1],
            lines[position.i][position.j+1], lines[position.i+1][position.j-1],
            lines[position.i+1][position.j], lines[position.i+1][position.j+1]))

        Classifier.lowerBounded -> isGear(listOf(lines[position.i][position.j-1],
            lines[position.i][position.j+1], lines[position.i-1][position.j-1],
            lines[position.i-1][position.j], lines[position.i-1][position.j+1]))

        Classifier.leftBounded -> isGear(listOf(lines[position.i-1][position.j],
            lines[position.i-1][position.j+1], lines[position.i][position.j+1],
            lines[position.i+1][position.j+1], lines[position.i+1][position.j]))

        Classifier.rightBounded -> isGear(listOf(lines[position.i-1][position.j],
            lines[position.i-1][position.j-1], lines[position.i][position.j-1],
            lines[position.i+1][position.j-1], lines[position.i+1][position.j]))

        Classifier.none -> isGear(listOf(lines[position.i-1][position.j-1],
            lines[position.i-1][position.j], lines[position.i-1][position.j+1],
            lines[position.i][position.j-1], lines[position.i][position.j+1],
            lines[position.i+1][position.j-1], lines[position.i+1][position.j],
            lines[position.i+1][position.j+1]))
    }
}

private fun isGear(chars: List<Char>): Boolean {
    val predicate: (Char) -> Boolean = {!it.isDigit() && it != '.'}
    return chars.any(predicate)
}

enum class Classifier {
    upperBounded, lowerBounded, leftBounded, rightBounded, leftUpperEdge, rightUpperEdge, leftLowerEdge, rightLowerEdge, none
}

