import java.io.File

fun findMax(input: List<Int>): Int {
	var result: Int = input[0]
	input.forEach {
		if (it > result)
			result = it
	}
	return result
}

fun findMin(input: List<Int>): Int {
	var result: Int = input[0]
	input.forEach {
		if (it < result)
			result = it
	}
	return result
}

fun main(args: Array<String>) {
	var xpos = mutableListOf<Int>()
	var ypos = mutableListOf<Int>()
	var xvel = mutableListOf<Int>()
	var yvel = mutableListOf<Int>()
	var xposnew = mutableListOf<Int>()
	var yposnew = mutableListOf<Int>()
	var xdist: Int
	var ydist: Int
	var xdistnew: Int
	var ydistnew: Int

	File("day1810_puzzle_input.txt").forEachLine {
		var instruction = it.split("> velocity=<")
		var positions = instruction[0].split(",")
		var velocities = instruction[1].split(",")
		var x = positions[0].drop(10).replace(" ", "").toInt()
		var y = positions[1].replace(" ", "").toInt()
		var vx = velocities[0].replace(" ", "").toInt()
		var vy = velocities[1].dropLast(1).replace(" ", "").toInt()

		xpos.add(x)
		ypos.add(y)
		xvel.add(vx)
		yvel.add(vy)
	}

	var i: Int = 0
	Loop@ while (true) {


		xdist = findMax(xpos) - findMin(xpos)
		ydist = findMax(ypos) - findMin(ypos)

		for (i in 0..xpos.size - 1) {
			xposnew.add(xpos[i] + xvel[i])
			yposnew.add(ypos[i] + yvel[i])
		}

		xdistnew = findMax(xposnew) - findMin(xposnew)
		ydistnew = findMax(yposnew) - findMin(yposnew)

		if (xdistnew > xdist && ydistnew > ydist) {
			break@Loop
		}

		//println("-------  -------")
		//println("xdist: $xdist, ydist: $ydist")
		//println("xdistnew: $xdistnew, ydistnew: $ydistnew")
		//println()

		xpos.clear()
		ypos.clear()
		xposnew.forEach {
			xpos.add(it)
		}
		yposnew.forEach {
			ypos.add(it)
		}
		xposnew.clear()
		yposnew.clear()

		i += 1
	}


	//generate list of pairs out of xdistnew and ydistnew
	var stars = mutableListOf<Pair<Int, Int>>()
	for (i in 0..xpos.size - 1) {
		stars.add(Pair(xpos[i], ypos[i]))
	}

	for (y in findMin(ypos)..findMax(ypos)) {
		for (x in findMin(xpos)..findMax(xpos)) {

			if (stars.contains(Pair(x, y))) {
				print("#")
			} else {
				print(".")
			}
		}
		println()
	}
	println()

	println("*******************************")
	println("--- Day 10: The Stars Align ---")
	println("*******************************")
	println("Solution for part1")
	println(" NBRALZPH will eventually appear in the sky")
	println()
	println("Solution for part2")
	println(" $i seconds would they have needed to wait for that message to appear")
}
