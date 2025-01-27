// sudo apt-get update && sudo apt-get install kotlin
// kotlinc day1822_1_2.kt -include-runtime -d day1822_1_2.jar && java -jar day1822_1_2.jar

import java.io.File



fun main() {
    var t1 = System.currentTimeMillis()
   
    println("--- Day 22: Mode Maze ---")
	
    var puzzleInput = mutableListOf("510", "10,10")
    // puzzleInput = mutableListOf("XXXX", "XXXX")

    // prepare cave plan
    val d = puzzleInput[0].toInt()
    val w = puzzleInput[1].split(",")[0].toInt() + 1
    val h = puzzleInput[1].split(",")[1].toInt() + 1
    
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

    /*
    pI.chunked(w).forEach {
        it.forEach {
            when(it) {
                0 -> print(".")
                1 -> print("=")
                2 -> print("|")
            }
        }
        println()
    }
    */
    
    var solution1 = pI.sum()
    println("   part1: the total risk level for the smallest rectangle is $solution1")

    t1 = System.currentTimeMillis() - t1
	println("puzzle solved in ${t1} ms")
}
