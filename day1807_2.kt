import java.io.File

fun main() { 
 
    
    // alle Steps werden in eine Map transferiert und die jeweiligen Blocker und die Zeit abgespeichert. 
    var stepListUnsorted = mutableMapOf<String,Pair<MutableList<String>,Int>>()
    
	//var lines = mutableListOf("Step C must be finished before step A can begin.","Step C must be finished before step F can begin.","Step A must be finished before step B can begin.","Step A must be finished before step D can begin.","Step B must be finished before step E can begin.","Step D must be finished before step E can begin.","Step F must be finished before step E can begin.")
    var lines = File("day1807_puzzle_input.txt").readLines()
	
	lines.forEach {
        println(it)
        var currentStep: String = it.substringAfter("ore step ").take(1)//.substringBefore(" must").drop(5)
        var blockList = mutableListOf<String>(it.substringBefore(" must").drop(5))
        if (stepListUnsorted.containsKey(currentStep)) {
            blockList.addAll(stepListUnsorted.getValue(currentStep).first)
            stepListUnsorted.put(currentStep, Pair(blockList, 61+currentStep[0].toChar().toInt()-'A'.toChar().toInt()))
        } else {
            stepListUnsorted.put(currentStep, Pair(blockList, 61+currentStep[0].toChar().toInt()-'A'.toChar().toInt()))
        }
    }
    // in den Instruktionen finden wir nur die Steps, die geblockt werden, d.h. wir müssen unsere Map um die unblockierten ergänzen
    var unblockedSteps = mutableListOf<String>()
    stepListUnsorted.forEach {
        var steps = it.value.first
        steps.forEach{
            if (!stepListUnsorted.containsKey(it)) {
                unblockedSteps.add(it)
            }
        }
    }
    unblockedSteps.distinct().forEach{
        stepListUnsorted.put(it, Pair(mutableListOf<String>(),61+it[0].toChar().toInt()-'A'.toChar().toInt()))
    }
    
    // die Map muss sortiert verwendet werden
    
    var stepList = stepListUnsorted.toSortedMap()
    
    // jetzt brauchen wir eine Map mit den Workern und der Zuordnung an welchem Step diese Arbeiten
    var workers = mutableMapOf<Int,String>()
    for (i in 0..4) {
        workers.put(i, "")
    }
    
    // jetzt das Game starten, bis alle Zeiten auf Null sind
    var gameEnd = false
    var seconds = 0
    while (!gameEnd) {
    //for (m in 0..100) {
        gameEnd = false
        // gehe alle Step durch, sobald einer keinen blocker hat, gehe alle Workers durch und weise einen Worker zu
        stepList.forEach {
            if(it.value.first.size == 0) {
                println(" Treffer: ${it.key}")
                for(i in 0..workers.size-1) {
                    if (workers.getValue(i) == ""  && !workers.map{it.value}.contains(it.key)) {
                        workers.put(i,it.key)
                        break
                    }
                }
            }
        }
        // gehe alle Steps durch, bei jedem der einen Worker hat reduziere die Zeit. Wenn die Zeit Null ist, gib den Worker frei
        println("  workers: $workers")
       
        workers.forEach{
            if (it.value != "") {
                stepList.put(it.value, Pair(stepList.getValue(it.value).first, stepList.getValue(it.value).second-1))
                if (stepList.getValue(it.value).second == 0) {
                    stepList.remove(it.value)
                    var stepToRemove = it.value
                    // remove condition out of all values
                    stepList.forEach {
                        if(it.value.first.contains(stepToRemove)) {
                            var help = it.value.first
                            help.remove(stepToRemove)
                            stepList.put(it.key, Pair(help,it.value.second))
                        }
                    }
                    workers.put(it.key,"")
                }
            }
        }
       
        // überprüfe alle worker, falls die Zeit 0 ist, entferne den step aus dem Worker und entferne den Key aus der Map
        // überprüfe alle Steps, wenn eine Zeit > 0 ist, setze GameEnd auf false
         
        if (stepList.size == 0) {
            gameEnd = true
        }
        println(stepList)
        seconds += 1
    }
    
    println(" GameEnd after $seconds seconds")
    
}
