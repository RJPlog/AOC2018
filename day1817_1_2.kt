import java.io.File

fun reservoir(in1: Int): Int {

	var puzzleInput = mutableListOf<String>()

	File("day1817_puzzle_input.txt").forEachLine {
		puzzleInput.add(it)
	}

	// setup dimensions 

	var xMin = 500
	var xMax = 500
	var width = 0
	var height = 0
	var yMin = 500

	puzzleInput.forEach {
		var first = it.split(", ")[0]
		var second = it.split(", ")[1]
		if (first.take(1) == "x") {
			if (first.drop(2).toInt() < xMin) xMin = first.drop(2).toInt()
			if (first.drop(2).toInt() > xMax) xMax = first.drop(2).toInt()
			if (second.split("..")[1].toInt() > height) height = second.split("..")[1].toInt()
			if (second.drop(2).split("..")[0].toInt() < yMin) yMin = second.drop(2).split("..")[0].toInt()
		} else {
			if (first.drop(2).toInt() > height) height = first.drop(2).toInt()
			if (first.drop(2).toInt() < yMin) yMin = first.drop(2).toInt()
			if (second.drop(2).split("..")[0].toInt() < xMin) xMin = second.drop(2).split("..")[0].toInt()
			if (second.drop(2).split("..")[1].toInt() > xMax) xMax = second.drop(2).split("..")[1].toInt()
		}
	}

	width = (xMax - xMin) + 3
	height += 1

	//setup region

	var region = ""

	repeat(height) {
		region += ".".repeat(width)
	}

	region = region.replaceRange(500 - (xMin - 1), 500 - (xMin - 1) + 1, "+")

	puzzleInput.forEach {

		var first = it.split(", ")[0]
		var second = it.split(", ")[1]

		if (first.take(1) == "x") {

			var startR = second.drop(2).split("..")[0].toInt()
			var endR = second.drop(2).split("..")[1].toInt()

			for (i in startR..endR) {
				region = region.replaceRange(
					i * width + first.drop(2).toInt() - (xMin - 1),
					i * width + first.drop(2).toInt() - (xMin - 1) + 1,
					"#"
				)
			}
		} else {
			var startR = first.drop(2).toInt() * width + second.drop(2).split("..")[0].toInt() - (xMin - 1)
			var endR = first.drop(2).toInt() * width + second.drop(2).split("..")[1].toInt() - (xMin - 1) + 1
			region = region.replaceRange(startR, endR, "#".repeat(endR - startR))
		}
	}

	var runGame = true

	while (runGame) {

		runGame = false

		// follow vertical flow

		val regex1 = """(\||\+).{${width - 1}}\.""".toRegex()

		if (regex1.containsMatchIn(region)) {
			runGame = true

			var matchResult = regex1.find(region)

			region = region.replaceRange(matchResult!!.range, region.substring(matchResult!!.range).dropLast(1) + "|")
		}

		// follow horizontal flow left

		val regex2 = """\.\|.{${width - 1}}(#|~)""".toRegex()

		if (regex2.containsMatchIn(region)) {
			runGame = true
			var matchResult = regex2.find(region)

			region = region.replaceRange(matchResult!!.range, "|" + region.substring(matchResult!!.range).drop(1))
		}

		// follow horizontal flow right

		val regex3 = """\|\..{${width - 2}}(#|~)""".toRegex()

		if (regex3.containsMatchIn(region)) {
			runGame = true
			var matchResult = regex3.find(region)
			region = region.replaceRange(matchResult!!.range, "||" + region.substring(matchResult!!.range).drop(2))
		}

		// replace filled places

		val regex4 = """#\|+#""".toRegex()

		if (regex4.containsMatchIn(region)) {
			var multipleMatch = true
			var matchResult = regex4.find(region)

			while (multipleMatch) {
				val matchResultOffset =
					matchResult!!.range.map { it + width }.first()..matchResult!!.range.map { it + width }.last()
				var regex5 = """[#~]+""".toRegex()
				if (regex5.matches(region.substring(matchResultOffset))) {
					//println("   treffer2")
					runGame = true
					region = region.replaceRange(
						matchResult!!.range,
						"#" + "~".repeat(region.substring(matchResult!!.range).length - 2) + "#"
					)
				}
				matchResult = matchResult.next()
				if (matchResult == null) {
					multipleMatch = false
				}
			}
		}
	}

	if (in1 == 1) {
		return region.count { it == '|' || it == '~' } - (yMin - 1)
	} else {
		return region.count { it == '~' }
	}
}

fun main() {

	var solution1 = reservoir(1)
	println("solution1")
	println(solution1)

	var solution2 = reservoir(2)
	println("solution2")
	println(solution2)
}
