fun main() {
    val cubes = mapOf(
            "blue" to 14,
            "red" to 12,
            "green" to 13
    )

    fun addNumber(row: String, startIndex: Int, endIndex: Int, data: MutableList<Data>, rowIndex: Int) {
        val number = row.substring(startIndex, endIndex + 1).toInt()
        data.add(Data(rowIndex, -1, 'x', number, startIndex, endIndex))
    }

    fun extractData(input: List<String>, data: MutableList<Data>) {
        input.mapIndexed { rowIndex, row ->
            var startIndex = -1
            row.toCharArray().mapIndexed { index, it ->
                if (it.isDigit()) {
                    if (startIndex < 0) {
                        startIndex = index
                    } else {
                        if (index == row.length - 1) {
                            addNumber(row, startIndex, index, data, rowIndex)
                            startIndex = -1
                        }
                    }
                } else {
                    if (startIndex >= 0) {
                        addNumber(row, startIndex, index - 1, data, rowIndex)
                        startIndex = -1
                    } else {
                    }

                    if (it != '.') {
                        data.add(Data(rowIndex, index, it,-1, -1, -1))
                    }
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        val data = mutableListOf<Data>()
        val partNumbers = mutableListOf<Data>()

        extractData(input, data)

        val simbols = data.filter { it.isSimbol() }
        data.filter { it ->
            it.isNumber()
        }.map { num ->
            simbols.forEach {
                if(num.isPartNumber(it)){
                    partNumbers.add(num)
//                    num.println()
                }
            }

        }
        return partNumbers.sumOf { it.number }
    }

    fun part2(input: List<String>): Int {
        val data = mutableListOf<Data>()
        val partNumbers = mutableListOf<Data>()

        extractData(input, data)

        val nums = data.filter { it.isNumber() }
        return data.filter {
            it.isSimbol() && it.simbol == '*'
        }.map { simbol ->
            val parts = nums.filter { num->
                num.isPartNumber(simbol)
            }
            if(parts.count() == 2){
                return@map parts.map { it.number }.reduce { acc, next -> acc * next }
            } else {
                return@map 0
            }
        }.sum()
    }

    val testInput = readInput("Day03_test")
    val input = readInput("Day03")

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

data class Data(
        val row: Int,
        val simbolIndex: Int,
        val simbol: Char,
        val number: Int,
        val startIndex: Int,
        val endIndex: Int
) {
    fun isPartNumber(simbol: Data): Boolean {
        if(simbol.row !in IntRange(row - 1, row + 1)){
            return false
        }
        if(simbol.simbolIndex in IntRange(startIndex - 1, endIndex + 1)) {
            return true
        }
        return false
    }

    fun isNumber(): Boolean{
        return !isSimbol()
    }

    fun isSimbol(): Boolean {
        return simbolIndex >= 0
    }
}
