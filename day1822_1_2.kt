// sudo apt-get update && sudo apt-get install kotlin
// kotlinc day1822_1_2.kt -include-runtime -d day1822_1_2.jar && java -jar day1822_1_2.jar


// 0: . = rocky  -> torch or climbing (12)
// 1: = = wet	   -> climbing or nothing (20)
// 2: |  = narrow -> torch or nothing (10)


// since I do not decide on which tool I equip, it goes straigt trough some regions because both could be true
// need to find a way to calculate way with both tools!!! node and node*?

import java.io.File
 
fun maze(puzzleInput: String, w: Int, h: Int, start: Int, end: Int, t: Int, part: Int): Int {
      
    // initialize all necessary variables for Dijkstra
    var Q = mutableMapOf<Int,List<Int>>()  // id -> dist, previous, tool (0 = neither, 1 = torch, 2 = climbing gear)
    var allNodes = mutableMapOf<Int,List<Int>>()
    var startIndex = start
    var endIndex = end

    for (i in 0..puzzleInput.length-1) {
            var node = listOf(w*h*1000000, 0, 0 )
            Q.put(i, node)
        }
    Q.put(startIndex, listOf(0,0,t))
    
    var j = 0
    while (!allNodes.contains(endIndex)) {   // ends when destination is reached
        // take node with shortest distance
        var idU = 0
        var distU = w*h*1000000
        var prevU = 0
        var toolU = 0
        for ((key, value) in Q) {
            if (value[0] < distU) {
                idU = key
                distU = value[0]
                prevU = value[1]
                toolU = value[2]
            }
        }
        allNodes.put(idU, listOf(distU,prevU,toolU))
        Q.remove(idU)


        
        // for each neigbour of U
        var xU = idU % w
        var yU = idU / w
        if (Q.containsKey((xU) + w * (yU-1)) && yU >0) {
            var distance = distU
            var region = puzzleInput[(xU) + w * (yU-1)].toString().toInt()
            var tool = 0
            if (region == 0 && (toolU.toString().contains("2") || toolU.toString().contains("1"))) {
                distance += 1
                tool = toolU
            } else if (region == 0 && (toolU.toString() == "0")) {
                distance += 8
                tool = 12
            } else if (region == 1 && (toolU.toString().contains("2") || toolU.toString().contains("0"))) {
                distance += 1
                tool = toolU
            } else if (region == 1 && (toolU.toString() == "1")) {
                distance += 8
                tool = 20
            } else if (region == 2 && (toolU.toString().contains("1") || toolU.toString().contains("0"))) {
                distance += 1
                tool = toolU
            } else if (region == 2 && (toolU.toString() == "2")) {
                distance += 8
                tool = 10
            }
            if (distance < Q.getValue((xU) + w * (yU-1))[0]) Q.put((xU) + w * (yU-1), listOf(distance, idU, tool))
        } 
        
        if (Q.containsKey((xU+1) + w * (yU)) && xU < w-1) {
            var distance = distU
            var region = puzzleInput[(xU+1) + w * (yU)].toString().toInt()
            var tool = 0
            if (region == 0 && (toolU.toString().contains("2") || toolU.toString().contains("1"))) {
                distance += 1
                tool = toolU
            } else if (region == 0 && (toolU.toString() == "0")) {
                distance += 8
                tool = 12
            } else if (region == 1 && (toolU.toString().contains("2") || toolU.toString().contains("0"))) {
                distance += 1
                tool = toolU
            } else if (region == 1 && (toolU.toString() == "1")) {
                distance += 8
                tool = 20
            } else if (region == 2 && (toolU.toString().contains("1") || toolU.toString().contains("0"))) {
                distance += 1
                tool = toolU
            } else if (region == 2 && (toolU.toString() == "2")) {
                distance += 8
                tool = 10
            }
            if (distance < Q.getValue((xU+1) + w * (yU))[0]) Q.put((xU+1) + w * (yU), listOf(distance, idU, tool))
        }
         
        if (Q.containsKey((xU) + w * (yU+1)) && yU < h-1) {
            var distance = distU
            var region = puzzleInput[(xU) + w * (yU+1)].toString().toInt()
            var tool = 0
            if (region == 0 && (toolU.toString().contains("2") || toolU.toString().contains("1"))) {
                distance += 1
                tool = toolU
            } else if (region == 0 && (toolU.toString() == "0")) {
                distance += 8
                tool = 12
            } else if (region == 1 && (toolU.toString().contains("2") || toolU.toString().contains("0"))) {
                distance += 1
                tool = toolU
            } else if (region == 1 && (toolU.toString() == "1")) {
                distance += 8
                tool = 20
            } else if (region == 2 && (toolU.toString().contains("1") || toolU.toString().contains("0"))) {
                distance += 1
                tool = toolU
            } else if (region == 2 && (toolU.toString() == "2")) {
                distance += 8
                tool = 10
            }
            if (distance < Q.getValue((xU) + w * (yU+1))[0]) Q.put((xU) + w * (yU+1), listOf(distance, idU, tool))
        }
        
        if (Q.containsKey((xU-1) + w * (yU)) && xU > 0) {
            var distance = distU
            var region = puzzleInput[(xU-1) + w * (yU)].toString().toInt()
            var tool = 0
            if (region == 0 && (toolU.toString().contains("2") || toolU.toString().contains("1"))) {
                distance += 1
                tool = toolU
            } else if (region == 0 && (toolU.toString() == "0")) {
                distance += 8
                tool = 12
            } else if (region == 1 && (toolU.toString().contains("2") || toolU.toString().contains("0"))) {
                distance += 1
                tool = toolU
            } else if (region == 1 && (toolU.toString() == "1")) {
                distance += 8
                tool = 20
            } else if (region == 2 && (toolU.toString().contains("1") || toolU.toString().contains("0"))) {
                distance += 1
                tool = toolU
            } else if (region == 2 && (toolU.toString() == "2")) {
                distance += 8
                tool = 10
            }
            if (distance < Q.getValue((xU-1) + w * (yU))[0]) Q.put((xU-1) + w * (yU), listOf(distance, idU, tool))
        }
         

    //println()
    //println("Q: $Q")
    //println("allNodes: $allNodes")
        j += 1
    }    
    


    var printOut = puzzleInput
    for ((key,value) in allNodes) {
        printOut = printOut.replaceRange(key, key+1, "4")
    }
    
    /*
    println("allNodes: $allNodes")
    printOut.chunked(w).forEach {
        it.forEach {
            when(it) {
                '0' -> print(".")
                '1' -> print("=")
                '2' -> print("|")
                '4' -> print("x")
            }
        }
        println()
    }
    */

    var currNode = endIndex
    var i = 0
    println(" for debugging: current path:")
    while (currNode != 0) {
        i+= 1
                println("$currNode: ${allNodes.getValue(currNode)}")
                currNode = allNodes.getValue(currNode)[1]  
        }
        println("$currNode: ${allNodes.getValue(currNode)}")

    return allNodes.getValue(endIndex)[0]
}

fun main() {
    var t1 = System.currentTimeMillis()
   
    println("--- Day 22: Mode Maze ---")
	
    var puzzleInput = mutableListOf("510", "10,10")
    // puzzleInput = mutableListOf("XXXX", "XXXX")

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

    // part 2
    w = 16
    h = 16
    
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
    println(endIndex)
    var tool = 1
        
    var solution2 = maze(pIPart2, w, h, startIndex, endIndex, tool, 2)
    println("   part2: the fewest number of minutes you can take is $solution2")


    t1 = System.currentTimeMillis() - t1
	println("puzzle solved in ${t1} ms")
}
