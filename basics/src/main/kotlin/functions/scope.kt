package functions

/*

 Scope - functions that allow to execute a block of code within a certain context:
  - let: executes a given block of code with the object as an argument and returns the result of the block
        purpose: to perform operations on a non-null object, especially when you need to execute a block of code only if the object is not null
        result: the result of the let function is the result of the lambda expression
        usage: typically used for null-checking and executing operations on the object

  - run: executes a block of code on the object itself, returning the result of the block
        purpose: to perform multiple operations on an object and access its properties or methods within the lambda block
        result: the result of the run function is the result of the lambda expression
        usage: useful when you want to access multiple properties or methods of the object and potentially return a value

  - with: executes multiple operation on the object without the need to qualify each operation with the object's name
        purpose: to operate on properties or methods of an object without repeating the object reference
        result: it doesn't return any value, it simply executes the lambda block on the object
        usage: typically used to improve readability by avoiding repetition of the object reference

  - apply: executes a block of code on the object itself and returns the object
        purpose: to initialize or configure properties of an object and return the same object
        result: the result of the apply function is the object itself
        usage: commonly used for configuring properties of newly created objects or initializing properties based on certain conditions

  - also: similar to apply, but it does not return the object, instead it returns the original object
        purpose: to perform additional operations on an object and return the same object
        result: the result of the 'also' function is the object itself
        usage: useful for performing additional operations, such as logging or side effects, while chaining method calls


    +----------+------------------+----------------+---------------------------------------+
    | Function | Object Reference |  Return Value  |         Is Extension Function         |
    +----------+------------------+----------------+-----------------------+---------------+
    |   let    |        it        | lambda result  |                  Yes                  |
    |   run    |       this       | lambda result  |                  Yes                  |
    |   with   |       this       | lambda result  | No: called without the context object |
    |   apply  |       this       | context object | No: takes the context obj as argument |
    |   also   |        it        | context object |                  Yes                  |
    +----------+------------------+----------------+---------------------------------------+

* */


// dummy class for examples
class Car(
    private var _brand: String,
    private var _price: Double,
    private var _color: String? = null,
    private var _owner: String? = null
) {

    fun fillColor(color: String) {
        _color = color
    }

    fun setOwner(owner: String) {
        _owner = owner
    }

    override fun toString(): String = "Car(brand=$_brand, price=$_price, color=$_color, owner=$_owner)"
}


fun main() {
    println("Let examples")
    letFunctionBasicExamples()
    letFunctionFluentInterface()

    println("\nRun example")
    runFunctionFluentInterface()

    println("\nWith example")
    withFunctionFluentInterface()

    println("\nApply example")
    applyFunctionFluentInterface()

    println("\nAlso example")
    alsoFunctionFluentInterface()
}


fun letFunctionBasicExamples() {
    val listOfStrings: List<String?> = listOf("one", null, "three", null)
    val num = 1234
    println("Initial data: listOfStrings: $listOfStrings")

    // if item is not null then assign it to upper variable as upper case
    //  else, use elvis operator to execute the next println if item is null
    listOfStrings.forEachIndexed { index, item ->
        item?.let {
            val upper = it.uppercase()
            println(" Item at index $index is $upper")
        } ?: println(" Item at index $index is null")
    }

    // let creates a scoped block, therefore the variables inside it won't pollute the outer block
    val result = num.let {
        val numToStr = it.toString()
        val reverse = numToStr.reversed()
        val split = reverse.toList()
        val dotted = split.joinToString(separator = ".")
        dotted // return the modified instance
    }
    println("Initial number: $num, after let: $result")
}


// 'it' refers to the Car object that is passed into the lambda function by the 'let' function
// 'it' needs to be referenced to access the methods of the Car object
fun letFunctionFluentInterface() {
    val car = Car("Renault", 2500.00).let {
        it.fillColor("Green")
        it.setOwner("Dave")
        it // explicitly return 'it' as the result of the 'let' function
    }
    println("From 'let': $car")
}


// 'this' refers to the context object on which the 'run' function is being called, which is the Car object in this case
// 'this' does not need to be referenced to access the methods of the Car object
fun runFunctionFluentInterface() {
    val car = Car("Dacia", 1250.00).run {
        fillColor("Blue")
        setOwner("Jim")
        this // implicitly return 'this' as the result of the 'run' function
    }
    println("From 'run': $car")
}


// 'this' refers to the Car object on which the block is being executed, which is implicitly passed into the 'with' function
// 'this' does not need to be referenced to access the methods of the Car object
// does not return any value from the block
fun withFunctionFluentInterface() {
    val car = Car("Skoda", 2250.75)
    with(car) {
        fillColor("Red")
        setOwner("Joe")
    }
    println("From 'with': $car")

}

// 'this' refers to the Car object on which the 'apply' function is being called
// 'this' does not need to be referenced to access the methods of the Car object
// returns the same Car object after applying the specified operations
fun applyFunctionFluentInterface() {
    val car = Car("BMW", 4000.00).apply {
        fillColor("Black")
        setOwner("Doug")
    }
    println("From 'apply: $car")
}


// 'it' refers to the Car object that is passed into the lambda function by the 'also' function
// 'it' needs to be referenced to access the methods of the Car object
// returns the same Car object after applying the specified operations
fun alsoFunctionFluentInterface() {
    val car = Car("Mercedes", 4200.00).also {
        it.fillColor("White")
        it.setOwner("Mel")
    }
    println("From 'apply: $car")
}
