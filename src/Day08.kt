fun main() {

    fun findLCM(a: Long, b: Long): Long {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }

    fun findLCMOfListOfNumbers(numbers: List<Long>): Long {
        var result = numbers[0]
        for (i in 1 until numbers.size) {
            result = findLCM(result, numbers[i])
        }
        return result
    }


    fun part1(input: List<String>): Int {
        val instructions = input.get(0)

        val nodes = input.drop(2).map{
            it.substring(0,3) to Triple<String, String, String>(it.substring(0,3), it.substring(7, 10), it.substring(12, 15))
        }.toMap()

        var node = nodes.get("AAA")
        var instructionIndex = 0
        var counter = 0
        while (true){
            counter++
            val instruction = instructions.get(instructionIndex)

            var nextNodeName = node?.second
            if (instruction == 'R') {
                nextNodeName = node?.third
            }
            if(nextNodeName == "ZZZ"){
                break
            }
            instructionIndex++
            if(instructionIndex == instructions.length){
                instructionIndex = 0
            }
            node = nodes.get(nextNodeName)
        }
        return counter
    }


    fun part2(input: List<String>): Long {
        val instructions = input.get(0)

        val nodes = input.drop(2).map{
            it.substring(0,3) to Triple<String, String, String>(it.substring(0,3), it.substring(7, 10), it.substring(12, 15))
        }.toMap()

        var startNodes = nodes.keys.filter {
            it.endsWith("A")
        }
//        startNodes.println()

        val results = LongArray(startNodes.size){0L}

        startNodes.forEachIndexed { index, startNode ->
            var node = nodes.get(startNode)
            var instructionIndex = 0
            var counter = 0L
            while (true){
                counter++
                val instruction = instructions.get(instructionIndex)

                var nextNodeName = node?.second
                if (instruction == 'R') {
                    nextNodeName = node?.third
                }
                if(nextNodeName?.endsWith("Z")?:true){
                    break
                }
                instructionIndex++
                if(instructionIndex == instructions.length){
                    instructionIndex = 0
                }
                node = nodes.get(nextNodeName)
            }
            results[index] = counter
        }
//        results.toList().println()

        return findLCMOfListOfNumbers(results.toList())
    }

    val testInput = readInput("Day08_test")
    val test2Input = readInput("Day08_test2")
    val input = readInput("Day08")
    val test22Input = readInput("Day08_2test2")

    part1(testInput).println()

    "****************************************".println()

    part1(test2Input).println()

    "****************************************".println()

    part1(input).println()

    "################################################################".println()

    part2(test22Input).println()

    "****************************************".println()

    part2(input).println()
}
