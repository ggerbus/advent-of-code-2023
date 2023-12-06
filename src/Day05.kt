fun main() {


    fun readRow(it: String): Triple<Long, Long, Long> {
        var values = it.split(" ")
                .filter {
                    it.isNotBlank()
                }.map {
                    it.trim().toLong()
                }
        val range = values.last()
        val destination = values.first()
        val source = values.get(1)
        return Triple(range, destination, source)
    }

    fun part1(input: List<String>): Long {
        var seeds = emptyList<Seed>()
        var isSeedToSoil = false
        var isSoilToFertilizer = false
        var isFertilizerToWater = false
        var isWaterToLight = false
        var isLightToTemperature = false
        var isTemperatureToHumidity = false
        var isHumidityToLocation = false
        input.forEach {
            if(it.trim().isEmpty()){
                isSeedToSoil = false
                isSoilToFertilizer = false
                isFertilizerToWater = false
                isWaterToLight = false
                isLightToTemperature = false
                isTemperatureToHumidity = false
                isHumidityToLocation = false
            }

            if(it.startsWith("seeds: ")){
                seeds = it.substringAfter("seeds: ")
                        .split(" ")
                        .filter {
                            it.isNotBlank()
                        }.map {
                            val id = it.trim().toLong()
                            Seed(id, id, id, id, id, id, id, id)
                        }
            }

            if(it.startsWith("seed-to-soil map")){
                isSeedToSoil = true
            } else if(isSeedToSoil){
                val (range, destination, source) = readRow(it)
                seeds.forEach {seed ->
                    if(seed.id in source..source + range - 1){
                        seed.soil = seed.id - source + destination
                    }
                }
            }

            if(it.startsWith("soil-to-fertilizer map")){
                isSoilToFertilizer = true
                seeds.forEach { seed ->
                    seed.fertilizer = seed.soil
                }
            } else if(isSoilToFertilizer){
                val (range, destination, source) = readRow(it)
                seeds.forEach {seed ->
                    if(seed.soil in source..source + range - 1){
                        seed.fertilizer = seed.soil - source + destination
                    }
                }
            }

            if(it.startsWith("fertilizer-to-water map")){
                isFertilizerToWater = true
                seeds.forEach { seed ->
                    seed.water = seed.fertilizer
                }
            } else if(isFertilizerToWater){
                val (range, destination, source) = readRow(it)
                seeds.forEach {seed ->
                    if(seed.fertilizer in source..source + range - 1){
                        seed.water = seed.fertilizer - source + destination
                    }
                }
            }

            if(it.startsWith("water-to-light map")){
                isWaterToLight = true
                seeds.forEach { seed ->
                    seed.light = seed.water
                }
            } else if(isWaterToLight){
                val (range, destination, source) = readRow(it)
                seeds.forEach {seed ->
                    if(seed.water in source..source + range - 1){
                        seed.light = seed.water - source + destination
                    }
                }
            }

            if(it.startsWith("light-to-temperature")){
                isLightToTemperature = true
                seeds.forEach { seed ->
                    seed.temperature = seed.light
                }
            } else if(isLightToTemperature){
                val (range, destination, source) = readRow(it)
                seeds.forEach {seed ->
                    if(seed.light in source..source + range - 1){
                        seed.temperature = seed.light - source + destination
                    }
                }
            }

            if(it.startsWith("temperature-to-humidity")){
                isTemperatureToHumidity = true
                seeds.forEach { seed ->
                    seed.humidity = seed.temperature
                }
            } else if(isTemperatureToHumidity){
                val (range, destination, source) = readRow(it)
                seeds.forEach {seed ->
                    if(seed.temperature in source..source + range - 1){
                        seed.humidity = seed.temperature - source + destination
                    }
                }
            }

            if(it.startsWith("humidity-to-location")){
                isHumidityToLocation = true
                seeds.forEach { seed ->
                    seed.location = seed.humidity
                }
            } else if(isHumidityToLocation){
                val (range, destination, source) = readRow(it)
                seeds.forEach {seed ->
                    if(seed.humidity in source..source + range - 1){
                        seed.location = seed.humidity - source + destination
                    }
                }
            }
        }
//        seeds.forEach { it.println() }
        return seeds.minOf { it.location }
    }

    fun calculate(value: Long, sts: MutableList<Triple<Long, Long, Long>>): Long {
        sts.forEach {
            if (value in it.third..it.third + it.first - 1) {
                return value - it.third + it.second
            }
        }
        return value
    }

    fun part2(input: List<String>): Long {
        val sts = mutableListOf<Triple<Long, Long,Long>>()
        val stf = mutableListOf<Triple<Long, Long,Long>>()
        val ftw = mutableListOf<Triple<Long, Long,Long>>()
        val wtl = mutableListOf<Triple<Long, Long,Long>>()
        val ltt = mutableListOf<Triple<Long, Long,Long>>()
        val tth = mutableListOf<Triple<Long, Long,Long>>()
        val htl = mutableListOf<Triple<Long, Long,Long>>()
        
        var isSeedToSoil = false
        var isSoilToFertilizer = false
        var isFertilizerToWater = false
        var isWaterToLight = false
        var isLightToTemperature = false
        var isTemperatureToHumidity = false
        var isHumidityToLocation = false
        input.forEach {
            if(it.trim().isEmpty()){
                isSeedToSoil = false
                isSoilToFertilizer = false
                isFertilizerToWater = false
                isWaterToLight = false
                isLightToTemperature = false
                isTemperatureToHumidity = false
                isHumidityToLocation = false
            }

            if(it.startsWith("seed-to-soil map")){
                isSeedToSoil = true
            } else if(isSeedToSoil){
                sts.add (readRow(it))
            }

            if(it.startsWith("soil-to-fertilizer map")){
                isSoilToFertilizer = true
            } else if(isSoilToFertilizer){
                stf.add (readRow(it))
            }

            if(it.startsWith("fertilizer-to-water map")){
                isFertilizerToWater = true
            } else if(isFertilizerToWater){
                ftw.add (readRow(it))
            }

            if(it.startsWith("water-to-light map")){
                isWaterToLight = true
            } else if(isWaterToLight){
                wtl.add (readRow(it))
            }

            if(it.startsWith("light-to-temperature")){
                isLightToTemperature = true
            } else if(isLightToTemperature){
                ltt.add (readRow(it))
            }

            if(it.startsWith("temperature-to-humidity")){
                isTemperatureToHumidity = true
            } else if(isTemperatureToHumidity){
                val (range, destination, source) = readRow(it)
                tth.add (readRow(it))
            }

            if(it.startsWith("humidity-to-location")){
                isHumidityToLocation = true
            } else if(isHumidityToLocation){
                htl.add (readRow(it))
            }
        }
        var minLocation = -1L
        input.get(0).substringAfter("seeds: ")
                .split(" ")
                .filter {
                    it.isNotBlank()
                }
                .zipWithNext()
                .withIndex()
                .filter { (index, _) ->
                    index % 2 == 0
                }
                .map { it.value }
                .forEach {
                    val id = it.first.trim().toLong()
                    val seek = it.second.trim().toLong()
                    val list = mutableListOf<Seed>()
                    for(i in id..id + seek - 1) {
                        val soil = calculate(i, sts)
                        val fertilizer = calculate(soil, stf)
                        val water = calculate(fertilizer, ftw)
                        val light = calculate(water, wtl)
                        val temperature = calculate(light, ltt)
                        val humidity = calculate(temperature, tth)
                        val location = calculate(humidity, htl)
                        if(minLocation > location || minLocation == -1L){
                            minLocation = location
                        }
                    }
                }
        return minLocation
    }

    val testInput = readInput("Day05_test")
    val input = readInput("Day05")

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

data class Seed(
        val id: Long,
        var soil: Long,
        var fertilizer: Long,
        var water: Long,
        var light: Long,
        var temperature: Long,
        var humidity: Long,
        var location: Long,
) {

}
