import java.io.File

fun area(in1: Int): Int {
	val width = 50
	val heigth = 50

	var adjacent: String = ""
	var newArea: String = ""

	var area: String = ""
	var areaInit: String = ""
	var timeInit = 0
	var frequency = 0

	var timeMax: Int = 10
	File("day1818_puzzle_input.txt").forEachLine {
		areaInit = areaInit + it
	}
	area = areaInit


	if (in1 == 1) {
		timeMax = 10
	} else if (in1 == 2) {
		timeMax = 1000000000
	}

	var time = 1
	while (time <= timeMax) {

		for (y in 0..heigth - 1) {
			for (x in 0..width - 1) {

				// create sting of adjacent fields
				for (j in maxOf(0, y - 1)..minOf(heigth - 1, y + 1)) {
					for (i in maxOf(0, x - 1)..minOf(width - 1, x + 1)) {
						if (!(i == x && j == y)) {
							adjacent = adjacent + area[i + width * j]
						}
					}
				}

				// check rule for current tile
				when (area[x + width * y]) {
					('.') -> {
						if (adjacent.filter { it == '|' }.count() >= 3) {
							newArea = newArea + "|"
						} else {
							newArea = newArea + "."
						}
					}
					('|') -> {
						if (adjacent.filter { it == '#' }.count() >= 3) {
							newArea = newArea + "#"
						} else {
							newArea = newArea + "|"
						}
					}
					('#') -> {
						if (adjacent.contains("|") && adjacent.contains("#")) {
							newArea = newArea + "#"
						} else {
							newArea = newArea + "."
						}
					}
				}
				adjacent = ""
			}
		}

		if (in1 == 2 && newArea == areaInit) {
			frequency = time -timeInit
			timeInit = time

			while (time < timeMax) {
				time += frequency
			}
		}
		
		if (time == 1000) {
			areaInit = newArea
			timeInit = time
		}

		area = newArea
		newArea = ""
		time += 1
	}

	return area.filter { it == '|' }.count() * area.filter { it == '#' }.count()
}

fun main(args: Array<String>) {
	var t1 = System.currentTimeMillis()

	var solution1: Int
	var solution2: Int

	solution1 = area(1)
	solution2 = area(2)

// tag::output[]
// print solution for part 1
	println("******************************************")
	println("--- Day 18: Settlers of The North Pole ---")
	println("******************************************")
	println("Solution for part1")
	println("   $solution1 is the total resource value of the lumber collection area be after 10 minutes")
	println()
// print solution for part 2
	println("*******************************")
	println("Solution for part2")
	println("   $solution2 is the total resource value of the lumber collection area be after 1000000000 minutes")
	println()
// end::output[]

	t1 = System.currentTimeMillis() - t1
	println("puzzle solved in ${t1} ms")
}
