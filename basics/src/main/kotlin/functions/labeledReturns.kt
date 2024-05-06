package functions


/*

 labeled return - specify which function/loop to skip to

*/


fun main() {
    println("Labeled break")
    labeledBreak()

    println("\nLabeled continue")
    labeledContinue()
}


// break out of the outer loop when condition is met
fun labeledBreak() {
    outerLoop@ for (i in 1..3) {
        for (j in 1..3) {
            println(" i = $i; j = $j")
            if (i == 2 && j == 2) {
                // break out of the outerLoop
                break@outerLoop
            }
        }
    }
    println("Out of outer loop")
}


// continue from the outmost loop when k == 1
fun labeledContinue() {
    outerLoop@ for (i in 1..3) {
        for (j in 1..3) {
            for (k in 1..3) {
                println(" i = $i; j = $j; k = $k")
                if (k == 2) {
                    // continue from i when condition reached (j will always be 1; k will never go above 2)
                    continue@outerLoop
                }
            }
        }
    }
}
