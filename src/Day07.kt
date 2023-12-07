fun main() {


    fun part1(input: List<String>): Int {
        return input.map {
            Hand(
                    it.substringBefore(" "),
                    it.substringAfter(" ").toInt(),
            )
        }.sortedWith(
                Hand::compareTo
        ).mapIndexed { index, hand ->
//            "${hand} - ${index + 1}".println()
            return@mapIndexed (index + 1) * hand.bid
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.map {
            Hand(
                    it.substringBefore(" "),
                    it.substringAfter(" ").toInt(),
            )
        }.sortedWith(
                Hand::compareTo2
        ).mapIndexed { index, hand ->
//            "${hand} - ${index + 1}".println()
            return@mapIndexed (index + 1) * hand.bid
        }.sum()
    }

    val testInput = readInput("Day07_test")
    val input = readInput("Day07")

//    testInput.println()
    part1(testInput).println()

    "****************************************".println()

//    input.println()
    part1(input).println()

    "################################################################".println()

//    testInput.println()
    part2(testInput).println()

    "****************************************".println()

//    input.println()
    part2(input).println()
}


data class Hand(
        val hand: String,
        var bid: Int,
) {
    var type: HandType
    var type2: HandType
    val chars: CharArray
    val hasJocker: Boolean

    val cards = mapOf(
            'A' to 14,
            'K' to 13,
            'Q' to 12,
            'J' to 11,
            'T' to 10,
            '9' to 9,
            '8' to 8,
            '7' to 7,
            '6' to 6,
            '5' to 5,
            '4' to 4,
            '3' to 3,
            '2' to 2,
    )
    val cards2 = mapOf(
            'A' to 13,
            'K' to 12,
            'Q' to 11,
            'T' to 10,
            '9' to 9,
            '8' to 8,
            '7' to 7,
            '6' to 6,
            '5' to 5,
            '4' to 4,
            '3' to 3,
            '2' to 2,
            'J' to 1,
    )
    init {
        chars = hand.toCharArray()

        type = findType(this.hand)

        val numOfJ = this.hand.groupingBy {
            it
        }.eachCount().filter {
            it.key == 'J'
        }.mapNotNull {
            it.value
        }.firstOrNull() ?: 0

        hasJocker = numOfJ > 0

        type2 = HandType from (type.order)

        if(hasJocker) {
            if (type == HandType.FIVE_OF_A_KIND){
                type2 = HandType.FIVE_OF_A_KIND
            } else if(type == HandType.FOUR_OF_A_KIND){
                type2 = HandType.FIVE_OF_A_KIND
            } else if(type == HandType.FULL_HAUSE){
                type2 = HandType.FIVE_OF_A_KIND
            } else if(type == HandType.THREE_OF_A_KIND){
                if(numOfJ == 2){
                    type2 = HandType.FIVE_OF_A_KIND
                } else {
                    type2 = HandType.FOUR_OF_A_KIND
                }
            } else if(type == HandType.TWO_PAIR){
                if(numOfJ == 1){
                    type2 = HandType.FULL_HAUSE
                } else {
                    type2 = HandType.FOUR_OF_A_KIND
                }
            } else if(type == HandType.ONE_PAIR){
                type2 = HandType.THREE_OF_A_KIND
            } else if(type == HandType.HIGH_CARD){
                type2 = HandType.ONE_PAIR
            }
        }
    }

    private fun findType(str: String): HandType {
        var typeCount = Array(5) { 0 }
        hand.groupingBy {
            it
        }.eachCount().map {
            it.value
        }.filter {
            it > 1
        }.forEach {
            typeCount[it - 1]++
        }

        if (typeCount[4] == 1) {
            return HandType.FIVE_OF_A_KIND
        } else if (typeCount[3] == 1) {
            return HandType.FOUR_OF_A_KIND
        } else if (typeCount[2] == 1 && typeCount[1] == 1) {
            return HandType.FULL_HAUSE
        } else if (typeCount[2] == 1) {
            return HandType.THREE_OF_A_KIND
        } else if (typeCount[1] == 2) {
            return HandType.TWO_PAIR
        } else if (typeCount[1] == 1) {
            return HandType.ONE_PAIR
        } else {
            return HandType.HIGH_CARD
        }
    }

    fun compareTo(other: Hand): Int{
        if(this.type == other.type){
            this.chars.forEachIndexed { index, c ->
                if(c != other.chars.get(index)){
                    return cards.get(c)!! - cards.get(other.chars.get(index))!!
                }
            }
        }
        return this.type.order - other.type.order
    }
    fun compareTo2(other: Hand): Int{
        if(this.type2 == other.type2){
            this.chars.forEachIndexed { index, c ->
                if(c != other.chars.get(index)){
                    return cards2.get(c)!! - cards2.get(other.chars.get(index))!!
                }
            }
        }
        return this.type2.order - other.type2.order
    }

    override fun toString(): String {
        return "Hand(hand='$hand', bid=$bid, type=$type, type2=$type2, hasJocker=$hasJocker"
    }

}

enum class HandType(val order: Int) {
    FIVE_OF_A_KIND(7),
    FOUR_OF_A_KIND(6),
    FULL_HAUSE(5),
    THREE_OF_A_KIND(4),
    TWO_PAIR(3),
    ONE_PAIR(2),
    HIGH_CARD(1);

    companion object {
        infix fun from(value: Int): HandType = HandType.values().first { it.order == value }
    }
}
