import java.io.File

fun main(args: Array<String>) {
    var circle = mutableListOf<Int>(0)
    var current_index: Int = 0
    var current_player: Int = 1
    val players: Int = 491
    val last_marble: Int = 7105800
    var player_score = mutableMapOf<Int, Long>()
    
    for (k in 1 .. players){
        player_score.put(k, 0)
    }
    
    //println(player_score)
    //println(circle)
    
    for(i in 1..last_marble){
        if (i%1000 == 0) {println(i)}
 
        if (i%23 < 0 || i%23 > 0) {
            // normal turn
            if (current_index +2 > circle.size) {
                current_index = current_index+2-circle.size
            } else {
                current_index = current_index+2
            }
            circle.add(current_index,i)
 
        } else {
            // special turn
            player_score.put(current_player, player_score.getValue(current_player)+i)
             if (current_index -7 < 0) {
                player_score.put(current_player, player_score.getValue(current_player)+circle[circle.size+(current_index-7)])
                circle.removeAt(circle.size+(current_index-7)) 
                current_index = circle.size+1+(current_index-7)  // make shure not to set current_index onto not existing
                if (current_index == circle.size) {current_index = 0}
            } else {
                player_score.put(current_player, player_score.getValue(current_player)+circle[current_index-7])
                circle.removeAt(current_index-7)
                current_index = current_index-7 // make shure not to set current_index onto not existing
                if (current_index == circle.size) {current_index = 0}
            }            
             
            
        }
//        println("[$current_player] $circle")
        current_player++
        if (current_player > players) { current_player = 1}
    }
        //println(player_score)
        //println(current_index)
        println(player_score.maxBy { it.value })
}
