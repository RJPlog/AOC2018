import java.io.File

fun parse(data:List<Int>, index: Int):Pair<Int, Int> {
    var i = index
    var result = 0
    val childNodes = data[i]
    i += 1
    val dataEntries = data[i]
    i += 1

    for (k in 0..childNodes-1) {
        parse(data, i).let{result += it.first; i = it.second}
    }
       
    for (j in 0..dataEntries-1) {
        result += data[i+j]
    }
	
    i += dataEntries
    
 return Pair(result,i)
}

fun parse2(data:List<Int>, index: Int):Pair<Int, Int> {
    var i = index
    var result = 0
    
    var values = mutableMapOf <Int, Int>()
    
    val childNodes = data[i]
    i += 1
    val dataEntries = data[i]
    i += 1
 
    for (k in 0..childNodes-1) {
        parse2(data, i).let{i = it.second; values.put(k+1,it.first)}
    }

    for (j in 0..dataEntries-1) {
        if (childNodes == 0){
			result += data[i+j]
        }
        else {
            if (values.containsKey(data[i+j])) {
                result += values.getValue(data[i+j])
            }
        }
    }
                
    i += dataEntries

return Pair(result,i)
}



fun main(args: Array<String>) {
	var t1 = System.currentTimeMillis()
	
	var data = listOf<Int>()
	
	File("C:/Users/Thomas/Desktop/input.txt").forEachLine {
		data = it.split(" ").map {it.toInt()}
	}

	var solution1 = parse(data, 0).first
	//56477 is to high!
	var solution2 = parse2(data, 0).first

// tag::output[]
// print solution for part 1
	println("*****************************")
	println("--- Day 8: Memory Maneuve ---")
	println("*****************************")
	println("Solution for part1")
	println("   $solution1 is the sum of all metadata entries")
	println()
// print solution for part 2
	println("*******************************")
	println("Solution for part2")
	println("   $solution2 is the value of the root node")
	println()
// end::output[]

	t1 = System.currentTimeMillis() - t1
	println("puzzle solved in ${t1} ms")
}
