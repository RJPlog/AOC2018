import java.io.File
import kotlin.math.*

fun main(args: Array<String>) {
// ****************************************************************************	
// *     Declaration Variablen
// ****************************************************************************


	var solution1: Int = 0
	var solution2: Int = 0
	val puzzle_input = mutableListOf<String>()
	val guards = mutableMapOf<Int, String>()
	var minutes: String = "0"
	var minute_new: String = "0"
	var guard: Int = 0
	var start: Int = 0
	var end: Int = 0


// tag::prepare_minutes[]
	for (i in 1..59) {
		minutes = minutes + "=0"
	}
// end::prepare_minutes[]	


// tag::read_puzzle[]	
	File("day1804_puzzle_input.txt").forEachLine {
		puzzle_input.add(it)
	}
// end::read_puzzle[]

// tag::sort_input[]	
	puzzle_input.sort()
// end::sort_input[]	

// tag::interpret_input[]
	puzzle_input.forEach {
		// zerlege it " " --> if 3 elemnts  = guard, setze Guard aofu
		var instruction = it.split(" ")
		if (instruction[2] == "Guard") {
			guard = instruction[3].drop(1).toInt()
			if (!guards.contains(guard)) {
				guards.put(guard, minutes)
			}
		} else if (instruction[2] == "falls") {        // wenn falls asleep, setze Start Schleife
			start = instruction[1].drop(3).dropLast(1).toInt()
		} else if (instruction[2] == "wakes") {            // wenn wakes up, setze End Schleife
			end = instruction[1].drop(3).dropLast(1).toInt()
			minutes = guards.getValue(guard)
			var minute = minutes.split("=")
			minute_new = ""
			for (j in 0..59) {                // gehe minutes von StartSchleife bis Endschliefe durch und erhöhe um eins
				if (j < start || j >= end) {
					minute_new = minute_new + "=" + minute[j]
				} else if (j >= start && j < end) {
					minute_new = minute_new + "=" + (minute[j].toInt() + 1)
				}
			}
			guards.put(guard, minute_new.drop(1))            // speichere neuen Wert ab
		}
	}
// end::interpret_input[]

	guards.forEach {
		println(it)
	}
	println()

//tag::strategy_1[]
	var count1: Int = 0
	var count1_max: Int = 0
	var guard_max: Int = 0
	var solution_value: Int = 0

	guards.forEach {
		var log = it.value.split("=")
		log.forEach {
			count1 = count1 + it.toInt()
		}
		if (count1 > count1_max) {
			count1_max = count1
			solution1 = 0
			solution_value = 0
			guard_max = it.key.toInt()
			for (k in 1..log.size - 1) {
				if (log[k].toInt() > solution_value) {
					solution_value = log[k].toInt()
					solution1 = k
				}
			}
		}
		count1 = 0
	}
	println("guard_max asleep: $guard_max is $count1_max minutes asleep, most in minute $solution1")
//end::strategy_1[]


//tag::strategy_2[]
	var count2: Int = 0
	var count2_max: Int = 0
	var guard_max_2: Int = 0
	solution_value = 0
	guards.forEach {
		var log = it.value.split("=")
		for (k in 0..log.size - 1) {
			if (log[k].toInt() > solution_value) {
				solution_value = log[k].toInt()
				solution2 = k
				guard_max_2 = it.key.toInt()
			}
		}

		count1 = 0
	}
	println("guard_max asleep: $guard_max_2 is asleep solution_value $solution_value, most in minute $solution1")
//end::strategy_2[]


	// Ausgabe der Lösung für Part 1
	println()
	println("******************")
	println("Solution for part1")
	println("   ${solution1 * guard_max} is the ID of the guard you chose multiplied by the minute you chose")
	println()


	// Ausgabe der Lösung für Part 2
	// 51401 ist falsch  --> warum sind die minutes < als 60?
	println()
	println("******************")
	println("Solution for part2")
	println("   ${solution2 * guard_max_2} is the ID of the guard you chose multiplied by the minute you chose?")
	println()

}
