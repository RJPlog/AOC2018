import java.io.File
import kotlin.math.*

fun day1806(in1: Int): Int {
	var xmin = 137
	var xmax = 137
	var ymin = 282
	var ymax = 282
	var coordinates = mutableMapOf<Int, Pair<Int, Int>>()
	var coordinatesCount = mutableMapOf<Int, Int>()
	var coordinatesCount2 = mutableMapOf<Int, Int>()
	var count = 1
	File("day1806_puzzle_input.txt").forEachLine {
		var pair = it.split(", ").map { it.toInt() }
		if (pair[0] < xmin) xmin = pair[0]
		if (pair[0] > xmax) xmax = pair[0]
		if (pair[1] < ymin) ymin = pair[1]
		if (pair[1] > ymax) ymax = pair[1]
		coordinates.put(count, Pair(pair[0], pair[1]))
		coordinatesCount.put(count, 0)
		coordinatesCount2.put(count, 0)
		count += 1
	}

	var debugcount = 0
	var resultPart2 = 0
	for (y in ymin - 149..ymax + 149) {
		for (x in xmin - 149..xmax + 149) {
			var distList = mutableListOf<Pair<Int, Int>>()
			for ((key, value) in coordinates) {
				var dist = abs(y - value.second) + abs(x - value.first)
				distList.add(Pair(dist, key))
			}
			if (in1 == 1) {
				distList.sortBy { it.first }
				if (distList[0].first != distList[1].first) {
					coordinatesCount.put(distList[0].second, coordinatesCount.getValue(distList[0].second) + 1)
					debugcount += 1
				}
			} else {
			  if (distList.map {it.first}.sum() < 10000) {
				  resultPart2 += 1
					}
			}
		}
	}
	
	if (in1 == 2) {
		return resultPart2
	}

	for (y in ymin - 1..ymax + 1) {
		for (x in xmin - 1..xmax + 1) {
			var distList = mutableListOf<Pair<Int, Int>>()
			for ((key, value) in coordinates) {
				var dist = abs(y - value.second) + abs(x - value.first)
				distList.add(Pair(dist, key))
			}

			if (in1 == 1) {
				distList.sortBy { it.first }
				if (distList[0].first != distList[1].first) {
					coordinatesCount2.put(distList[0].second, coordinatesCount2.getValue(distList[0].second) + 1)
				}
			}
		}
	}

	var largestArea = 0
	for ((key, value) in coordinatesCount) {
		if (value == coordinatesCount2.getValue(key)) {
			if (value > largestArea) {
				largestArea = value
			}
		} else {
		}
	}
	return largestArea
}

fun main() {
	var t1 = System.currentTimeMillis()

	var solution1 = day1806(1)  
	var solution2 = day1806(2)

// print solution for part 1
	println("*******************************")
	println("--- Day 6: Chronal Coordinates ---")
	println("*******************************")
	println("Solution for part1")
	println("   $solution1 ")
	println()
// print solution for part 2
	println("*******************************")
	println("Solution for part2")
	println("   $solution2 ")
	println()

	t1 = System.currentTimeMillis() - t1
	println("puzzle solved in ${t1} ms")
}
