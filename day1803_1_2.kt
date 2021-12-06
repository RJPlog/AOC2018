import java.io.File
import kotlin.math.*

fun main(args: Array<String>) {
// ****************************************************************************	
// *     Declaration Variablen
// ****************************************************************************


	var solution1: Int = 0
	var solution2: Int = 0
	var fabric = mutableMapOf<String, String>()
	var fabric_ccm = mutableMapOf<Int, Int>()

// 	Erzeugen des Grids
	for (y in 0..999) {
		for (x in 0..999) {
			fabric.put(x.toString() + "=" + y.toString(), ".")
		}
	}

// Part 1

	File("day1803_puzzle_input.txt").forEachLine {
		var instruction = it.split(" ")  // #1 @ 1,3: 4x4
		var start = instruction[2].dropLast(1).split(",")
		var range = instruction[3].split("x")

		fabric_ccm.put(instruction[0].drop(1).toInt(),range[0].toInt()*range[1].toInt())
		
		for (y in start[1].toInt()..start[1].toInt() + range[1].toInt() - 1) {
			for (x in start[0].toInt()..start[0].toInt() + range[0].toInt() - 1) {
				if (fabric.getValue(x.toString() + "=" + y.toString()) == ".") {
					fabric.put(x.toString() + "=" + y.toString(), instruction[0].drop(1))
				} else {
					fabric.put(x.toString() + "=" + y.toString(), "X")
				}
			}
		}
	}

	fabric.forEach {
		if (it.value == "X") {
			solution1++
		}
	}

	for (i in 1..1295) {
		solution2 = 0
		fabric.forEach {
			if (it.value == i.toString()) {
				solution2 ++
			}
		}
		if (solution2 == fabric_ccm.getValue(i)) {
			solution2 = i
			break
		}
	}
	

	// Ausgabe der Lösung für Part 1
	println()
	println("******************")
	println("Solution for part1")
	println("   $solution1 square inches of fabric are within two or more claims")
	println()


	// Ausgabe der Lösung für Part 2
	println()
	println("******************")
	println("Solution for part2")
	println("   $solution2 is the ID of the only claim that doesn't overlap?")
	println()

}
