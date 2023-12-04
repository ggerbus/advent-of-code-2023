fun main() {

    fun readCards(input: List<String>) = input.map {
        var str = it.substring(5)
        var index = str.indexOf(":")
        val cardId = str.substring(0, index).trim()
        str = str.substring(index + 2)

        index = str.indexOf(" | ")
        var wining = str.substring(0, index).trim()
        var my = str.substring(index + 2).trim()

        return@map Card(cardId.toInt(),
                wining.split("\\s+".toRegex()).map { it.toInt() },
                my.split("\\s+".toRegex()).map { it.toInt() })
    }

    fun part1(input: List<String>): Int {
        return readCards(input).map { card ->
            val count = card.myNumbers.filter {
                it in card.winingNumbers
            }.count()
            return@map Math.pow(2.0, count - 1.0 ).toInt()
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val cards = readCards(input)
        val cardArray = Array(cards.size){1}

        cards.forEach {card ->
            val count = card.myNumbers.filter {
                it in card.winingNumbers
            }.count()

            for(i in 1..count){
                if(card.num + i <= cards.size) {
                    cardArray[card.num - 1 + i] += cardArray[card.num - 1]
                }
            }
        }

        return cardArray.sum()
    }

    val testInput = readInput("Day04_test")
    val input = readInput("Day04")

    testInput.println()
    part1(testInput).println()

    "****************************************".println()

    input.println()
    part1(input).println()

    "################################################################".println()

    testInput.println()
    part2(testInput).println()

    "****************************************".println()

    input.println()
    part2(input).println()
}

data class Card(
        val num: Int,
        val winingNumbers: List<Int>,
        val myNumbers: List<Int>,
) {

}
