import java.io.File
import kotlin.math.*

fun main(args: Array<String>) {
//****************************************************************************	
//*     Declaration Variablen
//****************************************************************************


	var solution1: Int = 0
	var solution2: Int = 0
	var abc: String = "abcdefghijklmnopqrstuvwxyz"
	var B_xx: Boolean = false
	var B_yyy: Boolean = false


//****************************************************************************	
//*     Einlesen Puzzledaten und direkt Berechnung des Weges.
//****************************************************************************


// Part 1

	File("day1802_puzzle_input.txt").forEachLine {
		var test = it
		abc.forEach {
			if (test.length - test.replace(it.toString(), "").length == 2) {
				B_xx = true
			}
			if (test.length - test.replace(it.toString(), "").length == 3) {
				B_yyy = true
			}
		}
		if (B_xx == true) {
			solution1++
			B_xx = false
		}
		if (B_yyy == true) {
			solution2++
			B_yyy = false
		}

	}
	solution1 = solution1 * solution2

// Part 2	
	var count: Int = 0
	var solution_space = mutableListOf<String>()
	var solution_space_2 = mutableListOf<Char>()
	var checksum2: String = ""

	File("day1802_puzzle_input.txt").forEachLine {
		var test_1 = it
		File("day1802_puzzle_input.txt").forEachLine {
			var test_2 = it
			for (i in 0..test_1.length - 1) {
				if (test_1[i] != test_2[i]) {
					count++
				}
			}
			if (count == 1) {
				solution_space.add(test_1)
				solution_space.add(test_2)
			}
			count = 0
		}
	}
	
	var box_1 = solution_space[0]
    var box_2 = solution_space[1]
	for (k in 0..solution_space[0].length-1) {
		var letter1 = box_1[k]
		var letter2 = box_2[k]
		if (letter1 == letter2) {
			checksum2= checksum2 + box_1[k]
		}
	}

	// Ausgabe der Lösung für Part 1
	println()
	println("******************")
	println("Solution for part1")
	println("  $solution1 is the checksum for your list of box IDs")
	println()


	// Ausgabe der Lösung für Part 2
	println()
	println("******************")
	println("Solution for part2")
	println("    $checksum2 letters are common between the two correct box IDs?")
	println()

}
