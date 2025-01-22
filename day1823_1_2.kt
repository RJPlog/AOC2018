// sudo apt-get update && sudo apt-get install kotlin
// kotlinc day1823_1_2.kt -include-runtime -d day1823_1_2.jar && java -jar day1823_1_2.jar

import java.io.File
import kotlin.math.*

fun nanoBot(part: Int): Int {
    var result = 0
    var pI = mutableListOf<NanoBot>()
	
    // #1 all nanobots captured
    // #2 find nanobots with largest signal radius

    var id = 0
    var largestRange = 0
    var idLargestRange = 0
	  File("day1823_puzzle_input.txt").forEachLine {
        val x = it.substringAfter("<").substringBefore(">").split(",")[0].toInt()
        val y = it.substringAfter("<").substringBefore(">").split(",")[1].toInt()
        val z = it.substringAfter("<").substringBefore(">").split(",")[2].toInt()
        val r = it.substringAfter("r=").toInt()
        if (r > largestRange) {
            largestRange = r
            idLargestRange = id
        }
        pI.add(NanoBot(id,x,y,z,r))
        id+=1
	}

    // #3 cont nanobots in range of nanobot with largest range
    if (part == 1) {
        var nanoBotLargestRange = pI[idLargestRange]
        pI.forEach {
            if (abs(nanoBotLargestRange.x - it.x) + abs(nanoBotLargestRange.y - it.y) + abs(nanoBotLargestRange.z - it.z) <= nanoBotLargestRange.r) result+= 1
        }
        return result
    } 
    
    // part 2
    
    return result
}

fun main() {

    var t1 = System.currentTimeMillis()

    println("--- Day 23: Experimental Emergency Teleportation ---")

    var solution1 = nanoBot(1)
    println("    $solution1 nanobots are in range of its signals")
	   
    var solution2 = nanoBot(2)
    println("    $solution2 is the shortest manhattan distance between any of those points")

    t1 = System.currentTimeMillis() - t1
    println("puzzle solved in ${t1} ms")
}

data class NanoBot(
    var id: Int,
    var x: Int,
    var y: Int,
    var z: Int,
    var r: Int
)
