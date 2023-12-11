fun main() {
    fun part1(input: List<String>): Int {
        val hands = mutableListOf<CharArray>()
        val bids = mutableListOf<Int>()
        val classifiedHands = mutableListOf<Hand>()

        for(line in input) {
            hands.add(line.split(" ")[0].trim().toCharArray())
            bids.add(line.split(" ")[1].trim().toInt())
        }

        for(i in hands.indices) {
            classifiedHands.add(Hand(hands[i], classifyHand(hands[i]), bids[i]))
        }

        classifiedHands.sortWith(compareBy(
            { it.classification },
            { it.cards[0].toRank() },
            { it.cards[1].toRank() },
            { it.cards[2].toRank() },
            { it.cards[3].toRank() },
            { it.cards[4].toRank() }
            ))

        return classifiedHands
            .mapIndexed{ index, hand -> (index+1) * hand.bid }
            .sum()
    }

    fun part2(input: List<String>): Int {
        val hands = mutableListOf<CharArray>()
        val bids = mutableListOf<Int>()
        val classifiedHands = mutableListOf<Hand>()

        for(line in input) {
            hands.add(line.split(" ")[0].trim().toCharArray())
            bids.add(line.split(" ")[1].trim().toInt())
        }

        for(i in hands.indices) {
            classifiedHands.add(Hand(hands[i], classifyHandWithJoker(hands[i]), bids[i]))
        }

        classifiedHands.sortWith(compareBy(
            { it.classification },
            { it.cards[0].toRankWithJoker() },
            { it.cards[1].toRankWithJoker() },
            { it.cards[2].toRankWithJoker() },
            { it.cards[3].toRankWithJoker() },
            { it.cards[4].toRankWithJoker() }
        ))

        return classifiedHands
            .mapIndexed{ index, hand -> (index+1) * hand.bid }
            .sum()
    }

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}

fun classifyHand(cards: CharArray): HandClassification {
    val cardsMap = mutableMapOf<Char, Int>()

    cards.forEach { card ->
        cardsMap[card] = cardsMap.getOrDefault(card, 0) + 1
    }

    return if (cardsMap.values.any { it == 5}) HandClassification.FIVE_OF_A_KIND
    else if (cardsMap.values.any { it == 4}) HandClassification.FOUR_OF_A_KIND
    else if (cardsMap.values.any { it == 3} && cardsMap.values.any { it == 2}) HandClassification.FULL_HOUSE
    else if (cardsMap.values.any { it == 3}) HandClassification.THREE_OF_A_KIND
    else if (cardsMap.values.count { it == 2 } == 2) HandClassification.TWO_PAIR
    else if (cardsMap.values.count { it == 2 } == 1) HandClassification.PAIR
    else HandClassification.HIGH_CARD
}

fun classifyHandWithJoker(cards: CharArray): HandClassification {
    val cardsMap = mutableMapOf<Char, Int>()
    var handClassification: HandClassification
    var jokerCount = 0

    cards.forEach { card ->
        if (card != 'J') {
            cardsMap[card] = cardsMap.getOrDefault(card, 0) + 1
        } else {
            jokerCount++
        }
    }

    handClassification = if (cardsMap.values.any { it == 5}) HandClassification.FIVE_OF_A_KIND
    else if (cardsMap.values.any { it == 4}) HandClassification.FOUR_OF_A_KIND
    else if (cardsMap.values.any { it == 3} && cardsMap.values.any { it == 2}) HandClassification.FULL_HOUSE
    else if (cardsMap.values.any { it == 3}) HandClassification.THREE_OF_A_KIND
    else if (cardsMap.values.count { it == 2 } == 2) HandClassification.TWO_PAIR
    else if (cardsMap.values.count { it == 2 } == 1) HandClassification.PAIR
    else HandClassification.HIGH_CARD

    repeat(jokerCount) {
        handClassification = handClassification.upgrade()
    }

    return handClassification
}

fun Char.toRank(): Int {
    return when (this) {
        'A' -> 12
        'K' -> 11
        'Q' -> 10
        'J' -> 9
        'T' -> 8
        '9' -> 7
        '8' -> 6
        '7' -> 5
        '6' -> 4
        '5' -> 3
        '4' -> 2
        '3' -> 1
        '2' -> 0
        else -> throw IllegalArgumentException("Invalid card: $this")
    }
}

fun Char.toRankWithJoker(): Int {
    return when (this) {
        'A' -> 12
        'K' -> 11
        'Q' -> 10
        'T' -> 9
        '9' -> 8
        '8' -> 7
        '7' -> 6
        '6' -> 5
        '5' -> 4
        '4' -> 3
        '3' -> 2
        '2' -> 1
        'J' -> 0
        else -> throw IllegalArgumentException("Invalid card: $this")
    }
}

fun HandClassification.upgrade(): HandClassification {
    return when (this) {
        HandClassification.FIVE_OF_A_KIND -> HandClassification.FIVE_OF_A_KIND
        HandClassification.FOUR_OF_A_KIND -> HandClassification.FIVE_OF_A_KIND
        HandClassification.FULL_HOUSE -> HandClassification.FOUR_OF_A_KIND
        HandClassification.THREE_OF_A_KIND -> HandClassification.FOUR_OF_A_KIND
        HandClassification.TWO_PAIR -> HandClassification.FULL_HOUSE
        HandClassification.PAIR -> HandClassification.THREE_OF_A_KIND
        HandClassification.HIGH_CARD -> HandClassification.PAIR
    }
}

data class Hand (
    val cards: CharArray,
    val classification: HandClassification,
    val bid: Int
)
enum class HandClassification {
    HIGH_CARD,
    PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND,
}
