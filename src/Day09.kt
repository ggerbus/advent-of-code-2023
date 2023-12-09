fun main() {

    fun interpolate(list: List<Int>): Int {
        val next = list.zipWithNext().map {
            it.second - it.first
        }
        if(next.none { it != 0 }) {
            return 0
        }
        val dif =  interpolate(next)
        return next.get(next.size-1) + dif
    }

    fun interpolate2(list: List<Int>): Int {
        val next = list.zipWithNext().map {
            it.second - it.first
        }
//        next.println()
        if(next.none { it != 0 }) {
            return 0
        }
        val dif =  interpolate2(next)
        return next.get(0) - dif
    }

    fun part1(input: List<String>): Int {
        return input.map {
            val list = it.split(" ").map { it.toInt() }
            val dif = interpolate(list)
            return@map list.get(list.size-1) + dif
        }.sum()
    }


    fun part2(input: List<String>): Int {
        return input.map {
            val list = it.split(" ").map { it.toInt() }
            val dif = interpolate2(list)
            return@map list.get(0) - dif
        }.sum()
    }

    val testInput = readInput("Day09_test")
    val input = readInput("Day09")

    part1(testInput).println()

    "****************************************".println()

    part1(input).println()

    "################################################################".println()

    part2(testInput).println()

    "****************************************".println()

    part2(input).println()
}
