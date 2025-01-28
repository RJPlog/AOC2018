// sudo apt-get update && sudo apt-get install kotlin
// kotlinc day1822_1_2.kt -include-runtime -d day1822_1_2.jar && java -jar day1822_1_2.jar

// 0: . = rocky  -> torch or climbing (12)
// 1: = = wet	   -> climbing or nothing (20)
// 2: |  = narrow -> torch or nothing (10)

import java.io.File
import kotlin.math.*
 
fun maze(puzzleInput: String, w: Int, h: Int, start: Int, end: Int, t: Int, part: Int): Int {
      
    // initialize all necessary variables for Dijkstra
    var Q = mutableMapOf<Pair<Int,Int>,List<Int>>()  // id -> dist, previous, prevtool (0 = neither, 1 = torch, 2 = climbing gear)
    var allNodes = mutableMapOf<Pair<Int,Int>,List<Int>>()
    var startIndex = start
    var endIndex = end

    println("puzzleInput: ${puzzleInput.length}")
    for (i in 1..puzzleInput.length-1) {
            if (puzzleInput[i] == '0') {
                Q.put(Pair(i,1), listOf(w*h*100, 0, 4))
                Q.put(Pair(i,2), listOf(w*h*100, 0, 4))            
            } else if  (puzzleInput[i] == '1') {
                Q.put(Pair(i,0), listOf(w*h*100, 0, 4))
                Q.put(Pair(i,2), listOf(w*h*100, 0, 4))            
            }  else if (puzzleInput[i] == '2') {
                Q.put(Pair(i,0), listOf(w*h*100, 0, 4))
                Q.put(Pair(i,1), listOf(w*h*100, 0, 4))            
            } 
        }
    Q.put(Pair(startIndex,t), listOf(0,0,t))
    Q.remove(Pair(endIndex,2))   
    Q.remove(Pair(endIndex,0))


    println("startIndex $startIndex, t $t, ${Q.getValue(Pair(startIndex, t))}")
    //println(Q)
    
    var j = 0
    while (!allNodes.containsKey(Pair(endIndex, t)))  {   // ends when destination is reached
        // take node with shortest distance
        var idU = Pair(0,4)
        var distU = w*h*100
        var prevU = 0
        var toolU = 4
        for ((key, value) in Q) {
            if (value[0] < distU) {
                idU = key
                distU = value[0]
                prevU = value[1]
                toolU = key.second
            }
        }
        allNodes.put(idU, listOf(distU,prevU,toolU))

        //print("$j check for $idU, ${Q.getValue(idU)}")
        Q.remove(idU)
        //println(allNodes)
        
        // for each neigbour of U
        var xU = idU.first % w
        var yU = idU.first / w

        //println("     (x,y) $xU, $yU, ")
        //if (xU +1 >= w-1) println("WARNING: w to small")
        if (yU +1 >= h-1) println("WARNING: h to small")

        // tile up 
        if (Q.containsKey(Pair((xU) + w * (yU-1),1)) && yU >0) {
            var distance = distU
            if (toolU == 1) {
                distance += 1
            } else {
                distance += 8
            } 
            if (distance < Q.getValue(Pair((xU) + w * (yU-1),1))[0]) {
                Q.put(Pair((xU) + w * (yU-1),1), listOf(distance, idU.first, toolU))
            }
        }         
        if (Q.containsKey(Pair((xU) + w * (yU-1),2)) && yU >0) {
            var distance = distU
            if (toolU == 2) {
                distance += 1
            } else {
                distance += 8
            } 
            if (distance < Q.getValue(Pair((xU) + w * (yU-1),2))[0]) {
                Q.put(Pair((xU) + w * (yU-1),2), listOf(distance, idU.first, toolU))
            }
        } 
        if (Q.containsKey(Pair((xU) + w * (yU-1),0)) && yU >0) {
            var distance = distU
            if (toolU == 0) {
                distance += 1
            } else {
                distance += 8
            } 
            if (distance < Q.getValue(Pair((xU) + w * (yU-1),0))[0]) {
                Q.put(Pair((xU) + w * (yU-1),0), listOf(distance, idU.first, toolU))
            }
        } 

        // tile right
        if (Q.containsKey(Pair((xU+1) + w * (yU),1)) && xU < w -1) {
            var distance = distU
            var region = puzzleInput[(xU+1) + w * (yU)].toString().toInt()
            if (toolU == 1) {
                distance += 1
            } else {
                distance += 8
            } 
            if (distance < Q.getValue(Pair((xU+1) + w * (yU),1))[0]) {
                Q.put(Pair((xU+1) + w * (yU),1), listOf(distance, idU.first, toolU))
            }
        }         
        if (Q.containsKey(Pair((xU+1) + w * (yU),2)) && xU < w -1) {
            var distance = distU
            var region = puzzleInput[(xU+1) + w * (yU)].toString().toInt()
            if (toolU == 2) {
                distance += 1
            } else {
                distance += 8
            } 
            if (distance < Q.getValue(Pair((xU+1) + w * (yU),2))[0]) {
                Q.put(Pair((xU+1) + w * (yU),2), listOf(distance, idU.first, toolU))
            }
        } 
        if (Q.containsKey(Pair((xU+1) + w * (yU),0)) && xU < w -1) {
            var distance = distU
            var region = puzzleInput[(xU+1) + w * (yU)].toString().toInt()
            if (toolU == 0) {
                distance += 1
            } else {
                distance += 8
            } 
            if (distance < Q.getValue(Pair((xU+1) + w * (yU),0))[0]) {
                Q.put(Pair((xU+1) + w * (yU),0), listOf(distance, idU.first, toolU))
            }
        } 

        // tile down
        if (Q.containsKey(Pair((xU) + w * (yU+1),1)) && yU < h-1) {
            var distance = distU
            var region = puzzleInput[(xU) + w * (yU+1)].toString().toInt()
            if (toolU == 1) {
                distance += 1
            } else {
                distance += 8
            } 
            if (distance < Q.getValue(Pair((xU) + w * (yU+1),1))[0]) {
                Q.put(Pair((xU) + w * (yU+1),1), listOf(distance, idU.first, toolU))
            }
        }         
        if (Q.containsKey(Pair((xU) + w * (yU+1),2)) && yU < h-1) {
            var distance = distU
            var region = puzzleInput[(xU) + w * (yU+1)].toString().toInt()
            if (toolU == 2) {
                distance += 1
            } else {
                distance += 8
            } 
            if (distance < Q.getValue(Pair((xU) + w * (yU+1),2))[0]) {
                Q.put(Pair((xU) + w * (yU+1),2), listOf(distance, idU.first, toolU))
            }
        } 
        if (Q.containsKey(Pair((xU) + w * (yU+1),0)) && yU < h-1) {
            var distance = distU
            var region = puzzleInput[(xU) + w * (yU+1)].toString().toInt()
            if (toolU == 0) {
                distance += 1
            } else {
                distance += 8
            }
            if (distance < Q.getValue(Pair((xU) + w * (yU+1),0))[0]) {
                Q.put(Pair((xU) + w * (yU+1),0), listOf(distance, idU.first, toolU))
            } 
        } 
        
        
    // tile left
    if (Q.containsKey(Pair((xU-1) + w * (yU),1)) && xU >0) {
        var distance = distU
        if (toolU == 1) {
            distance += 1
        } else {
            distance += 8
        }
        if (distance < Q.getValue(Pair((xU-1) + w * (yU),1))[0]) {
            Q.put(Pair((xU-1) + w * (yU),1), listOf(distance, idU.first, toolU))
        } 
    }         
    if (Q.containsKey(Pair((xU-1) + w * (yU),2)) && xU > 0) {
        var distance = distU
        if (toolU == 2) {
            distance += 1
        } else {
            distance += 8
        } 
        if (distance < Q.getValue(Pair((xU-1) + w * (yU),2))[0]) {
            Q.put(Pair((xU-1) + w * (yU),2), listOf(distance, idU.first, toolU))
        }
    } 
    if (Q.containsKey(Pair((xU-1) + w * (yU),0)) && xU >0) {
        var distance = distU
        if (toolU == 0) {
            distance += 1
        } else {
            distance += 8
        } 
        if (distance < Q.getValue(Pair((xU-1) + w * (yU),0))[0]) {
            Q.put(Pair((xU-1) + w * (yU),0), listOf(distance, idU.first, toolU))
        }
    } 
         
        j += 1
    }    
    
    // --------------------------------------------------------------
    // next step: Check for example, why path cannot be reconstructed
    // --------------------------------------------------------------
    
    /* 
    var currNode = Pair(endIndex, t)
    var i = 0
    println(" for debugging: current path:")
    while (currNode != Pair(0,t)) {
        i+= 1
                println("$currNode: ${allNodes.getValue(currNode)}")
                currNode = Pair(allNodes.getValue(currNode)[1],allNodes.getValue(currNode)[2])    
        }
        println("$currNode: ${allNodes.getValue(currNode)}")
    */    
    
    println()
    //println(allNodes)

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
            if (x == 0 && y == 0  || x == w-1 && y == h-1) {
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
    
    w += 40 // 20
    h += 15   // 20
    
    pIELev.clear()
    for (y in 0..h-1) {
        for (x in 0..w-1) {
            if (x == 0 && y == 0  || x == puzzleInput[1].split(",")[0].toInt() && y == puzzleInput[1].split(",")[1].toInt()) {
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
    println("endIndex: $endIndex")
    var tool = 1
        
    var solution2 = maze(pIPart2, w, h, startIndex, endIndex, tool, 2)
    println("   part2: the fewest number of minutes you can take is $solution2") // 977 to high 972 to low
    println("$w, $h")

    t1 = System.currentTimeMillis() - t1
	println("puzzle solved in ${t1} ms")
}
