// sudo apt-get update && sudo apt-get install kotlin
// kotlinc day1822_1_2.kt -include-runtime -d day1822_1_2.jar && java -jar day1822_1_2.jar

import java.io.File
import kotlin.math.*
 
fun maze(puzzleInput: String, w: Int, h: Int, start: Int, end: Int, t: Int): Int {
    // 0: . = rocky  -> torch or climbing (12)
    // 1: = = wet	   -> climbing or nothing (20)
    // 2: |  = narrow -> torch or nothing (10)
    val allowedTools = listOf("12", "20", "10")
      
    // initialize all necessary variables for Dijkstra
    var Q = mutableMapOf<Pair<Int,Int>,List<Int>>()  // id(location, tool) -> dist, previous, prevtool (0 = neither, 1 = torch, 2 = climbing gear)
    var allNodes = mutableMapOf<Pair<Int,Int>,List<Int>>()
    var startIndex = start
    var endIndex = end

    for (i in 1..puzzleInput.length-1) {
            if (puzzleInput[i] == '0') {
                Q.put(Pair(i,1), listOf(w*h*100, 0, 1))
                Q.put(Pair(i,2), listOf(w*h*100, 0, 2))            
            } else if  (puzzleInput[i] == '1') {
                Q.put(Pair(i,0), listOf(w*h*100, 0, 0))
                Q.put(Pair(i,2), listOf(w*h*100, 0, 2))            
            }  else if (puzzleInput[i] == '2') {
                Q.put(Pair(i,0), listOf(w*h*100, 0, 0))
                Q.put(Pair(i,1), listOf(w*h*100, 0, 1))            
            } 
        }
    Q.put(Pair(startIndex,t), listOf(0,0,t))
    Q.remove(Pair(endIndex,2))   
    Q.remove(Pair(endIndex,0))
    
    // start with node with lowest distance and calc all neighbors, 
    var j = 0       // not really needed, was for debbuging
    while (!allNodes.containsKey(Pair(endIndex, t)))  {   // ends when destination is reached
        // take node with shortest distance
        var idU = Pair(0,4)
        var distU = w*h*100
        var toolU = 4
        for ((key, value) in Q) {
            if (value[0] <= distU) {
                idU = key
                distU = value[0]
                toolU = key.second
            }
        }
        allNodes.put(idU, Q.getValue(idU))

        Q.remove(idU)
        var xU = idU.first % w
        var yU = idU.first / w

        // for each neighbor of U
        val neighbor = listOf(Pair(-1,0), Pair(1, 0), Pair(0, -1), Pair(0,1))
        neighbor.forEach {
            var dx = it.first
            val dy = it.second
            if (xU +dx >= 0 && xU+dx <= w-1 && yU +dy >= 0 && yU+dy <= h-1) {
            // for each tool    
            (0..2).forEach {
                var currRegion = puzzleInput[idU.first.toInt()].toString().toInt()
                var nextRegion = puzzleInput[(xU+dx) + w * (yU+dy)].toString().toInt()
                    if (allowedTools[currRegion].contains(it.toString()) && allowedTools[nextRegion].contains(it.toString())) {
                        if (Q.containsKey(Pair((xU+dx) + w * (yU+dy),it))) {
                            var distance = distU
                            if (toolU == it) {
                                distance += 1
                            } else {
                                distance += 8
                            } 
                            if (distance < Q.getValue(Pair((xU+dx) + w * (yU+dy),it))[0]) {
                                Q.put(Pair((xU+dx) + w * (yU+dy),it), listOf(distance, idU.first, toolU))
                            }
                        } 
                    }
                }
            }
        }
        j += 1
    }    
        
    // use this to determine path  / debugging:
    if (true) {
        // grenerate / print used path
        var pathContent = mutableMapOf<Pair<Int,Int>,List<Int>>()
        var currNode = Pair(endIndex, t)

        pathContent.put(currNode, allNodes.getValue(currNode))
        var i = 0
        println(" for debugging: current path:")
        while (currNode != Pair(0,t)) {
            i+= 1
                    println("$currNode: ${allNodes.getValue(currNode)}")
                    currNode = Pair(allNodes.getValue(currNode)[1],allNodes.getValue(currNode)[2]) 
                    pathContent.put(currNode, allNodes.getValue(currNode))   
            }
            println("$currNode: ${allNodes.getValue(currNode)}") 

        // visualize path    
        for (y in 0..h-1) {
            for (x in 0..w-1) {
                if (pathContent.containsKey(Pair(x + w*y, 0))) {
                    print("N")
                } else if (pathContent.containsKey(Pair(x + w*y, 1))) {
                    print("T")
                } else if (pathContent.containsKey(Pair(x + w*y, 2))) {
                    print("C")
                } else {
                    print(".")
                }
            }
            println()
        }
    }
    // use this to determine path  / debugging:

    return allNodes.getValue(Pair(endIndex,1))[0]
}

fun main() {
    var t1 = System.currentTimeMillis()
   
    println("--- Day 22: Mode Maze ---")
	
    var puzzleInput = mutableListOf("510", "10,10")
    puzzleInput = mutableListOf("5913", "8,701")

    // prepare cave plan
    val d = puzzleInput[0].toInt()
    var w = puzzleInput[1].split(",")[0].toInt() + 1
    var h = puzzleInput[1].split(",")[1].toInt() + 1
    
    var pIELev = mutableListOf<Int>()
    for (y in 0..h-1) {
        for (x in 0..w-1) {
            if ((x == 0 && y == 0)  || (x == w-1 && y == h-1)) {
                pIELev.add((0 + d) % 20183)
            } else if (y == 0 ) {
                pIELev.add(((x*16807) + d) % 20183)
            } else if (x == 0 ) {
                pIELev.add(((y*48271) + d) % 20183)
            } else {
                pIELev.add(((pIELev[(x-1)+w*y] * pIELev[x+w*(y-1)]) + d) % 20183)
            }
        }
    }
    var pI = pIELev.map {it % 3}
    
    var solution1 = pI.sum()
    println("   part1: the total risk level for the smallest rectangle is $solution1")

    // ---------------------
    //           part 2
    // ---------------------

    w += 40 // 40 for puzzleInput  // 5 for example
    h += 15 // 15 for puzzleInput // 12 for example
    
    pIELev.clear()
    for (y in 0..h-1) {
        for (x in 0..w-1) {
            if ((x == 0 && y == 0)  || (x == puzzleInput[1].split(",")[0].toInt() && y == puzzleInput[1].split(",")[1].toInt())) {
                pIELev.add((0 + d) % 20183)
            } else if (y == 0 ) {
                pIELev.add(((x*16807) + d) % 20183)
            } else if (x == 0 ) {
                pIELev.add(((y*48271) + d) % 20183)
            } else {
                pIELev.add(((pIELev[(x-1)+w*y] * pIELev[x+w*(y-1)]) + d) % 20183)
            }
        }
    }

    var pIPart2 = (pIELev.map {(it % 3)}).joinToString("")

    var startIndex = 0
    var endIndex = puzzleInput[1].split(",")[0].toInt() + w * puzzleInput[1].split(",")[1].toInt() 
    var tool = 1
        
    var solution2 = maze(pIPart2, w, h, startIndex, endIndex, tool)
    println("   part2: the fewest number of minutes you can take is $solution2") // 977 to high 972 to low

    t1 = System.currentTimeMillis() - t1
	println("puzzle solved in ${t1} ms")
}
