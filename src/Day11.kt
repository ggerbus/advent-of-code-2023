fun main() {

    fun loadGalaxies(input: List<String>, seek: Int): MutableList<Pair<Int, Int>> {
        val galaxies = mutableListOf<Pair<Int, Int>>()
        val rows = input.size
        val columns = input.get(0).length

        var matrix = Array(rows) {
            Array(columns) { '.' }
        }

        val expandedRows = mutableListOf<Int>()
        val expandedColumns = mutableListOf<Int>()

        input.forEachIndexed { rowIndex, row ->
            if (!row.contains("#")) {
                expandedRows.add(rowIndex)
            }
            row.toCharArray().forEachIndexed { columnIndex, c ->
                matrix[rowIndex][columnIndex] = c
            }
        }

        "expandedRows: $expandedRows".println()

        for (j in 0..columns - 1) {
            var hasGalaxy = false
            for (i in 0..rows - 1) {
                if (matrix[i][j] == '#') {
                    hasGalaxy = true
                }
            }
            if (!hasGalaxy) {
                expandedColumns.add(j)
            }
        }

        "expandedColumns: $expandedColumns".println()

        var rowSeek = 0

        for (i in 0..rows - 1) {
            var columnSeek = 0
            if (expandedRows.contains(i)) {
                rowSeek += seek
                continue
            }
            for (j in 0..columns - 1) {
                if (expandedColumns.contains(j)) {
                    columnSeek += seek
                    continue
                }
                if (matrix[i][j] == '#')
                    galaxies.add(Pair(i + rowSeek, j + columnSeek))
            }
        }

        "galaxies: $galaxies".println()
        return galaxies
    }

    fun part1(input: List<String>): Int {
        val galaxies = loadGalaxies(input, 1)

        var sum = 0
        for (i in 0..galaxies.count()-1) {
            for (j in i+1..galaxies.count() - 1) {
                val g1 = galaxies.get(i)
                val g2 = galaxies.get(j)
                sum += Math.abs(g2.first - g1.first) + Math.abs(g2.second - g1.second)
            }
        }

        return sum
    }


    fun part2(input: List<String>): Long {
        val galaxies = loadGalaxies(input, 999999)

        var sum = 0L
        for (i in 0..galaxies.count()-1) {
            for (j in i+1..galaxies.count() - 1) {
                val g1 = galaxies.get(i)
                val g2 = galaxies.get(j)
                sum += Math.abs(g2.first - g1.first) + Math.abs(g2.second - g1.second)
            }
        }

        return sum
    }

    val testInput = readInput("Day11_test")
    val input = readInput("Day11")

    part1(testInput).println()

    "****************************************".println()

    part1(input).println()

    "################################################################".println()

    part2(testInput).println()

    "****************************************".println()

    part2(input).println()
}
