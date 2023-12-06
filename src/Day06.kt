import kotlin.math.min

fun main() {

    fun part1(input: List<String>): Long {
        val games = input.get(0).parseDay06("Time:")
                .zip(
                    input.get(1).parseDay06("Distance:")
                )

        val items = games.map {game ->
            var minTime = -1
            for (hold in 1..game.first - 1) {
                if((game.first - hold)*hold > game.second){
                    minTime = hold
                    break
                }
            }
            var maxTime = -1
            for (hold in game.first - 1 downTo 1) {
                if((game.first - hold)*hold > game.second){
                    maxTime = hold
                    break
                }
            }
            return@map maxTime - minTime + 1
        }
        var result = 1L
        items.forEach {
            result *= it
        }
        return result
    }

    fun part2(input: List<String>): Long {
        val time = input.get(0).substringAfter("Time: ").replace(" ", "").trim().toLong()
        val distance = input.get(1).substringAfter("Distance: ").replace(" ", "").trim().toLong()

        var minTime = -1L
        for (hold in 1..time - 1) {
            if((time - hold)*hold > distance){
                minTime = hold
                break
            }
        }
        var maxTime = -1L
        for (hold in time - 1 downTo 1) {
            if((time - hold)*hold > distance){
                maxTime = hold
                break
            }
        }
        return maxTime - minTime + 1L
    }

    val testInput = readInput("Day06_test")
    val input = readInput("Day06")

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

fun String.parseDay06(prefix: String) = this
        .substringAfter(prefix)
        .trim()
        .split("\\s+".toRegex())
        .map {
            it.toInt()
        }

