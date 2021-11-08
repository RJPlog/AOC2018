import java.io.File
import kotlin.math.*

fun powerlevel(serial: Int): MutableList<Int> {
	val powerlevel_list = mutableListOf<Int>()

	for (y in 1..300) {
		for (x in 1..300) {
			var power = ((x + 10) * y + serial) * (x + 10)
			var power2 = power.toString().padStart(10, '0').takeLast(3).take(1).toInt() - 5
			powerlevel_list.add(power2)
		}
	}
	return powerlevel_list
}

fun max_result(n: Int, input: MutableList<Int>): Triple<Int, Int, Int> {
	var fuel: Int
	var fuelmax: Int = 0
	var xmax: Int = 0
	var ymax: Int = 0

	for (i in 1..300 - (n - 1)) {
		for (j in 1..300 - (n - 1)) {
			fuel = 0
			for (y in j..j + (n - 1)) {
				for (x in i..i + (n - 1)) {
					var power2 = input[(y - 1) * 300 + (x - 1)]
					fuel = fuel + power2
				}
			}
			if (fuel > fuelmax) {
				fuelmax = fuel
				xmax = i
				ymax = j
			}
		}
	}
	return Triple(xmax, ymax, fuelmax)
}

fun main(args: Array<String>) {
	var serial: Int = 5153
	var max = Triple(0, 0, 0)
	var maxk: Int = 0


	// generate list with fuel levels
	var powerlevel_list = powerlevel(serial)

	//part 1 - calculate value for k = 3
	var solution1 = max_result(3, powerlevel_list)

	// print solution for part 1
	println("******************************")
	println("--- Day 11: Chronal Charge ---")
	println("******************************")
	println("Solution for part1")
	println("The answer for part1 is: ${solution1.first},${solution1.second} (@ ${solution1.third}) ")
	println()

	// part 2 - generate max result for specific k
	for (n in 1..300) {
		var solution = max_result(n, powerlevel_list)
		//println("$n: Solution: ${solution.first},${solution.second},$n (@ ${solution.third}) ")
		if (solution.third > max.third) {
			max = solution
			maxk = n
		}
	}
	
	// print solution for part 2
	println("****************************")
	println("Solution for part2")
	println("The answer for part2 is: ${max.first},${max.second},$maxk (@ ${max.third}) ")
}