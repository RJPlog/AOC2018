import java.io.File

class intcom {
	var register = arrayOf(0, 0, 0, 0)
	var reg_string: String = register.joinToString("")

	fun init(reg0: Int = 0, reg1: Int = 0, reg2: Int = 0, reg3: Int = 0) {
		register[0] = reg0
		register[1] = reg1
		register[2] = reg2
		register[3] = reg3
	}

	fun read_reg() {
		println("current register:")
		register.forEach {
			print("$it  ")
		}
		println()
	}

	fun instruction(op: Int, inp1: Int, inp2: Int, out: Int) {
		when (op) {
			// addr
			9 -> register[out] = register[inp1] + register[inp2]
			// addi
			6 -> register[out] = register[inp1] + inp2
			// mulr
			8 -> register[out] = register[inp1] * register[inp2]
			// muli
			0 -> register[out] = register[inp1] * inp2
			// banr
			14 -> register[out] = register[inp1] and register[inp2]
			// bani
			11 -> register[out] = register[inp1] and inp2
			// borr
			1 -> register[out] = register[inp1] or register[inp2]
			// bori
			10 -> register[out] = register[inp1] or inp2
			// setr
			7 -> register[out] = register[inp1]
			// seti
			12 -> register[out] = inp1
			// gtir
			15 -> if (inp1 > register[inp2]) register[out] = 1 else register[out] = 0
			// gtri
			2 -> if (register[inp1] > inp2) register[out] = 1 else register[out] = 0
			// gtrr
			4 -> if (register[inp1] > register[inp2]) register[out] = 1 else register[out] = 0
			// eqir
			5 -> if (inp1 == register[inp2]) register[out] = 1 else register[out] = 0
			// eqri
			3 -> if (register[inp1] == inp2) register[out] = 1 else register[out] = 0
			// eqqrr
			13 -> if (register[inp1] == register[inp2]) register[out] = 1 else register[out] = 0
		}
		reg_string = register.joinToString()
	}
}

fun main(args: Array<String>) {
	var wrist = intcom()
	var step: Int = 1
	var before: String = "Before: [0, 0, 0, 0]"
	var instru0: Int = 0
	var instru1: Int = 0
	var instru2: Int = 0
	var instru3: Int = 0
	var input_instr: String = ""
	var match_count: Int = 1
	var table = Array(16) { Array(16) { 'X' } }

	var after: String = ""
	var exp_string: String
	var local_count: Int = 0
	var global_count: Int = 0

	File("day1816_puzzle_input_1.txt").forEachLine {
		if (step == 1) {
			before = it
			step = 2
		} else if (step == 2) {
			var instruction = it.split(" ")
			instru0 = instruction[0].toInt()
			instru1 = instruction[1].toInt()
			instru2 = instruction[2].toInt()
			instru3 = instruction[3].toInt()
			input_instr = it
			step = 3
		} else if (step == 3) {
			after = it
			step = 4
		} else if (step == 4) {
			for (i in 0..15) {
				wrist.init(
					before[9].toString().toInt(),
					before[12].toString().toInt(),
					before[15].toString().toInt(),
					before[18].toString().toInt()
				)
				wrist.instruction(
					i.toString().toInt(),
					instru1,
					instru2,
					instru3
				)
				exp_string = after.takeLast(11).dropLast(1)
				if (exp_string == wrist.reg_string) {
					local_count += 1
					if (match_count >= 0) {
						println("$match_count: match bei i: $i und instruction: $input_instr")
					}
				} else {
					table[i][instru0] = '.'
				}
			}

			if (match_count >= 0) {
				println()
				println("-- new sample --")
				println()
			}
			println("    0 1 2 3 4 5 6 7 8 9 a b c d e f")
			for (y in 0..15) {
				print("${y.toString(16)}:  ")
				for (x in 0..15) {
					print(table[x][y])
					print(" ")
				}
				println()
			}
			match_count += 1

			if (local_count >= 3) global_count += 1
			local_count = 0
			step = 1
		}
	}

	// after some paperwork with outprint of table ready for part 2

	var wrist2 = intcom()

	wrist2.init()

	File("day1816_puzzle_input_2.txt").forEachLine {
		var instruction = it.split(" ")
		println("inst: $it, -${instruction[0].toInt()}-${instruction[1].toInt()}-${instruction[2].toInt()}-${instruction[3].toInt()}")
		wrist2.instruction(
			instruction[0].toInt(),
			instruction[1].toInt(),
			instruction[2].toInt(),
			instruction[3].toInt()
		)
		wrist2.read_reg()
		println()
	}

	println("**************************************")
	println("--- Day 16: Chronal Classification ---")
	println("**************************************")
	println("Solution for part1")
	println(" $global_count samples behave like three or more opcodes")
	println()
	println("Solution for part2")
	println(" ${wrist2.register[0]} is contained in register 0 after executing the test program")
}
