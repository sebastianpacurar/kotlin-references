package basics

/*
 High-Order functions:
  - forEach: similar to for loop, but by using lambda; iterates over values, no index
  - forEachIndexed: similar to forEach, but using index along with current value
  - filter: filter our desired elements from a Collection
  - map: perform operations, modify elements
  - reduce: takes a binary operation (function which takes 2 params and returns a result)
  - fold: similar to reduce but allows specifying an initial value
  - take: returns a new Collection containing the first n elements from the original Collection
  - takeWhile: returns a new Collection containing the sequence until a condition is met
  - takeLast: returns a new Collection containing the last n elements from the original Collection
  - flatmap: use map to traverse elements through a function then perform flatten()
  - maxByOrNull: return the max value of a Collection based on a specific selector
  - minByOrNull: return the min value of a Collection based on a specific selector
  - groupBy: //TODO
  - sortedBy: //TODO
  - associateBy: //TODO
  - distinctBy: //TODO
  - sumBy: //TODO
  - partition: //TODO

 Predicates (a condition that returns TRUE or FALSE):
  - all: do all elements satisfy the predicate/condition
  - any: do any element in list satisfy the predicate
  - none: opposite of all; returns true if no elements satisfy the condition
  - count: total elements that satisfy the predicate
  - find: returns the FIRST element that satisfy the predicate
  - firstOrNull: returns the first element of the collection, or null if collection is empty
  - lastOrNull: returns the last element of the collection, or null if collection is empty

 Standard Library Functions:
  - distinct: returns unique results
  - flatten: flatten a Collection of nested Collection
  - zip: returns a Pair of entries between 2 Collections
  - sort: //TODO
  - sorted: //TODO
  - average: //TODO
  - maxOrNull: //TODO
  - minOrNull: //TODO
*/


fun main() {
    /*
    * High Order functions
    * */
    println("ForEach and ForEachIndexed")
    forEachLoops()

    println("\nMap and Filter")
    mapAndFilter()

    println("\nReduce and Fold")
    reduceAndFold()

    println("\nTake Functions")
    takeFunctions()

    println("\nFlatMap Func")
    flatMapFunc()

    /*
    * Predicates
    *  */

    println("\nAll, None, Any, Find, Count Predicates")
    allNoneAnyFindCountPredicates()

    println("\nFirstOrNull and LastOrNull Predicates")
    firstLastOrNull()

    println("\nMinByOrNull and MaxByOrNull")
    minByMaxByOrNull()

    /*
    * Useful standard library functions
    *  */

    println("\nFlatten Func")
    flattenFunc()

    println("\nDistinct Func")
    distinctFunc()

    println("\nZip Func")
    zipFunc()
}

/*
* High Order functions
* */


fun forEachLoops() {
    val chars: List<Char> = listOf('a', 'b', 'c', 'd', 'e')
    println("Initial data: $chars")

    var forEachString = ""
    val forEachIndexedMap: MutableMap<Int, Char> = mutableMapOf()

    chars.forEach { forEachString += it }
    chars.forEachIndexed { index, v -> forEachIndexedMap[index + 1] = v }

    println("Adding chars together in a string using 'forEach' func: $forEachString")
    println("Mapping chars sequentially from 1 to 5 using 'forEachIndexed' func: $forEachIndexedMap")
}


fun mapAndFilter() {
    val nums: List<Int> = listOf(1, 2, 3, 4, 5, 6, 23, 90)
    println("Initial data: $nums")

    val smallNums: List<Int> = nums.filter { it < 10 }
    val squaredNums: List<Int> = nums.map { it * it }
    val smallSquaredNums: List<Int> = nums.filter { it < 10 }.map { it * it }

    println("Nums smaller than 10: $smallNums")
    println("Squared Nums: $squaredNums")
    println("Nums smaller than 10 and squared afterwards: $smallSquaredNums")
}


fun reduceAndFold() {
    val nums: List<Int> = listOf(0, 1, 2, 3, 4)
    println("Initial data: $nums")

    // 'acc' is the current computed value, while 'i' is the next in list
    val sum: Int = nums.reduce { acc, i -> acc + i }

    // if current 'i' value equals 0 then use the 'acc' value which has an initial value of 1, else use 'i' value
    val product: Int = nums.fold(1) { acc, i ->
        if (i != 0) acc * i else acc
    }

    println("Sum of nums is: $sum")
    println("Product of nums is $product")
}


fun takeFunctions() {
    val nums: List<Int> = listOf(1, 2, 3, 4, 5)
    println("Initial data: $nums")

    val firstThree: List<Int> = nums.take(3)
    val lastThree: List<Int> = nums.takeLast(3)
    val lastThreeReversed: List<Int> = nums.takeLast(3).reversed()
    val getBasedOnCondition: List<Int> = nums.takeWhile { it % 3 != 0 }

    println("first three using take: $firstThree")
    println("last three using takeLast: $lastThree")
    println("last three using takeLast but reversed: $lastThreeReversed")
    println("all nums until any number is divisible by 3: $getBasedOnCondition")
}


fun flatMapFunc() {
    val nestedList = listOf(listOf(1, 2), listOf(3, 4), listOf(5, 6))
    println("Initial data: $nestedList")

    val flattenAndMap = nestedList.flatMap { list -> list.map { elem -> elem * 2 } }

    println("Map elements as squared, then flatten using 'flatMap' func: $flattenAndMap")
}

fun minByMaxByOrNull() {
    data class Product(val name: String, val price: Double)
    data class User(val name: String, val age: Int)

    // used to return null value (empty list)
    val users: List<User> = emptyList()

    // used to return existing value (populated list)
    val products: List<Product> = listOf(
        Product("apple", 2.50),
        Product("pear", 3.25),
        Product("banana", 5.75)
    )

    println("Initial data: \n Products: $products \n Users: $users")

    val mostExpensiveProduct: Product? = products.maxByOrNull { it.price }
    val mostCheapProduct: Product? = products.minByOrNull { it.price }
    val checkNull: User? = users.maxByOrNull { it.age }

    println("Get most expensive product using 'maxByOrNull' func: $mostExpensiveProduct")
    println("Get cheapest product using 'minByOrNull' func: $mostCheapProduct")
    println("Attempt to return user from an empty users list: $checkNull")
}


/*
* Predicates
*  */


fun allNoneAnyFindCountPredicates() {
    val nums: List<Int> = listOf(2, 3, 4, 5, 6, 7, 9)
    println("Initial data: $nums")

    val allSmallerThan10: Boolean = nums.all { it < 10 }
    val noneGreaterThan10: Boolean = nums.none { it > 10 }
    val anyCanDivideBy9: Boolean = nums.any { it % 9 == 0 }
    val getFirstBiggerThan5: Int? = nums.find { it > 5 }
    val countBiggerThan5: Int = nums.count { it > 5 }

    println("Are all numbers smaller than 10 using 'all' func: $allSmallerThan10")
    println("Is there no number greater than 10 using 'none' func: $noneGreaterThan10")
    println("Is any number divisible by 9 using 'any' func: $anyCanDivideBy9")
    println("First bigger Than 5 number using 'find' func: $getFirstBiggerThan5")
    println("Count numbers bigger than 5 using 'count' func: $countBiggerThan5")
}

fun firstLastOrNull() {
    val oddNums = listOf(1, 3, 5, 7, 9)
    println("Initial data: $oddNums")

    val firstOddNum = oddNums.firstOrNull { it % 2 == 1 }
    val firstEvenNum = oddNums.firstOrNull { it % 2 == 0 }

    val lastOddNum = oddNums.lastOrNull { it % 2 == 1 }
    val lastEvenNum = oddNums.lastOrNull { it % 2 == 0 }

    println("First odd num from collection: $firstOddNum")
    println("First even num from collection: $firstEvenNum")
    println("Last odd num from collection: $lastOddNum")
    println("Last even num from collection: $lastEvenNum")
}

/*
* Useful standard library functions
*  */


fun flattenFunc() {
    val nestedList = listOf(listOf(1, 2), listOf(3, 4), listOf(5, 6))
    println("Initial data: $nestedList")

    val flattened = nestedList.flatten()

    println("Flattened list using 'flatten' func: $flattened")
}

fun distinctFunc() {
    val nums = listOf(1, 2, 2, 3, 3, 4, 5, 5)
    println("Initial data: $nums")

    val uniqueNums = nums.distinct()

    println("Distinct values using 'distinct' func: $uniqueNums")
}

fun zipFunc() {
    val firstList = listOf(1, 2, 3, 4, 5)
    val secondList = listOf('a', 'b', 'c', 'd', 'e')
    println("Initial data: \n nums: $firstList \n chars: $secondList")

    val pairs = firstList.zip(secondList)

    println("Zipping numbers with chars using 'zip' func: $pairs")
}

