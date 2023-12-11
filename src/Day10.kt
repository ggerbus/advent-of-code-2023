fun main() {

    fun findPath(input: List<String>): Triple<Matrix10,Long,MutableList<Position>> {
        val rows = input.size
        val columns = input.get(0).length
        val matrix = Matrix10(rows, columns, input)
        var path = mutableListOf<Position>()

        matrix.println()
        var position = matrix.animal ?: Pair(-1, -1)
        var max = -1L

        //up
        if (position.first > 0) {
            val candidatePath = mutableListOf<Position>()
            val steps = matrix.calculate(Pair(position.first - 1, position.second), 1L, PreviousPosition.DOWN, candidatePath)
            if (steps > max) {
                max = steps
                path.addAll(candidatePath)
            }
        }
        //down
        if (position.first < matrix.rows - 1) {
            val candidatePath = mutableListOf<Position>()
            val steps = matrix.calculate(Pair(position.first + 1, position.second), 1L, PreviousPosition.UP, candidatePath)
            if (steps > max) {
                max = steps
                path.clear()
                path.addAll(candidatePath)
            }
        }
        //left
        if (position.second > 0) {
            val candidatePath = mutableListOf<Position>()
            val steps = matrix.calculate(Pair(position.first, position.second - 1), 1L, PreviousPosition.RIGHT, candidatePath)
            if (steps > max) {
                max = steps
                path.clear()
                path.addAll(candidatePath)
            }
        }
        //right
        if (position.first < matrix.columns - 1) {
            val candidatePath = mutableListOf<Position>()
            val steps = matrix.calculate(Pair(position.first, position.second + 1), 1L, PreviousPosition.LEFT, candidatePath)
            if (steps > max) {
                max = steps
                path.clear()
                path.addAll(candidatePath)
            }
        }
        return Triple(matrix, max, path)
    }

    fun part1(input: List<String>): Long {
        var max = findPath(input).second

        return (max / 2).toLong()
    }


    fun part2(input: List<String>): Long {
        val (matrix10, max, path) = findPath(input)

        return matrix10.enclosedTiles(path)
    }

    val testInput = readInput("Day10_test")
    val testInput2 = readInput("Day10_test2")
    val test2Input = readInput("Day10_2test")
    val test2Input2 = readInput("Day10_2test2")
    val test2Input3 = readInput("Day10_2test3")
    val input = readInput("Day10")

    part1(testInput).println()

    "****************************************".println()

    part1(testInput2).println()

    "****************************************".println()

    part1(input).println()

    "################################################################".println()

    part2(test2Input).println()

    "################################################################".println()

    part2(test2Input2).println()

    "################################################################".println()

    part2(test2Input3).println()

    "****************************************".println()

    part2(input).println()
}

data class Matrix10(
        val rows: Int,
        val columns: Int,
        val input: List<String>
){
    val matrix: Array<Array<Char>>
    var animal: Pair<Int, Int>? = null

    init {
        matrix = Array(rows) {
            Array(columns) {'.'}
        }

        input.forEachIndexed { rowIndex, str ->
            str.toCharArray().forEachIndexed { index, c ->
                matrix.get(rowIndex)[index] = c
                if(c == 'S'){
                    animal = Pair(rowIndex, index)
                }
            }
        }
    }

    tailrec fun calculate(curr: Pair<Int, Int>, count: Long, previous: PreviousPosition, path: MutableList<Position>): Long {
        var maxSteps = -1L

//        "current: $curr, count: $count, prev.direct: $previous".println()
        if(path.any{ it.pos == curr }){
            return -1
        }

        val simbol = matrix[curr.first][curr.second]

        path.add(Position(simbol, curr, previous))

        if(simbol == 'S'){
            return count
        }
        if(simbol == '|'){
            if(previous == PreviousPosition.LEFT || previous == PreviousPosition.RIGHT)
                return -1
            if(previous == PreviousPosition.DOWN){
                if(curr.first == 0)
                    return -1
                return calculate(Pair(curr.first-1,curr.second), count+1, PreviousPosition.DOWN, path)
            }
            if(previous == PreviousPosition.UP){
                if(curr.first == rows - 1)
                    return -1
                return calculate(Pair(curr.first+1,curr.second), count+1, PreviousPosition.UP, path)
            }
        }
        if(simbol == '-'){
            if(previous == PreviousPosition.DOWN || previous == PreviousPosition.UP)
                return -1
            if(previous == PreviousPosition.RIGHT){
                if(curr.second == 0)
                    return -1
                return calculate(Pair(curr.first,curr.second-1), count+1, PreviousPosition.RIGHT, path)
            }
            if(previous == PreviousPosition.LEFT){
                if(curr.second == columns - 1)
                    return -1
                return calculate(Pair(curr.first,curr.second+1), count+1, PreviousPosition.LEFT, path)
            }
        }
        if(simbol == 'L'){
            if(previous == PreviousPosition.LEFT || previous == PreviousPosition.DOWN)
                return -1
            if(previous == PreviousPosition.RIGHT){
                if(curr.first == 0)
                    return -1
                return calculate(Pair(curr.first-1,curr.second), count+1, PreviousPosition.DOWN, path)
            }
            if(previous == PreviousPosition.UP){
                if(curr.second == columns - 1)
                    return -1
                return calculate(Pair(curr.first,curr.second+1), count+1, PreviousPosition.LEFT, path)
            }
        }
        if(simbol == 'J'){
            if(previous == PreviousPosition.RIGHT || previous == PreviousPosition.DOWN)
                return -1
            if(previous == PreviousPosition.LEFT){
                if(curr.first == 0)
                    return -1
                return calculate(Pair(curr.first-1,curr.second), count+1, PreviousPosition.DOWN, path)
            }
            if(previous == PreviousPosition.UP){
                if(curr.second == 0)
                    return -1
                return calculate(Pair(curr.first,curr.second-1), count+1, PreviousPosition.RIGHT, path)
            }
        }
        if(simbol == '7'){
            if(previous == PreviousPosition.RIGHT || previous == PreviousPosition.UP)
                return -1
            if(previous == PreviousPosition.LEFT){
                if(curr.first == rows - 1)
                    return -1
                return calculate(Pair(curr.first+1,curr.second), count+1, PreviousPosition.UP, path)
            }
            if(previous == PreviousPosition.DOWN){
                if(curr.second == 0)
                    return -1
                return calculate(Pair(curr.first,curr.second-1), count+1, PreviousPosition.RIGHT, path)
            }
        }
        if(simbol == 'F'){
            if(previous == PreviousPosition.LEFT || previous == PreviousPosition.UP)
                return -1
            if(previous == PreviousPosition.RIGHT){
                if(curr.first == rows - 1)
                    return -1
                return calculate(Pair(curr.first+1,curr.second), count+1, PreviousPosition.UP, path)
            }
            if(previous == PreviousPosition.DOWN){
                if(curr.second == 0)
                    return -1
                return calculate(Pair(curr.first,curr.second+1), count+1, PreviousPosition.LEFT, path)
            }
        }

        return maxSteps
    }

    fun enclosedTiles(path: MutableList<Position>): Long{
        var counterLeft = 0L
        var counterRight = 0L
        path.forEach{
            val simbol = matrix[it.pos.first][it.pos.second]
            val previous = it.previous

            if(simbol == 'L'){
                if(previous == PreviousPosition.RIGHT){
                    counterRight++
                }
                if(previous == PreviousPosition.UP){
                    counterLeft++
                }
            }
            if(simbol == 'J'){
                if(previous == PreviousPosition.LEFT){
                    counterLeft++
                }
                if(previous == PreviousPosition.UP){
                    counterRight++
                }
            }
            if(simbol == '7'){
                if(previous == PreviousPosition.LEFT){
                    counterRight++
                }
                if(previous == PreviousPosition.DOWN){
                    counterLeft++
                }
            }
            if(simbol == 'F'){
                if(previous == PreviousPosition.RIGHT){
                    counterLeft++
                }
                if(previous == PreviousPosition.DOWN){
                    counterRight++
                }
            }
        }

        val isLeftTurnLoop = counterLeft > counterRight
        "Lop is leftTurn = $isLeftTurnLoop (leftTurns> $counterLeft, rightTurns: $counterRight)".println()

        var counter = 0L
        //skiping first and last row, and also skip first and last column
        for (i in 1 .. rows-2){
            for (j in 1..columns-2){
                val simbol = matrix[i][j]
                if(path.none{ it.pos == Pair(i,j)} && isEnclosed(i,j, path, isLeftTurnLoop)){
                    "x: $i, y: $j".println()
                    counter++
                }
            }
        }
        return counter
    }

    private fun isEnclosed(x: Int, y: Int, path: MutableList<Position>, isLeftTurnLoop: Boolean): Boolean {
        var isEnclosedUp = false
        for (i in (x - 1) downTo 0){
            val pathPosition = path.firstOrNull { it.pos.equals(Pair(i, y)) }
            if(pathPosition != null) {
                if(pathPosition.isInLoop(PreviousPosition.DOWN, isLeftTurnLoop, path))
                    isEnclosedUp = true
                break
            }
        }
        if (!isEnclosedUp)
            return false

        var isEnclosedDown = false
        for (i in (x + 1) .. rows - 1){
            val pathPosition = path.firstOrNull { it.pos.equals(Pair(i, y)) }
            if(pathPosition != null) {
                if(pathPosition.isInLoop(PreviousPosition.UP, isLeftTurnLoop, path))
                    isEnclosedDown = true
                break
            }
        }
        if (!isEnclosedDown)
            return false

        var isEnclosedLeft = false
        for (j in (y - 1) downTo  0){
            val pathPosition = path.firstOrNull { it.pos.equals(Pair(x, j)) }
            if(pathPosition != null) {
                if(pathPosition.isInLoop(PreviousPosition.RIGHT, isLeftTurnLoop, path))
                    isEnclosedLeft = true
                break
            }
        }
        if (!isEnclosedLeft)
            return false

        var isEnclosedRight = false
        for (j in (y + 1) .. columns - 1){
            val pathPosition = path.firstOrNull { it.pos.equals(Pair(x, j)) }
            if(pathPosition != null) {
                if(pathPosition.isInLoop(PreviousPosition.LEFT, isLeftTurnLoop, path))
                    isEnclosedRight = true
                break
            }
        }
        if (!isEnclosedRight)
            return false

        return true
    }

    override fun toString(): String {
        return "Matrix10(rows=$rows, columns=$columns, animal=$animal)"
    }

}


data class Position(
        val simbol: Char,
        val pos: Pair<Int, Int>,
        val previous: PreviousPosition
){
    fun isInLoop(candidatePosition: PreviousPosition, leftTurnLoop: Boolean, path: MutableList<Position>): Boolean {
        if(candidatePosition == PreviousPosition.DOWN){
            if(previous == PreviousPosition.LEFT && !leftTurnLoop)
                return true
            if(previous == PreviousPosition.RIGHT && leftTurnLoop)
                return true
            if(previous == PreviousPosition.UP){
                var nextPathPosition = path.firstOrNull { it.pos.equals(Pair(pos.first, pos.second-1)) }
                if(nextPathPosition != null && nextPathPosition.previous == PreviousPosition.RIGHT && leftTurnLoop)
                    return true

                nextPathPosition = path.firstOrNull { it.pos.equals(Pair(pos.first, pos.second+1)) }
                if(nextPathPosition != null && nextPathPosition.previous == PreviousPosition.LEFT && !leftTurnLoop)
                    return true
            }
        }
        if(candidatePosition == PreviousPosition.UP){
            if(previous == PreviousPosition.LEFT && leftTurnLoop)
                return true
            if(previous == PreviousPosition.RIGHT && !leftTurnLoop)
                return true
            if(previous == PreviousPosition.DOWN){
                var nextPathPosition = path.firstOrNull { it.pos.equals(Pair(pos.first, pos.second-1)) }
                if(nextPathPosition != null && nextPathPosition.previous == PreviousPosition.RIGHT && !leftTurnLoop)
                    return true

                nextPathPosition = path.firstOrNull { it.pos.equals(Pair(pos.first, pos.second+1)) }
                if(nextPathPosition != null && nextPathPosition.previous == PreviousPosition.LEFT && leftTurnLoop)
                    return true
            }
        }
        if(candidatePosition == PreviousPosition.RIGHT){
            if(previous == PreviousPosition.DOWN && !leftTurnLoop)
                return true
            if(previous == PreviousPosition.UP && leftTurnLoop)
                return true
            if(previous == PreviousPosition.LEFT){
                var nextPathPosition = path.firstOrNull { it.pos.equals(Pair(pos.first-1, pos.second)) }
                if(nextPathPosition != null && nextPathPosition.previous == PreviousPosition.DOWN && !leftTurnLoop)
                    return true

                nextPathPosition = path.firstOrNull { it.pos.equals(Pair(pos.first+1, pos.second)) }
                if(nextPathPosition != null && nextPathPosition.previous == PreviousPosition.UP && leftTurnLoop)
                    return true
            }
        }
        if(candidatePosition == PreviousPosition.LEFT){
            if(previous == PreviousPosition.DOWN && leftTurnLoop)
                return true
            if(previous == PreviousPosition.UP && !leftTurnLoop)
                return true
            if(previous == PreviousPosition.RIGHT){
                var nextPathPosition = path.firstOrNull { it.pos.equals(Pair(pos.first-1, pos.second)) }
                if(nextPathPosition != null && nextPathPosition.previous == PreviousPosition.DOWN && leftTurnLoop)
                    return true

                nextPathPosition = path.firstOrNull { it.pos.equals(Pair(pos.first+1, pos.second)) }
                if(nextPathPosition != null && nextPathPosition.previous == PreviousPosition.UP && !leftTurnLoop)
                    return true
            }
        }
        return false
    }

}

enum class PreviousPosition {
    DOWN,
    UP,
    LEFT,
    RIGHT;
}