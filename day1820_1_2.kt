import java.io.File

fun ExPolRegex(in1: String): Int {
	// define pattern for smallest bracket
	val pattern = """\((N|W|S|E)+(\|(N|W|S|E)*)+\)""".toRegex()
	var matchResult = ""
	var pathList = mutableListOf<String>()
	pathList.add(in1)
	var pathListNew = mutableListOf<String>()

	// extract longest path
	// this algorithm was started to extract all pathes and afterwards take the longest,
	// after performance issues it was quick and dirty modified to keep only the longest.
	var gameEnd = false

	while (!gameEnd) {
		gameEnd = true
		pathList.forEach {
			val path = it
			if (pattern.containsMatchIn(path)) {
				matchResult = pattern.find(path)!!.value
				gameEnd = false
			}

			var parts = matchResult.drop(1).dropLast(1).split("|")

			if (parts.contains("")) {
				pathListNew.add(path.replace(matchResult, ""))
			} else {
				var partsSorted = parts.sortedByDescending { it.length }
				pathListNew.add(path.replace(matchResult, partsSorted[0]))
			}
		}

		pathList.clear()
		pathList.addAll(pathListNew.distinct())
		pathListNew.clear()
	}

	var result = 0
	pathList.forEach {
		if (it.length > result) {
			result = it.length
		}
	}
	
	return result - 2
}

fun main() {
	var t1 = System.currentTimeMillis()

	val puzzle_input = File("day1820_puzzle_input.txt").readLines()

	var solution1 = ExPolRegex(puzzle_input[0])
	//var solution2 = NoSpace(2)

// tag::output[]
// print solution for part 1
	println("**************************************")
	println("--- Day 20: A Regular Map ---")
	println("**************************************")
	println("Solution for part1")
	println("   $solution1 is the largest number of doors you would be required to pass through to reach a room")
	println()
// print solution for part 2
	println("**************************************")
	println("Solution for part2")
	//println("   $solution2 ")
	println()
// end::output[]

	t1 = System.currentTimeMillis() - t1
	println("puzzle solved in ${t1} ms")
}
