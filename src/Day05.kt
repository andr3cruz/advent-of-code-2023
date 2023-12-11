fun main() {

    fun part1(input: List<String>): Long {
        val line = input[0].split(":")[1].trim().split("\\s+".toRegex()).map { it.toLong() }
        var minLocation = Long.MAX_VALUE
        for (seed in line) {
            val location = calculateLocation(seed, input)
            if (location < minLocation) minLocation = location
        }
        return minLocation
    }

    fun part2(input: List<String>): Long {
        val line = input[0].split(":")[1].trim().split("\\s+".toRegex()).map { it.toLong() }

        var minLocation = Long.MAX_VALUE
        val seedRanges = mutableListOf<SeedRange>()
        val seedCache = mutableMapOf<Long, Long>()

        for (i in line.indices step 2) seedRanges.add(SeedRange(line[i], line[i+1]))

        for (seedRange in seedRanges) {
            for(i in 0..seedRange.range) {
                seedCache[seedRange.initialNumber + i] ?:
                calculateLocation(seedRange.initialNumber + i, input).let {
                    seedCache[seedRange.initialNumber + i] = it
                    if (it < minLocation) minLocation = it
                }
            }
        }
        return minLocation
    }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()

}

fun calculateLocation(seed: Long, input: List<String>): Long {
    val seed2Soil = mutableListOf<Line>()
    val soil2Fertilizer = mutableListOf<Line>()
    val fertilizer2Water = mutableListOf<Line>()
    val water2Light = mutableListOf<Line>()
    val light2Temperature = mutableListOf<Line>()
    val temperature2Humidity = mutableListOf<Line>()
    val humidity2Location = mutableListOf<Line>()

    var index = 1
    while (index < input.size) {
        if(input[index].trim() == "seed-to-soil map:") {
            index++
            while (index < input.size && input[index].isNotEmpty()) {
                val values = input[index].trim().split("\\s+".toRegex())
                seed2Soil.add(Line(values[0].toLong(), values[1].toLong(), values[2].toLong()))
                index++
            }
        }

        if(input[index] == "soil-to-fertilizer map:") {
            index++
            while (index < input.size && input[index].isNotEmpty()) {
                val values = input[index].trim().split("\\s+".toRegex())
                soil2Fertilizer.add(Line(values[0].toLong(), values[1].toLong(), values[2].toLong()))
                index++
            }
        }

        if(input[index] == "fertilizer-to-water map:") {
            index++
            while (index < input.size && input[index].isNotEmpty()) {
                val values = input[index].trim().split("\\s+".toRegex())
                fertilizer2Water.add(Line(values[0].toLong(), values[1].toLong(), values[2].toLong()))
                index++
            }
        }

        if(input[index] == "water-to-light map:") {
            index++
            while (index < input.size && input[index].isNotEmpty()) {
                val values = input[index].trim().split("\\s+".toRegex())
                water2Light.add(Line(values[0].toLong(), values[1].toLong(), values[2].toLong()))
                index++
            }
        }

        if(input[index] == "light-to-temperature map:") {
            index++
            while (index < input.size && input[index].isNotEmpty()) {
                val values = input[index].trim().split("\\s+".toRegex())
                light2Temperature.add(Line(values[0].toLong(), values[1].toLong(), values[2].toLong()))
                index++
            }
        }

        if(input[index] == "temperature-to-humidity map:") {
            index++
            while (index < input.size && input[index].isNotEmpty()) {
                val values = input[index].trim().split("\\s+".toRegex())
                temperature2Humidity.add(Line(values[0].toLong(), values[1].toLong(), values[2].toLong()))
                index++
            }
        }

        if(input[index] == "humidity-to-location map:") {
            index++
            while (index < input.size && input[index].isNotEmpty()) {
                val values = input[index].trim().split("\\s+".toRegex())
                humidity2Location.add(Line(values[0].toLong(), values[1].toLong(), values[2].toLong()))
                index++
            }
        }

        index++
    }

    var soil: Long = 0
    var fertilizer: Long = 0
    var water: Long = 0
    var light: Long = 0
    var temperature: Long = 0
    var humidity: Long = 0
    var location: Long = 0

    for (line in seed2Soil) {
        soil = if (line.sourceRange <= seed && seed <= line.sourceRange + line.length) {
            soil = line.destRange + (seed - line.sourceRange)
            break
        } else seed
    }

    for (line in soil2Fertilizer) {
        fertilizer = if (line.sourceRange <= soil && soil <= line.sourceRange + line.length) {
            fertilizer = line.destRange + (soil - line.sourceRange)
            break
        } else soil
    }

    for (line in fertilizer2Water) {
        water = if (line.sourceRange <= fertilizer && fertilizer <= line.sourceRange + line.length) {
            water = line.destRange + (fertilizer - line.sourceRange)
            break
        } else fertilizer
    }

    for (line in water2Light) {
        light = if (line.sourceRange <= water && water <= line.sourceRange + line.length) {
            light = line.destRange + (water - line.sourceRange)
            break
        } else water
    }

    for (line in light2Temperature) {
        temperature = if (line.sourceRange <= light && light <= line.sourceRange + line.length) {
            temperature = line.destRange + (light - line.sourceRange)
            break
        } else light
    }

    for (line in temperature2Humidity) {
        humidity = if (line.sourceRange <= temperature && temperature <= line.sourceRange + line.length) {
            humidity = line.destRange + (temperature - line.sourceRange)
            break
        } else temperature
    }

    for (line in humidity2Location) {
        location = if (line.sourceRange <= humidity && humidity <= line.sourceRange + line.length) {
            location = line.destRange + (humidity - line.sourceRange)
            break
        } else humidity
    }

    return location
}

data class Line(
    val destRange: Long,
    val sourceRange: Long,
    val length: Long
)

data class SeedRange(
    val initialNumber: Long,
    val range: Long
)

