import java.io.File
import kotlin.math.*

fun day25(): Int {
	var points = mutableListOf<List<Int>>()
	File("day1825_puzzle_input.txt").forEachLine {
		points.add(it.split(",").map { it.toInt() })
	}

	var result = 0

	while (points.size > 0) {
		var constellation = mutableListOf<List<Int>>()
		constellation.add(points[0])
		points.removeAt(0)

		var constellationIterator = constellation.listIterator()

		while (constellationIterator.hasNext()) {
			var pointConst = constellationIterator.next()
			var pointsIterator = points.listIterator()
			
			while (pointsIterator.hasNext()) {
				val point = pointsIterator.next()
				var dist =
					abs(pointConst[0] - point[0]) + abs(pointConst[1] - point[1]) + abs(pointConst[2] - point[2]) + abs(
						pointConst[3] - point[3]
					)
				if (dist <= 3) {
					constellationIterator.add(point)
					constellationIterator.previous()
					pointsIterator.remove()

				}
			}
		}
		result += 1
	}

	return result
}

fun main() {
	var t1 = System.currentTimeMillis()

	var solution1 = day25()

// print solution for part 1
	println("*******************************")
	println("--- Day 25: Four-Dimensional Adventure ---")
	println("*******************************")
	println("Solution for part1")
	println("   $solution1 ")
	println()

	t1 = System.currentTimeMillis() - t1
	println("puzzle solved in ${t1} ms")
}
