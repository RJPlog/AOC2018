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

    // #3 count nanobots in range of nanobot with largest range
    if (part == 1) {
        var nanoBotLargestRange = pI[idLargestRange]
        pI.forEach {
            if (abs(nanoBotLargestRange.x - it.x) + abs(nanoBotLargestRange.y - it.y) + abs(nanoBotLargestRange.z - it.z) <= nanoBotLargestRange.r) result+= 1
        }
        return result
    } 
    
    // part 2
    // #4 find clusters
    var clusters = mutableListOf<MutableList<Int>>()
    for (i in 0..pI.size-1) {
        clusters.add(mutableListOf(i))  
    }

    for (i in 0..0) {  // do while clusters are not changed any more - is this really needed?
        clusters.forEach {
            var currentCluster = it
            pI.forEach {
                var newElementFound = true
                val currCompNano = it
                if (!currentCluster.contains(it.id)) {
                    currentCluster.forEach {
                        val currClusterNano = pI[it]
                        if (abs(currCompNano.x - currClusterNano.x) + abs(currCompNano.y - currClusterNano.y) + abs(currCompNano.z - currClusterNano.z) > (currCompNano.r + currClusterNano.r)) {
                            newElementFound = false
                        }
                    }
                } else {
                    newElementFound = false
                }
                if (newElementFound) currentCluster.add(currCompNano.id)
            }
        }
    } // do while clusters are not changed any more

    // sort out dublicates and get largest Nano range
    for (i in 0..clusters.size-1) {
        clusters[i].sort()
    }
    clusters = clusters.distinct().toMutableList()

    var largestNanoRange = mutableListOf<Int>()
    clusters.forEach {
        if (it.size > largestNanoRange.size) {
            largestNanoRange.clear()
            largestNanoRange.addAll(it)
        }
    }
    println(largestNanoRange.size)

    // #5 identify shortest point to 0,0,0 in highest cluster
    // ok now, how to do this?


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
