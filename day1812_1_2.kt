import java.io.File

fun read_input(): Pair<String, Map<String, Char>> {
	// read input file and return initial state and ruleset
	val list = mapOf(
		"..#.." to '.',
		"..#.#" to '.',
		"#.#.." to '.',
		".#..#" to '.',
		"#...." to '.',
		"....#" to '.',
		".#.#." to '#',
		"#.###" to '.',
		"####." to '.',
		"....." to '.',
		".#..." to '#',
		"#####" to '#',
		".####" to '.',
		"#..#." to '#',
		"#...#" to '#',
		".###." to '.',
		"###.#" to '#',
		"...##" to '#',
		"#.##." to '#',
		".#.##" to '#',
		"##.#." to '#',
		"...#." to '.',
		"..###" to '#',
		"###.." to '#',
		"##..." to '.',
		"..##." to '.',
		".##.#" to '.',
		"##.##" to '.',
		".##.." to '.',
		"##..#" to '#',
		"#.#.#" to '.',
		"#..##" to '#'
	)
	return Pair(
		"##.#..#.#..#.####.#########.#...#.#.#......##.#.#...##.....#...#...#.##.#...##...#.####.##..#.#..#.",
		list
	)
}

fun main() {
	// initialize ruleset
	var ruleset = read_input().second
	/*println("This is the set of rules:")
	ruleset.forEach { println(it) }
	println(*/

	// initialize initial state
	var tunnel = read_input().first
	println("Let's start growing:")
	println("0: $tunnel")

	// setup time loop
	var tunnel20: String = ""
	var offset20: Long = 0
	val n: Long = 132

	var offset: Long = 0
	for (i in 1..n) {
		var tunnel_new: String = ""
		//fill up tunnel each step to consider plants growing to left and right
		// switch to dynamic fill due to part2 exceeding length
		//tunnel = "...." + tunnel +"...." //fill up tunnel each step to consider plants growing to left and right
		//offset = offset-4
		if (!tunnel.take(5).contains("#")) {
			tunnel = tunnel.drop(4)
			offset = offset + 4
		}
		if (tunnel.take(5).contains("#")) {
			tunnel = "...." + tunnel
			offset = offset - 4
		}
		if (tunnel.takeLast(5).contains("#")) {
			tunnel = tunnel + "...."
		}

		for (k in 2..tunnel.length - 3) {
			// apply ruleset
			tunnel_new = tunnel_new + ruleset.getValue(tunnel.substring(k - 2, k + 3))
			//println("debug Substring:${tunnel.substring(k-2, k+3)}, rule: ${ruleset.getValue(tunnel.substring(k-2, k+3))} Tunnel_new: $tunnel_new")

		}
		tunnel = ".." + tunnel_new + ".."
    println("$offset, $i: $tunnel")


		if (i < 21 && i > 19) {
			tunnel20 = tunnel
			offset20 = offset
		}
	}
	println()

	// count pots with plants
	var pot20: Long = 0
	for (i in 0..tunnel20.length - 1) {
		if (tunnel20[i].equals('#')) {
			pot20 = pot20 + (i + offset20)
		}
	}

	// line is starting to repeat after only a few runs (~113). offset can be calculated out of n- (n mod 4) -81. Thanks to excel :-)
	// guess this could have recognised by algorithm, but after here I did not continue :-)
	
	var pot: Long = 0
	offset = 49999999916
	for (i in 0..tunnel.length - 1) {
		if (tunnel[i].equals('#')) {
			pot = pot + (i + offset)
		}
	}

	println("*******************************************")
	println("--- Day 12: Subterranean Sustainability ---")
	println("*******************************************")
	println("Solution for part1")
	println("After 20 generations, the sum of all pots which contain a plant is $pot20")
	println()
	println("Solution for part2")
	println("After 50000000000 generations, the sum of all pots which contain a plant is $pot")
}
