package coroutines.builders

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.math.sqrt
import kotlin.random.Random


/*
    - CoroutineScope{} builder allows to structure the code to execute multiple coroutines concurrently while providing a structured way to handle their results
*/


fun main() = runBlocking {

    // generate random numbers list scope
    val randNums = coroutineScope {
        val deferredRandomNumbers = async { generateRandomNumbers(5) }
        deferredRandomNumbers.await()
    }
    println("Initial Data: $randNums")

    // compute second operations: use random nums list to calculate sum and product
    val (sum, product) = coroutineScope {
        val deferredSum = async { computeSum(randNums) }
        val deferredProduct = async { computeProduct(randNums) }
        Pair(deferredSum.await(), deferredProduct.await())
    }
    println("Sum: $sum, Product: $product")

    // compute third operations, use sum and product to calculate other operations
    coroutineScope {
        val deferredSquareSum = async { computeSquare(sum) }
        val deferredSqRootSum = async { computeSquareRoot(sum) }
        val deferredSquareProduct = async { computeSquare(product) }
        val deferredSqRootProduct = async { computeSquareRoot(product) }

        val squareSum = deferredSquareSum.await()
        val cubeSum = deferredSqRootSum.await()
        val squareProduct = deferredSquareProduct.await()
        val cubeProduct = deferredSqRootProduct.await()

        println("Square of sum: $squareSum, Square Root of sum: $cubeSum")
        println("Square of product: $squareProduct, Square Root of product: $cubeProduct")
    }
}


// used in first coroutineScope {}
//   generates random numbers list
suspend fun generateRandomNumbers(count: Int): List<Int> {
    delay(500)
    return List(count) { Random.nextInt(1, 10) }
}

// used in second coroutineScope {}
//   takes generatedRandomNumbers list param
suspend fun computeSum(numbers: List<Int>): Int {
    delay(500)
    return numbers.sum()
}


// used in second coroutineScope {}
//   takes generatedRandomNumbers list param
suspend fun computeProduct(numbers: List<Int>): Int {
    delay(800)
    return numbers.reduce { acc, i -> acc * i }
}


// used in third coroutineScope {}
//   takes the product or the sum as param
suspend fun computeSquare(number: Int): Int {
    delay(500)
    return number * number
}


// used in third coroutineScope {}
//   takes the product or the sum as param
suspend fun computeSquareRoot(number: Int): Double {
    delay(800)
    return sqrt(number.toDouble())
}
