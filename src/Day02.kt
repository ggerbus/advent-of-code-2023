fun main() {
    val cubes = mapOf(
            "blue" to 14,
            "red" to 12,
            "green" to 13
    )

    fun part1(input: List<String>): Int {
        return input.map {
            var  str = it.substring(5)
            val index = str.indexOf(":")
            var game = str.substring(0, index)
            str = str.substring(index + 2)

            str.split("; ").map {
                it.split(", ").map {
                    val colorCubes = it.split(" ")
                    if(cubes.containsKey(colorCubes.get(1))
                            && colorCubes.get(0).toInt() > cubes.get(colorCubes.get(1))!!) {
                        game = "0"
                    }
                }
            }
            return@map game.toInt()
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.map {
            var  str = it.substring(it.indexOf(":") + 2)
            val rounds = str.split("; ")
            var max = Triple(1,1,1)
            rounds.map {
                it.split(", ").map {
                    val round = it.split(" ")
                    if("blue".equals(round.get(1)) && max.first < round.get(0).toInt()){
                        max = Triple(round.get(0).toInt(), max.second, max.third)
                    } else if("red".equals(round.get(1)) && max.second < round.get(0).toInt()) {
                        max = Triple(max.first, round.get(0).toInt(), max.third)
                    } else if("green".equals(round.get(1)) && max.third < round.get(0).toInt()) {
                        max = Triple(max.first, max.second, round.get(0).toInt())
                    }
                }
            }
            return@map max.first*max.second*max.third
        }.sum()
    }

    val testInput = readInput("Day02_test")
    val input = readInput("Day02")

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
