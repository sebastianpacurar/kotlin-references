package functions

/*
 High-Order functions - functions that either take functions as params, return functions or do both:

  - forEach: similar to for loop, but by using lambda; iterates over values, no index
  - forEachIndexed: similar to forEach, but using index along with current value
  - filter: filter our desired elements from a Collection
  - map: perform operations, modify elements
  - reduce: takes a binary operation (function which takes 2 params and returns a result)
  - fold: similar to reduce but allows specifying an initial value
  - takeWhile: returns a new Collection containing the sequence until a condition is met
  - flatmap: use map to traverse elements through a function then perform flatten()
  - maxBy: //TODO
  - maxByOrNull: return the max value of a Collection based on a specific selector
  - minBy: //TODO
  - minByOrNull: return the min value of a Collection based on a specific selector
  - first: //TODO
  - firstOrNull: returns the first element of an indexed collection, or null if element not found
  - last: //TODO
  - lastOrNull: returns the last element of the indexed collection, or null if element not found
  - groupBy: groups elements of a collection by a specified key or criteria
  - groupingBy: returns a Grouping object, on which aggregation functions can be performed
  - sortedBy: //TODO
  - associate: //TODO
  - associateBy: //TODO
  - associateWith: //TODO
  - distinctBy: //TODO
  - sumBy: //TODO
  - partition: //TODO
  - indexOfFirst: //TODO

*/


fun main() {
    println("ForEach and ForEachIndexed examples")
    forEachLoops()

    println("\nMap and Filter examples")
    mapAndFilter()

    println("\nReduce and Fold examples")
    reduceAndFoldFuncs()

    println("\nTakeWhile examples")
    takeWhileFunc()

    println("\nFlatMap example")
    flatMapFunc()

    println("\nMinByOrNull and MaxByOrNull Funcs examples")
    minByMaxByOrNull()

    println("\nFirstOrNull and LastOrNull examples")
    firstLastOrNull()

    println("\nGroupBy example")
    groupByFunc()

    println("\nGroupingBy example")
    groupingByFunc()
}

fun forEachLoops() {
    val chars: List<Char> = listOf('a', 'b', 'c', 'd', 'e')
    println("Initial data: $chars")

    var forEachString = ""
    val forEachIndexedMap: MutableMap<Int, Char> = mutableMapOf()

    chars.forEach { forEachString += it }
    chars.forEachIndexed { index, v -> forEachIndexedMap[index + 1] = v }

    println("Adding chars together in a string using 'forEach': $forEachString")
    println("Mapping chars sequentially from 1 to 5 using 'forEachIndexed': $forEachIndexedMap")
}


fun mapAndFilter() {
    val nums: List<Int> = listOf(1, 2, 3, 4, 5, 6, 23, 90)
    println("Initial data: $nums")

    val smallNums: List<Int> = nums.filter { it < 10 }
    val squaredNums: List<Int> = nums.map { it * it }
    val smallSquaredNums: List<Int> = nums
        .filter { it < 10 }
        .map { it * it }

    println("Nums smaller than 10: $smallNums")
    println("Squared Nums: $squaredNums")
    println("Nums smaller than 10 and squared afterwards: $smallSquaredNums")
}


fun reduceAndFoldFuncs() {
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


fun takeWhileFunc() {
    val nums: List<Int> = listOf(1, 2, 3, 4, 5)
    println("Initial data: $nums")

    val getBasedOnCondition: List<Int> = nums.takeWhile { it % 3 != 0 }

    println("all nums until any number is divisible by 3: $getBasedOnCondition")
}


fun flatMapFunc() {
    val nestedList = listOf(listOf(1, 2), listOf(3, 4), listOf(5, 6))
    println("Initial data: $nestedList")

    val flattenAndMap = nestedList.flatMap { list -> list.map { elem -> elem * 2 } }

    println("Map elements as squared, then flatten using 'flatMap': $flattenAndMap")
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

    println("Get most expensive product using 'maxByOrNull': $mostExpensiveProduct")
    println("Get cheapest product using 'minByOrNull': $mostCheapProduct")
    println("Attempt to return user from an empty users list: $checkNull")
}


fun firstLastOrNull() {
    val oddNums: List<Int> = listOf(1, 3, 5, 7, 9)
    println("Initial data: $oddNums")

    val firstOddNum: Int? = oddNums.firstOrNull { it % 2 == 1 }
    val firstEvenNum: Int? = oddNums.firstOrNull { it % 2 == 0 }

    val lastOddNum: Int? = oddNums.lastOrNull { it % 2 == 1 }
    val lastEvenNum: Int? = oddNums.lastOrNull { it % 2 == 0 }

    println("First odd num from collection: $firstOddNum")
    println("First even num from collection: $firstEvenNum")
    println("Last odd num from collection: $lastOddNum")
    println("Last even num from collection: $lastEvenNum")
}


fun groupByFunc() {
    data class User(val name: String, val age: Int, val country: String)

    val users: List<User> = listOf(
        User(name = "Dave", age = 28, country = "USA"),
        User(name = "Jenny", age = 17, country = "USA"),
        User(name = "Saxena", age = 28, country = "India"),
        User(name = "Sam", age = 42, country = "USA"),
        User(name = "Steve", age = 28, country = "UK"),
        User(name = "Dan", age = 17, country = "USA"),
        User(name = "Laxmi", age = 42, country = "India")
    )
    println("Initial data:")
    users.forEach { println(" $it") } // print initial data, one entry per line

    val groupByCountry: Map<String, List<User>> = users.groupBy { it.country }
    val groupByAge: Map<Int, List<User>> = users.groupBy { it.age }
    val groupByFirstLetter: Map<Char, List<User>> = users.groupBy { it.name[0] }

    // using transformations
    val groupByMajorVsMinor: Map<String, List<User>> = users.groupBy(
        // sets the key of the map to either "Major or Minor"
        keySelector = {
            if (it.age > 18) "Major" else "Minor"
        },
        valueTransform = { it }
    )

    // it will end up in 3 lines, since there are 3 countries (UK, USA, India)
    println("Users mapped by Country:")
    groupByCountry.forEach { println(" ${it.key} -> ${it.value}") }

    // it will also end up in 3 lines, since there are 3 age values (28, 17, 42)
    println("Users mapped by Age")
    groupByAge.forEach { println(" ${it.key} -> ${it.value}") }

    // it will end up in 4 lines, since there are 4 different letters (D, J, S, L)
    println("Users mapped by First Letter of the name using 'groupBy':")
    groupByFirstLetter.forEach { println(" ${it.key} -> ${it.value}") }

    // filter who is Major and who is Minor
    println("Users mapped by age, if age < 18, they are Minor, else Major using 'groupBy' with transformation:")
    groupByMajorVsMinor.forEach { println(" ${it.key} -> ${it.value}") }
}


fun groupingByFunc() {
    data class Product(val name: String, val category: String, val price: Double)

    val products: List<Product> = listOf(
        Product(name = "Pear", category = "Fruits", price = 2.50),
        Product(name = "Laptop", category = "Electronics", price = 1999.90),
        Product(name = "Beer", category = "Beverages", price = 2.50),
        Product(name = "Phone", category = "Electronics", price = 999.50),
        Product(name = "Apple", category = "Fruits", price = 1.75),
        Product(name = "Juice", category = "Beverages", price = 2.50),
        Product(name = "Coffee", category = "Beverages", price = 3.30)
    )
    println("Initial data:")
    products.forEach { println(" $it") } // print initial data, one entry per line

    val countByCategory: Map<String, Int> = products.groupingBy { it.category }.eachCount()
    val totalPricePerCategory: Map<String, Double> = products
        .groupingBy { it.category }
        .fold(0.0) { acc, i -> acc + i.price }

    println("Count of categories using 'groupingBy' with 'fold':")
    countByCategory.forEach { println(" ${it.key} -> ${it.value}") }

    println("Total Price per category using 'groupingBy':")
    totalPricePerCategory.forEach { println(" ${it.key} -> ${it.value}") }
}
