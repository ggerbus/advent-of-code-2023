fun main() {
    var nums = listOf(
            Pair("1", "one"),
            Pair("2", "two"),
            Pair("3", "three"),
            Pair("4", "four"),
            Pair("5", "five"),
            Pair("6", "six"),
            Pair("7", "seven"),
            Pair("8", "eight"),
            Pair("9", "nine")
    )

    fun part1(input: List<String>): Int {
        val result = input.map {
            it.replace("[^0-9]".toRegex(),"")
        }
        val result1 = result.map{
            (it.first().toString() + it.last().toString()).toInt()
        }.sum()
        return result1
    }

    fun part2(input: List<String>): Int {
        return input.map{ it ->
            val list = nums.map { x ->
                Triple(x.first, it.indexOf(x.second), it.lastIndexOf(x.second))
            }.filter {
                it.second > -1
            }.toMutableList()

            list.addAll(nums.map { x ->
                Triple(x.first, it.indexOf(x.first), it.lastIndexOf(x.first))
            }.filter {
                it.second > -1
            })

            return@map list
        }.map {
            it.minBy { it.second }.first + it.maxBy { it.third }.first
        }.sumOf {
            it.toInt()
        }
    }

    val testInput = readInput("Day01_test")
    val testInput2 = readInput("Day01_test2")
    val input = readInput("Day01")

    testInput.println()
    part1(testInput).println()

    "****************************************".println()

    input.println()
    part1(input).println()

    "################################################################".println()

    testInput2.println()
    part2(testInput2).println()

    "****************************************".println()

    input.println()
    part2(input).println()
}
