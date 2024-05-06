package functions

/*

 Predicates - functions that can only take a condition which returns TRUE or FALSE:
  - all: do all elements satisfy the predicate/condition
  - any: do any element in list satisfy the predicate
  - none: opposite of all; returns true if no elements satisfy the condition
  - count: total elements that satisfy the predicate
  - find: returns the FIRST element that satisfy the predicate
  - takeIf: returns the receiver object if the predicate condition is true, else it returns null
  - takeUnless: returns the receiver object if the predicate condition is false, else it returns null

*/


fun main() {
    println("\nAll, None, Any, Find, Count Predicates examples")
    predicateFunctions()
}


fun predicateFunctions() {
    val nums: List<Int> = listOf(2, 3, 4, 5, 6, 7, 9)
    println("Initial data: $nums")

    val allSmallerThan10: Boolean = nums.all { it < 10 }
    println("Are all numbers smaller than 10 using 'all': $allSmallerThan10")

    val noneGreaterThan10: Boolean = nums.none { it > 10 }
    println("Is there no number greater than 10 using 'none': $noneGreaterThan10")

    val anyCanDivideBy9: Boolean = nums.any { it % 9 == 0 }
    println("Is any number divisible by 9 using 'any': $anyCanDivideBy9")

    val getFirstBiggerThan5: Int? = nums.find { it > 5 }
    println("First bigger Than 5 number using 'find': $getFirstBiggerThan5")

    val countBiggerThan5: Int = nums.count { it > 5 }
    println("Count numbers bigger than 5 using 'count': $countBiggerThan5")

    val filteredList = nums.takeIf { it.all { num -> num < 10 } }
    println("Filtered list using 'takeIf': $filteredList")

    val excludedList = nums.takeUnless { it.any { num -> num > 10 } }
    println("Excluded list using 'takeUnless': $excludedList")
}
